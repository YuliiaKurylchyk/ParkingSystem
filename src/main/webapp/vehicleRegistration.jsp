<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        <%@include file="WebContent/creation-style.css" %>
    </style>
</head>
<body>
<h1>Parking ticket creation</h1>
<c:if test="${requestScope.vehicle != null}">
<form action="update" method="post">
    </c:if>

    <c:if test="${requestScope.vehicle==null}">
        <form action="create" method="post">
        </c:if>
        <h3>Vehicle information:</h3>

        <p id="label_type_of_vehicle">Type of Vehicle: </p>
        <div class="vehicle_type_button">
            <input type="radio" id="motorbike" class="type_of_vehicle"
                   name="typeOfVehicle" value="MOTORBIKE" ${requestScope.vehicle.typeOfVehicle=='MOTORBIKE'? 'checked':''}
                                                                 ${typeOfVehicle=='MOTORBIKE'?'checked':''}>
            <label for="motorbike">Motorbike</label>
        </div>
        <div class="vehicle_type_button">
            <input type="radio" id="car" class="type_of_vehicle"
                   name="typeOfVehicle" value="CAR" ${requestScope.vehicle.typeOfVehicle=='CAR'? 'checked':''}
                                                        ${typeOfVehicle=='CAR'?'checked':''}>
            <label for="car">Car</label>
        </div>
        <div class="vehicle_type_button">
            <input type="radio" id="truck" class="type_of_vehicle"
                   name="typeOfVehicle" value="TRUCK" ${requestScope.vehicle.typeOfVehicle=='TRUCK'? 'checked':''}
                                                    ${typeOfVehicle=='TRUCK'?'checked':''} >
            <label for="truck">Truck</label>
        </div>
        <div class="vehicle_type_button">
            <input type="radio" id="bus" class="type_of_vehicle"
                   name="typeOfVehicle" value="BUS" ${requestScope.vehicle.typeOfVehicle=='BUS'? 'checked':''}
                                                    ${typeOfVehicle=='BUS'?'checked':''}>
            <label for="bus">Bus</label>
        </div>


        <p><input placeholder="Make" name="make"
                  <c:choose>
                  <c:when test="${requestScope.vehicle!=null}">value="${requestScope.vehicle.make}" </c:when>
                   <c:otherwise>value="${make}"</c:otherwise>
                    </c:choose>
                      ></p>
        <p><input placeholder="Model" name="model"
        <c:choose>
                  <c:when test="${requestScope.vehicle!=null}">value="${requestScope.vehicle.model}" </c:when>
                  <c:otherwise>value="${model}"</c:otherwise>
        </c:choose>

        ></p>



        <p><input placeholder="License plate" name="licensePlate"
        <c:choose>
                  <c:when test="${requestScope.vehicle!=null}">value="${requestScope.vehicle.licensePlate}" </c:when>
                  <c:otherwise>value="${licensePlate}"</c:otherwise>
        </c:choose>

        ></p>


            <c:if test="${requestScope.violations!=null}">

                <c:forEach items="${requestScope.violations}" var="violation">
                    <h3 style="color:red">${requestScope.violation}.</h3>
                </c:forEach>
            </c:if>


        <div style="overflow:auto;">
            <div style="float:right;">
                <c:if test="${requestScope.vehicle != null}">
                    <button type="submit">Done</button>
                </c:if>

                <c:if test="${requestScope.vehicle == null}">
                    <button type="submit">Next</button>
                </c:if>
            </div>
        </div>

    </form>
</body>
</html>
