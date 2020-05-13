<%@ page import="com.kurylchyk.model.vehicles.Vehicle" %>
<%@ page import="com.kurylchyk.model.dao.VehicleDao" %>
<%@ page import="com.kurylchyk.model.vehicles.TypeOfVehicle" %>
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
        <a href="<%=request.getContextPath()%>/new">
            <div class="in_nav">
                <div class="menu_icon">
                    <i class="fas fa-plus"></i>
                </div>
                <div class="menu_sign">Create parking ticket</div>
            </div>
        </a>

        <a href="<%=request.getContextPath()%>/remove">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-minus"></i></div>
                <div class="menu_sign">Delete parking ticket</div>
            </div>
        </a>
        <a href="<%=request.getContextPath()%>/update">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-pencil-alt"></i></div>
                <div class="menu_sign">Update</div>
            </div>
        </a>
        <a href="<%=request.getContextPath()%>/show">
            <div class="in_nav">
                <div class="menu_icon"><i class="fas fa-eye"></i></div>
                <div class="menu_sign">Show all parking tickets</div>
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
                    <%=request.getAttribute("numberOfBikes")%>
                </div>
            </div>
            <div id="car" class="vehicle" >
                <p class="vehicle_header">Count of cars</p>
                <div class="vehicle_icon" id="icon_car">
                    <i class="fas fa-car"></i>
                </div>
                <div class="vehicle_count">
                    <%=request.getAttribute("numberOfCars")%>
                </div>

            </div>

            <div id="truck" class="vehicle" >
                <p class="vehicle_header">Count of trucks</p>
                <div class="vehicle_icon" id="icon_truck">
                    <i class="fas fa-truck-moving"></i>
                </div>

                <div class="vehicle_count">
                    <%=request.getAttribute("numberOfTrucks")%>
                </div>
            </div>
            <div id="bus" class="vehicle" >
                <p class="vehicle_header">Count of buses</p>
                <div class="vehicle_icon" id="icon_bus">
                    <i class="fas fa-bus-alt"></i>
                </div>

                <div class="vehicle_count">
                    <%=request.getAttribute("numberOfBuses")%>
                </div>
            </div>

        </div>

        <div id="info_container">

            <h5>Current information about parking tickets</h5>
            <table>
                <tr>
                    <td>Today is:</td>
                    <td> </td>
                </tr>

                <tr>
                    <td>Today's vehicle entries: </td>
                    <td> </td>
                </tr>

                <tr>
                    <td>Today's vehicle entries present: </td>
                    <td> </td>
                </tr>
                <tr>
                    <td>Total vehicle entries left: </td>
                    <td> </td>
                </tr>
                <tr>
                    <td>Вільні small,medium, large </td>
                    <td> </td>
                </tr>
            </table>
        </div>
    </div>
</div>

</body>
</html>