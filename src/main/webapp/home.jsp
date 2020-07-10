<%@ page import="com.kurylchyk.model.domain.vehicles.Vehicle" %>
<%@ page import="com.kurylchyk.model.dao.vehicles.VehicleDAO" %>
<%@ page import="com.kurylchyk.model.domain.vehicles.vehicleEnum.VehicleType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="home"/>
<html>
<head>
    <title>Home</title>
    <style type="text/css">
        <%@include file="WebContent/home-style.css" %>
    </style>
    <script src="https://kit.fontawesome.com/89f0f4f970.js" crossorigin="anonymous"></script>
</head>
<body>
<header>

    <div style="float:left;margin-left: 43%"><fmt:message key="parkingSystem"/></div>
        <div style="float: right; height: auto;">  <a href="/home?language=en">Eng</a> <a href="/home?language=uk">Ukr</a></div>
</header>
<div id="main_container">
    <nav>
        <a href="/vehicle/form">
            <div class="in_nav">
                <div class="menu_icon">
                    <i class="fas fa-plus"></i>
                </div>
                <div class="menu_sign"><fmt:message key="createNew"/></div>
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
                <div class="menu_sign"><fmt:message key="search"/></div>
            </div>
        </a>
        <a href="/parkingTicket/showAll?date=allTickets&status=PRESENT">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-eye"></i></div>
                <div class="menu_sign"><fmt:message key="listOfTickets"/></div>
            </div>
        </a>

        <a href="/vehicle/show?status=PRESENT&vehicleType=CAR">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-car-side"></i></div>
                <div class="menu_sign"><fmt:message key="listOfVehicles"/></div>
            </div>
        </a>

        <a href="/customer/show">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-user-friends"></i></div>
                <div class="menu_sign"><fmt:message key="listOfCustomers"/></div>
            </div>
        </a>

        <a href="/parkingSlot/showAll?slotSize=ALL">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-user-friends"></i></div>
                <div class="menu_sign">All slots</div>
            </div>
        </a>
        <a href="/parkingSlot/edit">
            <div class="in_nav">
                <div class="menu_icon"><i class="fa fa-pencil" aria-hidden="true"></i></div>
                <div class="menu_sign"><fmt:message key="editParkingSlot"/></div>
            </div>
        </a>
    </nav>

    <div class="container">
        <div id="vehicle_container">
            <div id="motorbike" class="vehicle">
                <p class="vehicle_header"><fmt:message key="countOfMotorbike"/></p>
                <div class="vehicle_icon" id="icon_motorbike">
                    <i class="fas fa-motorcycle "></i>
                </div>
                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfBikes}"> </c:out>
                </div>
            </div>
            <div id="car" class="vehicle">
                <p class="vehicle_header"><fmt:message key="countOfCars"/></p>
                <div class="vehicle_icon" id="icon_car">
                    <i class="fas fa-car"></i>
                </div>
                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfCars}"> </c:out>
                </div>

            </div>

            <div id="truck" class="vehicle">
                <p class="vehicle_header"><fmt:message key="countOfTrucks"/></p>
                <div class="vehicle_icon" id="icon_truck">
                    <i class="fas fa-truck-moving"></i>
                </div>

                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfTrucks}"> </c:out>
                </div>
            </div>
            <div id="bus" class="vehicle">
                <p class="vehicle_header"><fmt:message key="countOfBuses"/></p>
                <div class="vehicle_icon" id="icon_bus">
                    <i class="fas fa-bus-alt"></i>
                </div>

                <div class="vehicle_count">
                    <c:out value="${requestScope.numberOfBuses}"> </c:out>
                </div>
            </div>
        </div>

        <div id="info_container">

            <h5><fmt:message key="currentInfoAboutTicket"/></h5>
            <table>
                <tr>
                    <td><fmt:message key="todayVehicleEntries"/></td>
                    <td><c:out value="${requestScope.countOfTodayEntities}"></c:out></td>
                </tr>

                <tr>
                    <td><fmt:message key="vehicleEntriesPresent"/></td>
                    <td><c:out value="${requestScope.countOfAllPresent}"></c:out></td>
                </tr>
                <tr>
                    <td><fmt:message key="vehicleEntriesLeft"/></td>
                    <td><c:out value="${requestScope.countOfAllLeft}"></c:out></td>
                </tr>
                <tr>
                    <td><fmt:message key="countOfFreeSlots"/>

                        <table>
                            <tr>
                                <td><fmt:message key="small"/></td>
                                <td><c:out value="${requestScope.smallSlots}"></c:out></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="medium"/></td>
                                <td><c:out value="${requestScope.mediumSlots}"></c:out></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="large"/></td>
                                <td><c:out value="${requestScope.largeSlots}"></c:out></td>
                            </tr>
                        </table>
                    </td>

                </tr>
            </table>
        </div>
    </div>
</div>

</body>
</html>