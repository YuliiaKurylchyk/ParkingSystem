<%@ page import="com.kurylchyk.model.parkingTicket.ParkingTicket" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Parking Ticket Information</title>
    <style type="text/css">
        <%@include file="WebContent/parkingInfo-style.css" %>
    </style>

</head>
<body>

<h1>Parking ticket Information: </h1>

<h3 style="color:red;"><c:out value="${requestScope.deleted}"></c:out></h3>

<div class="container">
    <table>
        <tr>
            <td>
                <jsp:text>Parking Ticket ID</jsp:text>
            </td>
            <td>
                <c:out value="${currentTicket.parkingTicketID}"/>
            <td></td>
        </tr>
        <tr>
            <td>
                <jsp:text>Vehicle</jsp:text>
            </td>
            <td><c:out value="${currentTicket.vehicle}"/></td>
            <td>
                <a href="/vehicle/edit?vehicleID=<c:out value='${currentTicket.vehicle.licensePlate}'/>">Edit</a>
                &nbsp;&nbsp;&nbsp;
            </td>
        </tr>

        <tr>
            <td>
                <jsp:text>Parking Slot</jsp:text>
            </td>
            <td><c:out value="${currentTicket.parkingSlot}"/></td>
            <td></td>
        </tr>
        <tr>
            <td>
                <jsp:text>Customer</jsp:text>
            </td>
            <td><c:out value="${currentTicket.customer}"/></td>
            <td><a href="/customer/edit?customerID=<c:out value='${currentTicket.customer.customerID}' />">Edit</a>
            </td>&nbsp;&nbsp;&nbsp;&nbsp;
        </tr>
        <tr>
            <td>
                <jsp:text>Arrival time:</jsp:text>
            </td>
            <td><c:out value="${currentTicket.arrivalTime}"/></td>
            &nbsp;&nbsp;<td></td>
        <tr>
            <td>Left time:</td>
            <td><c:out value="${currentTicket.departureTime}"/></td>
            <td></td>
        </tr>

        </tr>
        <tr>
            <td>Status:</td>
            <td><c:out value="${currentTicket.status}"/></td>&nbsp;
            <td></td>
        </tr>
        <tr>
            <td>Cost:</td>
            <td><c:out value="${currentTicket.cost}"/></td>&nbsp;
            <td></td>
        </tr>
    </table>
</div>


<div id="containerButton">
    <div style="float: left;margin-left: 10%;">
        <a>
            <button type="button" value="" name="GetReceipt">Get receipt</button>
        </a>
    </div>
    <div style="float:left;">
        <a href="<%request.getContextPath();%>/parkingTicket/end">
            <button type="button" value="" name="BackToMenu">Back to menu</button>
        </a>
    </div>
    <c:if test="${currentTicket.status=='PRESENT'}">
        <div style="float:left;">
            <a href="/parkingTicket/remove?parkingTicketID=<c:out value='${currentTicket.parkingTicketID}' />">
                <button type="button" value="" name="Remove">Remove</button>
            </a>
        </div>
    </c:if>
    <c:if test="${currentTicket.status=='LEFT' and requestScope.deleted==null}">
        <div style="float:left;">
            <a href="/parkingTicket/delete?parkingTicketID=<c:out value='${currentTicket.parkingTicketID}' />">
                <button type="button" value="" name="deleteCompletely">Delete Completely</button>
            </a>
        </div>
    </c:if>
</div>
</body>
</html>
