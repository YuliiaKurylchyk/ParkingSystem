<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The list of parking slots</title>

    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>

</head>
<body>
<h1>Parking slots price</h1>
<h3 style="color:cadetblue;"><c:out value="${requestScope.saved}"></c:out></h3>
<div id="container" style="width: 50%">
    <form method="POST" action="/parkingSlot/update">
        <table>
            <thead>
            <tr>
                <th>Size</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="slot" items="${requestScope.allSlots}">
                <tr>
                    <td>
                        <c:out value="${slot.slotSize}"/>
                    </td>
                    <td>
                        <input placeholder="Price of ${slot.slotSize}" name="priceOf${slot.slotSize}"
                               value="${slot.price}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <a href="" style="float:left;width:30%;margin-left: 5%;margin-top: 2%">
        <button  type="submit" value="" name="Save">Save</button>
        </a>

        <a href="" style="float:left;width:30%;margin-left: 5%;margin-top: 2%">
            <button   name="addNewParkingSlot">Add new parking slot</button>
        </a>
        <a href="" style="float:left;width:30%;margin-left: 5%;margin-top: 2%">
            <button   name="deleteParkingSlot">Delete parking slot</button>
        </a>

        <a href="/home" style="float: left;width:30%;margin-left: 25%;margin-top: 2%"> <button  type="button" value="" name="BackToMenu">Back to menu</button>
       </a>

    </form>
</div>

</body>
</html>
