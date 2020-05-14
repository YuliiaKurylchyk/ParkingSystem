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
<h1>Update</h1>

<div id="selectContainer">
    <h3>Search by</h3>
    <form action="searchPage.jsp" method="POST">
        <select name="option" onchange="this.form.submit();">
            <option value="" disabled selected>Select your option</option>
            <option value="parkingTicket">Parking ticket info</option>
            <option value="customer">Customer info</option>
            <option value="vehicle">Vehicle info</option>
        </select>
    </form>

    <form action="search" method="POST" id="searchForm">
        <input type="hidden" name="option" value="${param.option}">
        <c:choose>
            <c:when test="${param.option == 'parkingTicket'}">
                <p>Enter parking ticket ID</p>
                <input type="text" placeholder="ID" name="id">
                <input type="submit" value="${action=='update'?'Search':'Remove'}">
            </c:when>
            <c:when test="${param.option=='customer'}">
                <p>Enter customer ID or phone number</p>
                <input type="text" placeholder="phone number" name="phone_number">
                <input type="submit" value="${action=='update'?'Search':'Remove'}">
            </c:when>
            <c:when test="${param.option=='vehicle'}">
                <p>Enter licence plate of vehicle</p>
                <input type="text" placeholder="licence plate" name="licence_plate">
                <input type="submit" value="${action=='update'?'Search':'Remove'}">

            </c:when>
        </c:choose>
    </form>
</div>
<div id="container">

    <c:if test="${notFound!=null}">
        <h3 style="color:#ff0000">
            <c:out value="${notFound}"/>
        </h3>
    </c:if>

</div>
</body>
</html>
