<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="vehicleReg"/>

<html>
<head>
    <title>Vehicle information</title>
    <style type="text/css">
        <%@include file="WebContent/creation-style.css" %>
    </style>

    <script>
        function showAdditional(x) {

            if (x == 'car') {
                document.getElementById("carAdditional").style.display = 'block';
                document.getElementById("busAdditional").style.display = 'none';
                document.getElementById("truckAdditional").style.display = 'none';
                document.getElementById("carSize").required = true;
                setRequired("trailerPresent",false)
                document.getElementById("countOfSeats").required = false;

            } else if (x == 'bus') {
                document.getElementById("busAdditional").style.display = 'block';
                document.getElementById("carAdditional").style.display = 'none';
                document.getElementById("truckAdditional").style.display = 'none';
                document.getElementById("carSize").required = false;
                setRequired("trailerPresent",false)
                document.getElementById("countOfSeats").required = true;
            } else if (x == 'truck') {
                document.getElementById("truckAdditional").style.display = 'block';
                document.getElementById("busAdditional").style.display = 'none';
                document.getElementById("carAdditional").style.display = 'none';
                document.getElementById("carSize").required = false;
                setRequired("trailerPresent",true)
                document.getElementById("countOfSeats").required = false;
            } else {
                document.getElementById("truckAdditional").style.display = 'none';
                document.getElementById("busAdditional").style.display = 'none';
                document.getElementById("carAdditional").style.display = 'none';
                document.getElementById("carSize").required = false;
                setRequired("trailerPresent",false)
                document.getElementById("countOfSeats").required = false;
            }
            return;
        }
        function setRequired(element,value) {

            let input = document.getElementsByClassName(element);
            for(i=0; i<input.length; i++){
                input[i].required = value;
            }

        }


    </script>
</head>
<body>

<h1><fmt:message key="parkingTicketCreation"/></h1>
<h3><fmt:message key="vehicleInfo"/></h3>
<c:if test="${sessionScope.vehicle != null}">
<form action="/vehicle/update" method="POST"></c:if>
    <c:if test="${sessionScope.vehicle==null}">
    <form action="/vehicle/create" method="POST">
        </c:if>
        <div <c:if test="${sessionScope.vehicle!=null}" >
            style="display: none"
        </c:if>>
        <p id="label_type_of_vehicle"><fmt:message key="typeOfVehicle"/></p>
        <fieldset>
            <div class="vehicle_type_button">
                <input type="radio" id="motorbike" class="type_of_vehicle"
                       name="typeOfVehicle" required
                       value="MOTORBIKE"
                       onclick="showAdditional('motorbike');"
                ${sessionScope.vehicle.vehicleType=='MOTORBIKE'? 'checked':''}>
                <label for="motorbike"><fmt:message key="motorbike"/></label>
            </div>
            <div class="vehicle_type_button">
                <input type="radio" id="car" class="type_of_vehicle" onclick="showAdditional('car');"
                       name="typeOfVehicle" required
                       value="CAR" ${sessionScope.vehicle.vehicleType=='CAR'? 'checked':''}>
                <label for="car"><fmt:message key="car"/></label>
            </div>
            <div class="vehicle_type_button">
                <input type="radio" id="truck" class="type_of_vehicle" required onclick="showAdditional('truck');"
                       name="typeOfVehicle" value="TRUCK" ${sessionScope.vehicle.vehicleType=='TRUCK'? 'checked':''}
                <label for="truck"><fmt:message key="truck"/></label>
            </div>
            <div class="vehicle_type_button">
                <input type="radio" id="bus" class="type_of_vehicle" required onclick="showAdditional('bus');"
                       name="typeOfVehicle" value="BUS" ${sessionScope.vehicle.vehicleType=='BUS'? 'checked':''}
                <label for="bus"><fmt:message key="bus"/></label>
            </div>
        </fieldset>
        </div>
        <p><input placeholder="<fmt:message key="make"/>" required name="make" class="vehicleInput"
        value="${sessionScope.vehicle.make}"
        >
        </p>
        <p><input placeholder="<fmt:message key="model"/>" name="model" required class="vehicleInput"
                  value="${sessionScope.vehicle.model}"
        ></p>
        <p><input placeholder="<fmt:message key="licensePlate"/>" name="licensePlate" required
                  value="${sessionScope.vehicle.licensePlate}"
                  class="vehicleInput"
                  pattern="^[A-Z]{2} [0-9]{4} [A-Z]{2}$|^[0-9]{3}-[0-9]{2}[A-Z]{2}$">


