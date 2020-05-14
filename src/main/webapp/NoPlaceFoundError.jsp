<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 04.05.2020
  Time: 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>NoSuchPlaceFoundException</title>
</head>
<body>

    <h5><c:out value="${requestScope.noSlots}"></h5>

    <div style="float:right;">
    <a href="/"> <button type="button" value="" name="BackToMenu">Back to menu</button> </a>
    </div>
</body>
</html>
