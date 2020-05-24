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

        <p><input placeholder="Name" name="name"
        <c:choose>
        <c:when test="${customer!=null}">
                  value="${customer.name}"
        </c:when>
        <c:otherwise>
                  value="${name}"
        </c:otherwise>
            </c:choose>
            >
        </p>
        <p><input placeholder="Surname" name="surname"
        <c:choose>
                  <c:when test="${customer!=null}">value="${customer.surname}"
        </c:when>
        <c:otherwise> value="${surname}"</c:otherwise>
            </c:choose>
            >
        </p>

        <p><input placeholder="Phone number" name="phoneNumber"

        <c:choose>
                  <c:when test="${customer!=null}">value="${customer.phoneNumber}"</c:when>
        <c:otherwise> value="${phoneNumber}"</c:otherwise>
            </c:choose>
            >
        </p>

        <c:if test="${requestScope.violations!=null}">

            <c:forEach items="${violations}" var="violation">
                <h3 style="color:red">${violation}.</h3>
            </c:forEach>
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
