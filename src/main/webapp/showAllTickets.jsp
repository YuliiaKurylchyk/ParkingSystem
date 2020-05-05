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
</head>
<body>

<div id="container">
    <form action="" method="POST">

        <select name="entityName" id="entityName" onchange="this.form.submit()">
            <option value="parkingTicket">Parking tickets</option>
            <option value="vehicle">Vehicles</option>
            <option value="customer">Customers</option>
            <option value="parkingSlot">Parking slots</option>
        </select>

    </form>
</div>
</body>
</html>
