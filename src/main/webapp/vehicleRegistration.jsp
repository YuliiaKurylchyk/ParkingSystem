<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        <%@include file="WebContent/creation-style.css" %>
    </style>
</head>
<body>
<h1>Parking ticket creation</h1>
<form action="vehicleCreation" method="post" >
    <h3>Vehicle information:</h3>

    <p id="label_type_of_vehicle">Type of Vehicle: </p>
    <div class="vehicle_type_button">
        <input type="radio" id="motorbike" class="type_of_vehicle"
               name="type_of_vehicle" value="MOTORBIKE">
        <label for="motorbike">Motorbike</label>
    </div>
    <div class="vehicle_type_button">
        <input type="radio" id="car" class="type_of_vehicle"
               name="type_of_vehicle" value="CAR">
        <label for="car">Car</label>
    </div>
    <div class="vehicle_type_button">
        <input type="radio" id="truck" class="type_of_vehicle"
               name="type_of_vehicle" value="TRUCK">
        <label for="truck">Truck</label>
    </div>
    <div class="vehicle_type_button">
        <input type="radio" id="bus" class="type_of_vehicle"
               name="type_of_vehicle" value="BUS">
        <label for="bus">Bus</label>
    </div>

    <p><input placeholder="Make" name="make"></p>
    <p><input placeholder="Model" name="model"></p>
    <p><input placeholder="Licence plate" name="licence_plate"></p>


    <div style="overflow:auto;">
        <div style="float:right;">
            <button type="submit" value="vehicleCreation">Next</button>
        </div>
    </div>

</form>



</body>
</html>