<c:if test="${sessionScope.vehicle==null || sessionScope.vehicle!=null && sessionScope.vehicle.vehicleType=='CAR' }">
        <div id="carAdditional" <c:if test="${sessionScope.vehicle!=null && sessionScope.vehicle.vehicleType=='CAR'}">
            style="display: block;"
        </c:if>>
            <select name="carSize" id="carSize" >
                <option value="" disabled selected>Choose appropriate car size</option>
                <option value="MINI_CAR" ${sessionScope.vehicle.carSize == 'MINI_CAR' ? 'selected' : ''}>MINICAR</option>
                <option value="FAMILY_CAR" ${sessionScope.vehicle.carSize == 'FAMILY_CAR' ? 'selected' : ''}>FAMILY CAR</option>
                <option value="EXCLUSIVE_CAR" ${sessionScope.vehicle.carSize == 'EXCLUSIVE_CAR' ? 'selected' : ''}>EXCLUSIVE CAR</option>
                <option value="SUV" ${sessionScope.vehicle.carSize == 'SUV' ? 'selected' : ''}>SPORT UTILITY VEHICLE</option>
                <option value="MPV" ${sessionScope.vehicle.carSize == 'MPV' ? 'selected' : ''}>MULTY PURPOSE VEHICLE</option>
                <option value="PICK_UP" ${sessionScope.vehicle.carSize == 'PICK_UP' ? 'selected' : ''}>PICK UP</option>
            </select>
        </div>
            </c:if>

            <c:if test="${sessionScope.vehicle==null || sessionScope.vehicle!=null && sessionScope.vehicle.vehicleType=='BUS' }">
        <div id="busAdditional"
                <c:if test="${sessionScope.vehicle.vehicleType=='BUS'}">
                    style="display: block"
                </c:if>>
            <input type="number" min="5" max="100"  id="countOfSeats" placeholder="Count of seats"
                   name="countOfSeats" value="${sessionScope.vehicle.countOfSeats}">
        </div>
            </c:if>
<c:if test="${sessionScope.vehicle==null || sessionScope.vehicle!=null && sessionScope.vehicle.vehicleType=='TRUCK' }">
        <div id="truckAdditional" <c:if test="${sessionScope.vehicle!=null && sessionScope.vehicle.vehicleType=='TRUCK'}">
            style="display: block"
        </c:if>>
            <fieldset>
                <input type="radio" class="trailerPresent" name="trailerPresent" id="withTrailer" value="true"
                ${sessionScope.vehicle.trailerPresent==true? 'checked':''}
                >
                <label for="withTrailer">With trailer</label>
                <input type="radio" class="trailerPresent" name="trailerPresent"
                ${sessionScope.vehicle.trailerPresent==false? 'checked':''}
                       value="false" id="withoutTrailer" class="type_of_vehicle">
                <label for="withoutTrailer">Without trailer</label>
            </fieldset>
        </div>
</c:if>
        <div style="overflow:auto;">
            <div style="float:right;">
                <c:if test="${sessionScope.vehicle != null}">
                    <button type="submit"><fmt:message key="done"/></button>
                </c:if>

                <c:if test="${sessionScope.vehicle == null}">
                    <button type="submit"><fmt:message key="next"/></button>
                </c:if>
            </div>
        </div>
    </form>
</body>

</html>
