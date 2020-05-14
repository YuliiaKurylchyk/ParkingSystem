<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 16:11
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
<h1>Parking ticket creation</h1>
<c:if test="${customer != null}">
<form action="updatingServlet?option=updateCustomer" method="post">
</c:if>
<c:if test="${customer == null}">
<form action="creationServlet?action=customer" method="post">
    </c:if>

    <h3>Customer information: </h3>

    <p><input placeholder="Name" name="name" value="${customer.name}"></p>
    <p><input placeholder="Surname" name="surname" value="${customer.surname}"></p>

        <c:if test="${requestScope.badData!=null}">
            <h3 style="color:red">
                <c:out value="${requestScope.badData}"/>
            </h3>
        </c:if>
    <p><input placeholder="Phone number" name="phone_number" value="${customer.phoneNumber}"></p>

        <c:if test="${requestScope.badPhoneNumber!=null}">
            <h3 style="color:red">
                <c:out value="${requestScope.badPhoneNumber}"/>
            </h3>
        </c:if>
    </div>

    <div style="overflow:auto;">
        <div style="float:right;">

            <c:if test="${customer != null}">
                <button type="submit">Done</button>
            </c:if>

            <c:if test="${customer == null}">
                <button type="submit">Get parking ticket!</button>
            </c:if>

        </div>
    </div>
</form>

</body>
</html>
