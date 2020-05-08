<%@ page import="com.kurylchyk.model.ParkingTicket" %><%--
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
        <%@include file="WebContent/creation-style.css" %>
    </style>

</head>
<body>

<h1>Parking ticket Information: </h1>

<div class="container">
    <table>
        <tr><td><jsp:text>Parking Ticket ID</jsp:text></td>
            <td> <c:out value="${currentTicket.parkingTicketID}"/>
            <td></td>
        </tr>
        <tr>
            <td><jsp:text>Vehicle</jsp:text></td>
            <td> <c:out value="${currentTicket.vehicle}"/></td>
            <td><a href="edit?id=<c:out value='${currentTicket.vehicle}'/>">Edit</a> &nbsp;&nbsp;&nbsp;</td>
        </tr>

        <tr>
            <td><jsp:text>Parking Slot</jsp:text></td>
            <td> <c:out value="${currentTicket.parkingSlot}"/></td>
            <td></td>
        </tr>
        <tr><td><jsp:text>Customer</jsp:text></td>
            <td> <c:out value="${currentTicket.customer}"/></td>
            <td><a href="edit?id=<c:out value='${currentTicket.customer}' />">Edit</a> </td>&nbsp;&nbsp;&nbsp;&nbsp;
        </tr>
        <tr>
            <td><jsp:text>From time: </jsp:text></td>
            <td> <c:out value="${currentTicket.from_time}"/></td>
            <td><a href="edit?id=<c:out value='${currentTicket.from_time}' />">Edit</a> </td>&nbsp;&nbsp;&nbsp;&nbsp;
        </tr>
        <tr>
            <td>Status:</td>
            <td> <c:out value="${currentTicket.status}"/></td>
            <td><a href="edit?id=<c:out value='${currentTicket.status}' />">Edit</a> &nbsp;</td>&nbsp;&nbsp;&nbsp;
        </tr>
    </table>
</div>

<div id="containerButton">
    <div style="float: left;">
         <button type="button" value="" name="GetReceipt">Get receipt</button>
    </div>
    <div style="float:right;">
        <a href="/"> <button type="button" value="" name="BackToMenu">Back to menu</button> </a>
    </div>

</div>
</body>
</html>
