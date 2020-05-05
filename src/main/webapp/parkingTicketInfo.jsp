<%@ page import="com.kurylchyk.model.ParkingTicket" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        <%@include file="WebContent/creation-style.css" %>
    </style>

</head>
<body>
<h1>Parking ticket Information: </h1>

<div id="container">
    <table>

        <tr><td><jsp:text>Parking Ticket ID</jsp:text></td>
            <td><%=((ParkingTicket)session.getAttribute("parkingTicket")).getParkingTicketID() %></td>
        </tr>
        <tr>
            <td><jsp:text>Vehicle</jsp:text></td>
            <td><%=((ParkingTicket)session.getAttribute("parkingTicket")).getVehicle() %></td>
        </tr>

        <tr>
            <td><jsp:text>Parking Slot</jsp:text></td>
            <td><%=((ParkingTicket)session.getAttribute("parkingTicket")).getParkingSlot() %></td>
        </tr>
        <tr><td><jsp:text>Customer</jsp:text></td>
            <td><%=((ParkingTicket)session.getAttribute("parkingTicket")).getCustomer() %></td>
        </tr>
        <tr>
            <td><jsp:text>From time: </jsp:text></td>
            <td><%=((ParkingTicket)session.getAttribute("parkingTicket")).getFrom_time() %></td>
        </tr>
        <tr>

            <td>Status:</td>
            <td><%=((ParkingTicket)session.getAttribute("parkingTicket")).getStatus() %></td>
        </tr>
    </table>
</div>

<div id="containerButton">
    <div style="float: left;">
         <button type="button" value="" name="BackToMenu">Get receipt</button>
    </div>

    <div style="float:right;">
        <button type="button" value="" name="GetReceipt">Back to menu</button>
    </div>

</div>
</body>
</html>
