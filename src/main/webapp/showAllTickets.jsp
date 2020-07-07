<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="parkingTicketShow"/>
<html>
<head>
    <title>All tickets</title>
    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>
</head>

<body>
<h1><fmt:message key="allParkingTickets"/></h1>
<c:if test="${requestScope.onlyCurrentCustomer==null}">
    <h3><fmt:message key="choose"/></h3>
    <div id="selectContainer">
        <form action="/parkingTicket/showAll" method="GET">
            <select name="date">
                <option value="today" ${param.date == 'today' ? 'selected="selected"':''}  ><fmt:message key="today"/></option>
                <option value="yesterday" ${param.date == 'yesterday' ? 'selected="selected"':''}><fmt:message key="fromYesterday"/>
                </option>
                <option value="oneWeekAgo" ${param.date == 'oneWeekAgo' ? 'selected="selected"':''}><fmt:message key="fromWeek"/>
                </option>
                <option value="monthAgo" ${param.date == 'monthAgo' ? 'selected="selected"':''}><fmt:message key="forMonth"/>
                </option>
                <option value="allTickets" ${param.date == 'allTickets' ? 'selected="selected"':''}><fmt:message key="allOfTheTickets"/></option>
            </select>
            <select name="status">
                <option value="ALL" ${param.status == 'ALL' ? 'selected="selected"':''}><fmt:message key="all"/></option>
                <option value="PRESENT" ${param.status == 'PRESENT' ? 'selected="selected"':''}><fmt:message key="present"/></option>
                <option value="LEFT" ${param.status == 'LEFT' ? 'selected="selected"':''}><fmt:message key="left"/></option>
            </select>
            <input id="showButton" type="submit" value="<fmt:message key="show"/>">
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
                    <th><fmt:message key="ticketID"/></th>
                    <th><fmt:message key="vehicle"/></th>
                    <th><fmt:message key="customer"/></th>
                    <th><fmt:message key="parkingSlot"/></th>
                    <th><fmt:message key="status"/></th>
                    <th><fmt:message key="arrivalTime"/></th>
                    <th><fmt:message key="leftTime"/></th>
                    <th><fmt:message key="cost"/></th>
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
                            <a href="/parkingTicket/show?parkingTicketID=<c:out value='${currentTicket.parkingTicketID}'/>">
                                <fmt:message key="details"/></a> &nbsp;&nbsp;&nbsp;
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
            <button type="button" value="" name="BackToMenu"><fmt:message key="backToHome"/></button>
        </a>
    </div>
</div>
</div>
</body>
</html>
