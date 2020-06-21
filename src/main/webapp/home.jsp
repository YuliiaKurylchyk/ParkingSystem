<%@ page import="com.kurylchyk.model.vehicles.Vehicle" %>
<%@ page import="com.kurylchyk.model.dao.VehicleDAO" %>
<%@ page import="com.kurylchyk.model.vehicles.TypeOfVehicle" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>home page</title>
    <style type="text/css">
        <%@include file="WebContent/home-style.css" %>
    </style>
    <script src="https://kit.fontawesome.com/89f0f4f970.js" crossorigin="anonymous"></script>
</head>
<body>
<header>Parking system</header>
<div id="main_container">
    <nav>
       <!-- <a href="<%=request.getContextPath()%>/new">-->
        <a href="/vehicle/form">
            <div class="in_nav">
                <div class="menu_icon">
                    <i class="fas fa-plus"></i>
                </div>
                <div class="menu_sign">Create parking ticket</div>
            </div>
        </a>
<!--
       <a href="/home/remove">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-minus"></i></div>
                <div class="menu_sign">Delete parking ticket</div>
            </div>
        </a>
        <a href="/home/updating">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-pencil-alt"></i></div>
                <div class="menu_sign">Update</div>
            </div>
        </a>
        -->
        <a href="/searchPage.jsp">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-search"></i></div>
                <div class="menu_sign">Find parking ticket</div>
            </div>
        </a>
        <a href="/parkingTicket/showAll?date=allTickets&status=PRESENT">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-eye"></i></div>
                <div class="menu_sign">Show all parking tickets</div>
            </div>
        </a>

        <a href="/vehicle/show?status=PRESENT">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-car-side"></i></div>
                <div class="menu_sign">Show all vehicles</div>
            </div>
        </a>

        <a href="/customer/show">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-user-friends"></i></div>
                <div class="menu_sign">Show all customers</div>
            </div>
        </a>

    </nav>

    <div class="container" >
        <div id="vehicle_container">
            <div id="motorbike" class="vehicle" >
                <p class="vehicle_header">Count of motorbikes</p>
                <div  class="vehicle_icon" id="icon_motorbike">
                    <i class="fas fa-motorcycle "></i>
                </div>
                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfBikes}"> </c:out>
                </div>
            </div>
            <div id="car" class="vehicle" >
                <p class="vehicle_header">Count of cars</p>
                <div class="vehicle_icon" id="icon_car">
                    <i class="fas fa-car"></i>
                </div>
                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfCars}"> </c:out>
                </div>

            </div>

            <div id="truck" class="vehicle" >
                <p class="vehicle_header">Count of trucks</p>
                <div class="vehicle_icon" id="icon_truck">
                    <i class="fas fa-truck-moving"></i>
                </div>

                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfTrucks}"> </c:out>
                </div>
            </div>
            <div id="bus" class="vehicle" >
                <p class="vehicle_header">Count of buses</p>
                <div class="vehicle_icon" id="icon_bus">
                    <i class="fas fa-bus-alt"></i>
                </div>

                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfBuses}"> </c:out>
                </div>
            </div>

        </div>

        <div id="info_container">

            <h5>Current information about parking tickets</h5>
            <table>
                <tr>
                    <td>Today's vehicle entries: </td>
                    <td><c:out value="${requestScope.countOfTodayEntities}"></c:out> </td>
                </tr>

                <tr>
                    <td>Today's vehicle entries present: </td>
                    <td> <c:out value="${requestScope.countOfAllPresent}"></c:out></td>
                </tr>
                <tr>
                    <td>Total vehicle entries left: </td>
                    <td> <c:out value="${requestScope.countOfAllLeft}"></c:out></td>
                </tr>
                <tr>
                    <td>Count of free slots

                        <table>
                            <tr> <td>SMALL</td> <td><c:out value="${requestScope.smallSlots}"></c:out></td> </tr>
                            <tr> <td>MEDIUM</td> <td><c:out value="${requestScope.mediumSlots}"></c:out></td> </tr>
                            <tr> <td>LARGE</td> <td><c:out value="${requestScope.largeSlots}"></c:out></td> </tr>
                        </table>
                    </td>

                </tr>
            </table>
        </div>
    </div>
</div>

</body>
</html>