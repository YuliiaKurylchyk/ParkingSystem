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
    <style type="text/css">
        <%@include file="WebContent/errorPage-style.css" %>
    </style>
    <title>Error</title>
</head>
<body>

<div class="container">
    <h1>Error!</h1>

    <h3>
        <c:if test="${requestScope.exception!=null}"><c:out value="${requestScope.exception}"/></c:if>
      </h3>


    <h3>
        <c:if test="${requestScope.javax.servlet.error.exception!=null}"><c:out value="${requestScope.javax.servlet.error.exception}"/></c:if>
    </h3>
</div>
<div id="containerButton">
    <div style="float:left;">
        <a href="/">
            <button type="button" value="" name="BackToMenu">Back to menu</button>
        </a>
    </div>
</div>

</body>
</html>
