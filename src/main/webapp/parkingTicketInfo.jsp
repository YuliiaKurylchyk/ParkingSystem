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
    <title>Title</title>
    <style type="text/css">
        <%@include file="WebContent/parkingInfo-style.css" %>
    </style>

</head>
<body>

<h1>Parking ticket Information: </h1>

<h3 style="color:red;"><c:out value="${requestScope.alreadyLeft}"></c:out></h3>
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
                <a href="search?action=update&option=vehicle&vehicleID=<c:out value='${currentTicket.vehicle.licencePlate}'/>">Edit</a>
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
            <td><a href="search?action=update&option=customer&phoneNumber=<c:out value='${currentTicket.customer.phoneNumber}' />">Edit</a>
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
            <td><c:out value="${currentTicket.leftTime}"/></td>
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
        <a href="/">
            <button type="button" value="" name="BackToMenu">Back to menu</button>
        </a>
    </div>
    <c:if test="${currentTicket.status=='present'}">
        <div style="float:left;">
            <a href="search?action=remove&option=parkingTicket&parkingTicketID=<c:out value='${currentTicket.parkingTicketID}' />">
                <button type="button" value="" name="Remove">Remove</button>
            </a>
        </div>
    </c:if>
    <c:if test="${currentTicket.status=='left' and requestScope.deleted==null}">
        <div style="float:left;">
            <a href="search?action=delete&option=parkingTicket&parkingTicketID=<c:out value='${currentTicket.parkingTicketID}' />">
                <button type="button" value="" name="deleteCompletely">Delete Completely</button>
            </a>
        </div>
    </c:if>
</div>
</body>
</html>
