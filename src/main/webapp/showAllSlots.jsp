<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="vehicleShow"/>
<html>
<head>
    <title>The list of parking slots</title>

    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>

</head>
<body>
<h1 style="color: deepskyblue">The list slots</h1>

<c:if test="${sessionScope.vehicle==null && requestScope.change==null}">
<div id="selectContainer">
    <form action="/parkingSlot/showAll" method="GET">
        <input type="radio" id="all" name="slotSize" value="ALL" onchange="this.form.submit();"
        ${param.slotSize=='ALL'?'checked':''}>
        <label for="all"> All </label>

        <input type="radio" id="small" name="slotSize" value="SMALL" onchange="this.form.submit();"
        ${param.slotSize=='SMALL'?'checked':''}>
        <label for="small"> Small </label>

        <input type="radio" id="medium" name="slotSize" value="MEDIUM" onchange="this.form.submit();"
        ${param.slotSize=='MEDIUM'?'checked':''}>
        <label for="medium"> Medium </label>

        <input type="radio" id="large" name="slotSize" value="LARGE" onchange="this.form.submit();"
        ${param.slotSize=='LARGE'?'checked':''}>
        <label for="large"> Large </label>
    </form>
</div>
</c:if>
<div id="container">
    <table>
        <thead>
        <tr>
            <th>Slot ID</th>
            <th>Size</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="slot" items="${requestScope.allSlots}">
            <tr>
                <td>
                    <c:out value="${slot.parkingSlotID}"/>
                </td>
                <td>
                    <c:out value="${slot.sizeOfSlot}"/>
                </td>
                <td>
                    <c:out value="${slot.status}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${param.info!=null}">
                            <c:if test="${slot.status=='OCCUPIED'}">
                            <a href="/parkingTicket/showBySlot?parkingSlotID=<c:out value='${slot.parkingSlotID}'/>&slotSize=<c:out value='${slot.sizeOfSlot}'/>">
                                Show details</a>
                            </c:if>
                        </c:when>

                        <c:when test="${requestScope.change!=null}">
                            <a href="/parkingSlot/change?parkingSlotID=<c:out value='${slot.parkingSlotID}'/>&slotSize=<c:out value='${slot.sizeOfSlot}'/>">
                                Choose</a> &nbsp;
                        </c:when>

                        <c:otherwise>
                            <a href="/parkingSlot/get?parkingSlotID=<c:out value='${slot.parkingSlotID}'/>&slotSize=<c:out value='${slot.sizeOfSlot}'/>">
                                Choose</a> &nbsp;
                            &nbsp;&nbsp;</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${param.info!=null}">
    <div id="containerButton">
        <div style="float:left;">
            <a href="/">
                <button type="button" value="" name="BackToMenu">Back home</button>
            </a>
        </div>
    </div>
    </c:if>
</div>
</body>
</html>
