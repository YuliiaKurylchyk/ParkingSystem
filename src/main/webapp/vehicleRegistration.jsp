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
<c:if test="${vehicle != null}">
<form action="updatingServlet?option=updateVehicle" method="post">
    </c:if>

    <c:if test="${vehicle==null}">
    <form action="creationServlet?action=vehicle" method="post">
        </c:if>
        <h3>Vehicle information:</h3>

        <p id="label_type_of_vehicle">Type of Vehicle: </p>
        <div class="vehicle_type_button">
            <input type="radio" id="motorbike" class="type_of_vehicle"
                   name="type_of_vehicle" value="MOTORBIKE" ${vehicle.typeOfVehicle=='MOTORBIKE'? 'checked':''}>
            <label for="motorbike">Motorbike</label>
        </div>
        <div class="vehicle_type_button">
            <input type="radio" id="car" class="type_of_vehicle"
                   name="type_of_vehicle" value="CAR" ${vehicle.typeOfVehicle=='CAR'? 'checked':''} >
            <label for="car">Car</label>
        </div>
        <div class="vehicle_type_button">
            <input type="radio" id="truck" class="type_of_vehicle"
                   name="type_of_vehicle" value="TRUCK" ${vehicle.typeOfVehicle=='TRUCK'? 'checked':''}>
            <label for="truck">Truck</label>
        </div>
        <div class="vehicle_type_button">
            <input type="radio" id="bus" class="type_of_vehicle"
                   name="type_of_vehicle" value="BUS" ${vehicle.typeOfVehicle=='BUS'? 'checked':''}>
            <label for="bus">Bus</label>
        </div>

        <c:if test="${requestScope.badType!=null}">
            <p style="color:red">
                <c:out value="${requestScope.badType}"/>
            </p>
        </c:if>
        <p><input placeholder="Make" name="make" value="${vehicle.make}"></p>
        <p><input placeholder="Model" name="model" value="${vehicle.model}"></p>

        <c:if test="${requestScope.badData!=null}">
            <h3 style="color:red">
                <c:out value="${requestScope.badData}"/>
            </h3>
        </c:if>

        <p><input placeholder="Licence plate" name="licence_plate" value="${vehicle.licencePlate}"></p>

        <c:if test="${requestScope.badLicencePlate!=null}">
            <h3 style="color:red">
                <c:out value="${requestScope.badLicencePlate}"/>
            </h3>
        </c:if>


        <div style="overflow:auto;">
            <div style="float:right;">
                <c:if test="${vehicle != null}">
                    <button type="submit">Done</button>
                </c:if>

                <c:if test="${vehicle == null}">
                    <button type="submit">Next</button>
                </c:if>
            </div>
        </div>

    </form>
</body>
</html>
