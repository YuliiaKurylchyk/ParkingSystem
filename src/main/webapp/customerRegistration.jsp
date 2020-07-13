
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="customerReg"/>

<html>
<head>
    <title>Customer information</title>
    <style type="text/css">
        <%@include file="WebContent/creation-style.css" %>
    </style>

</head>
<body>
<h1><fmt:message key="parkingTicketCreation"/></h1>
<c:if test="${requestScope.customer != null}">
<form action="/customer/update" method="post">
    <input type="hidden" name="customerID" value="${param.customerID}">
    </c:if>
    <c:if test="${requestScope.customer == null}">
    <form action="/customer/create" method="post">
        </c:if>
        <h3><fmt:message key="customerInfo"/></h3>
        <p><input placeholder="<fmt:message key="name"/>" name="name" pattern="^\D*$" required

        <c:if test="${requestScope.customer!=null}">
                  value="${requestScope.customer.name}"></c:if>
        </p>
        <p><input placeholder="<fmt:message key="surname"/>" name="surname" pattern="^\D*$" required
                  <c:if test="${requestScope.customer!=null}">value="${requestScope.customer.surname}"> </c:if>
        </p>

        <p><input placeholder="<fmt:message key="phoneNumber"/>(+380...)" type="tel" pattern="^+380[0-9]{9}$" name="phoneNumber" required
                  <c:if test="${requestScope.customer!=null}">value="${requestScope.customer.phoneNumber}"</c:if>>
        </p>

        </div>

        <div style="overflow:auto;">
            <div style="float:right;">

                <c:if test="${requestScope.customer != null}">
                    <button type="submit"><fmt:message key="done"/></button>
                </c:if>

                <c:if test="${requestScope.customer == null}">
                    <button type="submit"><fmt:message key="getParkingTicket"/></button>
                </c:if>

            </div>
        </div>
    </form>

</body>
</html>
