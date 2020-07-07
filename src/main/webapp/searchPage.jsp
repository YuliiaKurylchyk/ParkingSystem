<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="search"/>
<html>
<head>
    <title>Search</title>

    <style type="text/css">
        <%@include file="WebContent/searchPage-style.css" %>
    </style>

</head>
<body>
<h1><fmt:message key="toSearch"/></h1>
<h3><fmt:message key="searchBy"/></h3>
<div id="selectContainer">
    <form action="/searchPage.jsp" method="GET">
        <select name="option" onchange="this.form.submit();">
            <option value="" disabled selected><fmt:message key="select"/></option>
            <option value="parkingTicket" ${param.option == 'parkingTicket' ? 'selected="selected"':''}  ><fmt:message key="ticketID"/></option>
            <option value="customer" ${param.option == 'customer' ? 'selected="selected"':''}><fmt:message key="cus"/></option>
            <option value="vehicle" ${param.option == 'vehicle' ? 'selected="selected"':''}><fmt:message key="veh"/></option>
        </select>
    </form>
</div>
<div id="searchContainer">
    <form action="/${param.option}/get" method="GET" id="searchForm">
        <input type="hidden" name="option" value="${param.option}">
        <c:choose>
            <c:when test="${param.option == 'parkingTicket'}">
                <p><fmt:message key="enterTicket"/></p>
                <input type="text" placeholder="<fmt:message key="ticketID"/>" name="parkingTicketID">
                <input type="submit" class="searchButton" value="Search">
            </c:when>
            <c:when test="${param.option=='customer'}">
                <p><fmt:message key="enterCustomer"/></p>
                <input type="text" placeholder="<fmt:message key="phoneNumber"/>" name="phoneNumber">
                <input type="submit" class="searchButton"  value="Search">
            </c:when>
            <c:when test="${param.option=='vehicle'}">
                <p><fmt:message key="enterVehicle"/></p>
                <input type="text" placeholder="<fmt:message key="licensePlate"/>" name="vehicleID">
                <input type="submit" class="searchButton" value="<fmt:message key="searchTicket"/>">
            </c:when>
        </c:choose>
    </form>
</div>

<c:if test="${requestScope.NotFound!=null}">
<div id="container">
        <h3 style="color:#ff0000">
            <c:out value="${requestScope.NotFound}"/>
        </h3>
</div>
</c:if>

</body>
</html>
