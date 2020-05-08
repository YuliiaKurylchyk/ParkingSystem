<%@ page import="com.kurylchyk.controller.ParkingTicketUtil" %>
<%@ page import="com.kurylchyk.model.ParkingTicket" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 20:31
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
<h1>Parking Tickets</h1>

<h3>Choose the date and parking status</h3>
<div class="container">
    <div id="selectContainer">
    <form action="ParkingTicketUtil" method="POST">
        <select name="dateOfParkingTicket" >
            <option value="today">Today</option>
            <option value="yesterday">Yesterday</option>
            <option value="oneWeekAgo">1 week</option>
            <option value="MonthAgo">1 month</option>
        </select>


        <select name="statusOfParkingTicket" >
            <option value="all">all</option>
            <option value="present">only present</option>
            <option value="left">only left</option>
        </select>

        <input type="submit" value="Show">
    </form>
    </div>

    <div id="container">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Vehicle</th>
                <th>Customer</th>
                <th>Parking Slot</th>
                <th>Status</th>
                <th>From time</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="currentTicket" items="${appropriateTickets}">
                <tr>
                    <td>
                        <c:out value="${currentTicket.parkingTicketID}"/>
                    </td>
                    <td>
                        <c:out value="${currentTicket.vehicle}"/>
                    </td>
                    <td>
                        <c:out value="${currentTicket.customer}"/>
                    </td>
                    <td>
                        <c:out value="${currentTicket.parkingSlot}"/>
                    </td>
                    <td>
                        <c:out value="${currentTicket.status}"/>
                    </td>
                    <td>
                        <c:out value="${currentTicket.from_time}"/>
                    </td>
                    <td>
                        <c:out value="${currentTicket.to_time}"/>
                    </td>
                    <td>
                        <c:out value="${currentTicket.cost}"/>
                    </td>

                    <td><a href="/edit?id=<c:out value='${currentTicket.parkingTicketID}'/>">Edit</a> &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=<c:out value='${currentTicket.parkingTicketID}' />">Delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
