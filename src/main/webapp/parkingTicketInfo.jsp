<%@ page import="com.kurylchyk.model.parkingTicket.ParkingTicket" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="TicketInfo"/>

<html>
<head>
    <title>Parking Ticket Information</title>
    <style type="text/css">
        <%@include file="WebContent/parkingInfo-style.css" %>
    </style>

</head>
<body>

<h1><fmt:message key="parkingTicketInfo"/></h1>

<h3 style="color:red;"><c:out value="${requestScope.deleted}"></c:out></h3>

<div class="container">
    <table>
        <tr>
            <td>
                <fmt:message key="parkingTicketID"/>
            </td>
            <td>
                <c:out value="${currentTicket.parkingTicketID}"/>
            <td></td>
        </tr>
        <tr>
            <td>
                <fmt:message key="vehicle"/>
            </td>
            <td><c:out value="${currentTicket.vehicle}"/></td>
            <td>
                <a href="/vehicle/edit?vehicleID=<c:out value='${currentTicket.vehicle.licensePlate}'/>&vehicleType=<c:out value='${currentTicket.vehicle.vehicleType}'/>"><fmt:message
                        key="edit"/></a>
                &nbsp;&nbsp;&nbsp;
            </td>
        </tr>

        <tr>
            <td>
                <fmt:message key="parkingSlot"/>
            </td>
            <td><c:out value="${currentTicket.parkingSlot}"/></td>
            <td>
                <c:if test="${currentTicket.status=='PRESENT'}">
                    <a href="/parkingSlot/showAll?&slotSize=<c:out value='${currentTicket.parkingSlot.sizeOfSlot}'/>
        &status='VACANT'"> <fmt:message
                            key="edit"/></a>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="customer"/>
            </td>
            <td><c:out value="${currentTicket.customer}"/></td>
            <td><a href="/customer/edit?customerID=<c:out value='${currentTicket.customer.customerID}'/>"><fmt:message
                    key="edit"/></a>
            </td>&nbsp;&nbsp;&nbsp;&nbsp;
        </tr>
        <tr>
            <td>
                <fmt:message key="arrivalTime"/>
            </td>
            <td><c:out value="${currentTicket.arrivalTime}"/></td>
            &nbsp;&nbsp;<td></td>
        <tr>
            <td><fmt:message key="leftTime"/></td>
            <td><c:out value="${currentTicket.departureTime}"/></td>
            <td></td>
        </tr>

        </tr>
        <tr>
            <td><fmt:message key="status"/></td>
            <td><c:out value="${currentTicket.status}"/></td>&nbsp;
            <td></td>
        </tr>
        <tr>
            <td><fmt:message key="cost"/></td>
            <td><c:out value="${currentTicket.cost}"/></td>&nbsp;
            <td></td>
        </tr>
    </table>
</div>


<div id="containerButton">
    <div style="float: left;margin-left: 10%;">
        <a href="/parkingTicket/getReceipt">
            <button type="button" value="" name="GetReceipt"><fmt:message key="getReceipt"/></button>
        </a>
    </div>
    <div style="float:left;">
        <a href="<%request.getContextPath();%>/parkingTicket/end">
            <button type="button" value="" name="BackToMenu"><fmt:message key="backToHome"/></button>
        </a>
    </div>
    <c:if test="${currentTicket.status=='PRESENT'}">
        <div style="float:left;">
            <a href="/parkingTicket/remove?parkingTicketID=<c:out value='${currentTicket.parkingTicketID}' />">
                <button type="button" value="" name="Remove"><fmt:message key="remove"/></button>
            </a>
        </div>
    </c:if>
    <c:if test="${currentTicket.status=='LEFT' and requestScope.deleted==null}">
        <div style="float:left;">
            <a href="/parkingTicket/delete?parkingTicketID=<c:out value='${currentTicket.parkingTicketID}' />">
                <button type="button" value="" name="deleteCompletely"><fmt:message key="delete"/></button>
            </a>
        </div>
    </c:if>
</div>
</body>
</html>
