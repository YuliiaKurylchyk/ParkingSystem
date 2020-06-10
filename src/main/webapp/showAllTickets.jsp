<%@ page import="com.kurylchyk.controller.ParkingTicketUtil" %>
<%@ page import="com.kurylchyk.model.parkingTicket.ParkingTicket" %>
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
        <%@include file="WebContent/showPage-style.css" %>
    </style>

</head>
<body>
<h1>Parking Tickets</h1>

<c:if test="${requestScope.onlyCurrentCustomer==null}">
    <h3>Choose the date and parking status</h3>
    <div id="selectContainer">
        <form action="showTicket" method="GET">
            <select name="dateOfParkingTicket">
                <option value="today">Today</option>
                <option value="yesterday">From yesterday</option>
                <option value="oneWeekAgo">For the last week</option>
                <option value="MonthAgo">For the last month</option>
                <option value="allTickets">All</option>
            </select>

            <select name="statusOfParkingTicket">
                <option value="all">all</option>
                <option value="present">only present</option>
                <option value="left">only left</option>
            </select>

            <input id="showButton" type="submit" value="Show">
        </form>
    </div>
</c:if>
<input type="hidden" name="dateOfParkingTicket" value="${param.dateOfParkingTicket}">
<input type="hidden" name="statusOfParkingTicket" value="${param.statusOfParkingTicket}">
<c:if test="${param.dateOfParkingTicket!=null}">
<div id="container">

    <c:if test="${param.dateOfParkingTicket!=null}">
        <h3>You have chosen <c:out value="${param.dateOfParkingTicket}"/>
            and <c:out value="${param.statusOfParkingTicket}"/>
        </h3>
    </c:if>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Vehicle</th>
            <th>Customer</th>
            <th>Parking Slot</th>
            <th>Status</th>
            <th>Arrival time</th>
            <th>Left time</th>
            <th>Cost</th>
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
                    <c:out value="${currentTicket.arrivalTime}"/>
                </td>
                <td>
                    <c:out value="${currentTicket.leftTime}"/>
                </td>
                <td>
                    <c:out value="${currentTicket.cost}"/>
                </td>
                <td>
                    <a href="/edit?option=parkingTicket&parkingTicketID=<c:out value='${currentTicket.parkingTicketID}'/>">Show
                        details</a> &nbsp;&nbsp;&nbsp;
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</c:if>
<div id="containerButton">
    <div style="float:left;">
        <a href="/">
            <button type="button" value="" name="BackToMenu">Back to menu</button>
        </a>
    </div>
</div>
</div>
</body>
</html>
