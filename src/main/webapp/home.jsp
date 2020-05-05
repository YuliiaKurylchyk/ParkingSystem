<%@ page import="com.kurylchyk.model.vehicles.Vehicle" %>
<%@ page import="com.kurylchyk.model.dao.VehicleDao" %>
<%@ page import="com.kurylchyk.model.vehicles.TypeOfVehicle" %>
<html>
<head>
    <title>home page</title>
    <link rel="stylesheet" href="home-style.css">
    <script src="https://kit.fontawesome.com/89f0f4f970.js" crossorigin="anonymous"></script>
</head>
<body>
<header>Parking system</header>

<nav>

    <div class="in_nav">
		<span>
			<i class="fas fa-plus"></i>
		</span>
        <a href="<%=request.getContextPath()%>/new">Add new vehicle</a>
    </div>
    <div class="in_nav">
        <span><i class="fas fa-minus"></i></span>
        <a a href="">Delete vehicle</a>
    </div>
    <div class="in_nav">

        <span><i class="fas fa-pencil-alt"></i></span>
        <a href="">Update vehicle</a>
    </div>
    <div class="in_nav">
        <span><i class="fas fa-eye"></i></span>
        <a href="<%=request.getContextPath()%>/show">Show vehicle</a>
    </div>
</nav>

<div class="container" >
    <div id="motorbike" class="vehicle" >
        <p class="vehicle_header">Count of motorbikes</p>
        <div  class="vehicle_icon" id="icon_motorbike">
				<i class="fas fa-motorcycle "></i>
			</div>
        <div class="vehicle-count">
           <%=request.getAttribute("numberOfBikes")%>
        </div>
    </div>
    <div id="car" class="vehicle" >
        <p class="vehicle_header">Count of cars</p>
        <div class="vehicle_icon" id="icon_car">
			<i class="fas fa-car"></i>
		</div>
        <div class="vehicle-count">
            <%=request.getAttribute("numberOfCars")%>
        </div>

    </div>

    <div id="truck" class="vehicle" >
        <p class="vehicle_header">Count of trucks</p>
        <div class="vehicle_icon" id="icon_truck">
				<i class="fas fa-truck-moving"></i>
		</div>

        <div class="vehicle-count">
            <%=request.getAttribute("numberOfTrucks")%>
        </div>
    </div>
    <div id="bus" class="vehicle" >
        <p class="vehicle_header">Count of buses</p>
        <div class="vehicle_icon" id="icon_bus">
		<i class="fas fa-bus-alt"></i>
	</div>

        <div class="vehicle-count">
            <%=request.getAttribute("numberOfBuses")%>
        </div>
    </div>

</div>
</body>
</html>