<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.sessionLocale}"/>
<fmt:setBundle basename="customerShow"/>
<html>
<head>
    <title>The list of customers</title>
    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>
</head>
<body>
<h1 style="color: deepskyblue"><fmt:message key="allCustomer"/></h1>
<div id="container">
    <table>
        <thead>
        <tr>
            <th><fmt:message key="customerID"/></th>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="surname"/></th>
            <th><fmt:message key="phoneNumber"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="customer" items="${requestScope.allCustomers}">
            <tr>
                <td>
                    <c:out value="${customer.customerID}"/>
                </td>
                <td>
                    <c:out value="${customer.name}"/>
                </td>
                <td>
                    <c:out value="${customer.surname}"/>
                </td>
                <td>
                    <c:out value="${customer.phoneNumber}"/>
                </td>
                <td>
                    <a href="/parkingTicket/showByCustomer?customerID=<c:out value='${customer.customerID}'/>"><fmt:message key="details"/></a> &nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
