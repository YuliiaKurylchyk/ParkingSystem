<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All tickets</title>
    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>
</head>

<body>
<h1>Parking Tickets</h1>
<c:if test="${requestScope.onlyCurrentCustomer==null}">
    <h3>Choose the date and parking status</h3>
    <div id="selectContainer">
        <form action="/parkingTicket/showAll" method="GET">
            <select name="date">
                <option value="today" ${param.date == 'today' ? 'selected="selected"':''}  >Today</option>
                <option value="yesterday" ${param.date == 'yesterday' ? 'selected="selected"':''}>From yesterday
                </option>
                <option value="oneWeekAgo" ${param.date == 'oneWeekAgo' ? 'selected="selected"':''}>For the last week
                </option>
                <option value="monthAgo" ${param.date == 'monthAgo' ? 'selected="selected"':''}>For the last month
                </option>
                <option value="allTickets" ${param.date == 'allTickets' ? 'selected="selected"':''}>All</option>
            </select>
            <select name="status">
                <option value="ALL" ${param.status == 'ALL' ? 'selected="selected"':''}>all</option>
                <option value="PRESENT" ${param.status == 'PRESENT' ? 'selected="selected"':''}>only present</option>
                <option value="LEFT" ${param.status == 'LEFT' ? 'selected="selected"':''}>only left</option>
            </select>
            <input id="showButton" type="submit" value="Show">
        </form>
    </div>
</c:if>
<input type="hidden" name="date" value="${param.date}">
<input type="hidden" name="status" value="${param.status}">

<c:choose>
    <c:when test="${requestScope.NotFound==null}">
        <div id="container">
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
                <c:forEach var="currentTicket" items="${requestScope.appropriateTickets}">
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
                            <c:out value="${currentTicket.departureTime}"/>
                        </td>
                        <td>
                            <c:out value="${currentTicket.cost}"/>
                        </td>
                        <td>
                            <a href="/parkingTicket/show?parkingTicketID=<c:out value='${currentTicket.parkingTicketID}'/>">Show
                                details</a> &nbsp;&nbsp;&nbsp;
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <div id="container">
            <h3><c:out value="${requestScope.NotFound}"/></h3>
        </div>
    </c:otherwise>
</c:choose>
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
