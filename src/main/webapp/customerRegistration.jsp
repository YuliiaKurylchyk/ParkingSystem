<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 05.05.2020
  Time: 16:11
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
<h1>Parking ticket creation</h1>
<form action="customerCreation" method="post">
        <h3>Customer information: </h3>
        <p><input placeholder="Name" name="name" ></p>
        <p><input placeholder="Surname" name="surname" ></p>
        <p><input placeholder="Phone number" name="phone_number" ></p>
    </div>

    <div style="overflow:auto;">
        <div style="float:right;">
            <button type="submit" value="creationCustomer">Get parking ticket!</button>
        </div>
    </div>
</form>

</body>
</html>
