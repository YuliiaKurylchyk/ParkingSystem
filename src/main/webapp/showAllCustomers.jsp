<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The list of customers</title>
    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>
</head>
<body>
<h3>All Customers</h3>
<div id="container">
    <table>
        <thead>
        <tr>
            <th>CustomerID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>phoneNumber</th>
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
                    <a href="/parkingTicket/showByCustomer?customerID=<c:out value='${customer.customerID}'/>">Show
                        details</a> &nbsp;&nbsp;&nbsp;
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
