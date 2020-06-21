<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The list of vehicles</title>

    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>

</head>
<body>
<h3>All vehicles</h3>
<div id="selectContainer">
    <form action="/vehicle/show" method="GET" >
        <input type="radio" id="all" name="status" value="ALL" onchange="this.form.submit();"
        ${param.status=='ALL'?'checked':''}>
        <label for="all"> ALL </label><br>
        <input type="radio" id="present" name="status" value="PRESENT" onchange="this.form.submit();"
        ${param.status=='PRESENT'?'checked':''}>
        <label for="present"> PRESENT </label><br>
        <input type="radio" id="left" name="status" value="LEFT" onchange="this.form.submit();"
        ${param.status=='LEFT'?'checked':''}>
        <label for="left"> LEFT </label><br>
    </form>
</div>
<input type="hidden" name="status" value="${param.status}">
<div id="container">
    <table>
        <thead>
        <tr>
            <th>Make</th>
            <th>Model</th>
            <th>license plate</th>
            <th>Type</th>
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
                <td>
                    <c:out value="${vehicle.typeOfVehicle}"/>
                </td>
                <td>
                    <a href="/parkingTicket/showByVehicle?vehicleID=<c:out value='${vehicle.licensePlate}'/>">Show
                        details</a> &nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
