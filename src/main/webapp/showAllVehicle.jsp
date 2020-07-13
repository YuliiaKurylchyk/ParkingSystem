<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="vehicleShow"/>
<html>
<head>
    <title>The list of vehicles</title>

    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>

</head>
<body>
<h1 style="color: deepskyblue"><fmt:message key="allVehicle"/></h1>
<div id="selectContainer">
    <form action="/vehicle/show" method="GET">
        <p id="label_type_of_vehicle"><fmt:message key="typeOfVehicle"/></p>
        <fieldset>
            <input type="radio" id="motorbike" class="type_of_vehicle" required
                   name="vehicleType"
                   value="MOTORBIKE"
                   onchange="this.form.submit();"
            ${param.vehicleType=='MOTORBIKE'? 'checked':''}>
            <label for="motorbike"><fmt:message key="motorbike"/></label>

            <input type="radio" id="car" class="type_of_vehicle" onchange="this.form.submit();"
                   name="vehicleType" required
                   value="CAR" ${param.vehicleType=='CAR'? 'checked':''}>
            <label for="car"><fmt:message key="car"/></label>

            <input type="radio" id="truck" class="type_of_vehicle" required onchange="this.form.submit();"
                   name="vehicleType" value="TRUCK" ${param.vehicleType=='TRUCK'? 'checked':''}
            <label for="truck"><fmt:message key="truck"/></label>

            <input type="radio" id="bus" class="type_of_vehicle" required onchange="this.form.submit();"
                   name="vehicleType" value="BUS" ${param.vehicleType=='BUS'? 'checked':''}
            <label for="bus"><fmt:message key="bus"/></label>

        </fieldset>

        <input type="radio" id="all" name="status" value="ALL" onchange="this.form.submit();"
        ${param.status=='ALL'?'checked':''}>
        <label for="all"> <fmt:message key="all"/> </label>
        <input type="radio" id="present" name="status" value="PRESENT" onchange="this.form.submit();"
        ${param.status=='PRESENT'?'checked':''}>
        <label for="present"> <fmt:message key="present"/> </label>
        <input type="radio" id="left" name="status" value="LEFT" onchange="this.form.submit();"
        ${param.status=='LEFT'?'checked':''}>
        <label for="left"> <fmt:message key="left"/> </label>
    </form>
</div>
<input type="hidden" name="status" value="${param.status}">
<div id="container">
    <table>
        <thead>
        <tr>
            <th><fmt:message key="make"/></th>
            <th><fmt:message key="model"/></th>
            <th><fmt:message key="licensePlate"/></th>

            <c:if test="${vehicle.vehicleType=='CAR'}">
                <th>Car size</th>
            </c:if>

            <c:if test="${vehicle.vehicleType=='BUS'}">
                <th>Count of seats</th>
            </c:if>

            <c:if test="${vehicle.vehicleType=='TRUCK'}">
                <th>Trailer</th>
            </c:if>

        </tr>
        </thead>
        <tbody>

        <c:forEach var="vehicle" items="${requestScope.allVehicles}">
            <tr>
                <td>
                    <c:out value="${vehicle.make}"/>
                </td>
                <td>
                    <c:out value="${vehicle.model}"/>
                </td>
                <td>
                    <c:out value="${vehicle.licensePlate}"/>
                </td>


                <c:if test="${vehicle.vehicleType=='CAR'}">
                <td>
                    <c:out value="${vehicle.carSize}" />
                </td>
                </c:if>

                <c:if test="${vehicle.vehicleType=='BUS'}">
                    <td>
                        <c:out value="${vehicle.countOfSeats}" />
                    </td>
                </c:if>

                <c:if test="${vehicle.vehicleType=='TRUCK'}">
                    <td>
                        <c:out value="${vehicle.trailerPresent?'With trailer':'Without trailer'}" />
                    </td>
                </c:if>

                <td>
                    <a href="/parkingTicket/showByVehicle?vehicleID=<c:out value='${vehicle.licensePlate}'/>">
                        <fmt:message key="details"/></a> &nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div id="containerButton">
        <div style="float:left;">
            <a href="/">
                <button type="button" value="" name="BackToMenu"><fmt:message key="backToHome"/></button>
            </a>
        </div>
    </div>

</div>
</body>
</html>
