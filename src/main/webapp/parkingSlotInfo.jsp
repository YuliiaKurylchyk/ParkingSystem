<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Parking slot information</title>

    <style type="text/css">
        <%@include file="WebContent/showPage-style.css" %>
    </style>

</head>
<h1>Parking slots price</h1>
<h3 style="color:cadetblue;"><c:out value="${requestScope.saved}"></c:out></h3>
<nav>
    <div style="width: 90%">
        <form method="POST" action="/admin/editPrice">
            <table>
                <thead>
                <tr>
                    <th>Size</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="slotPrice" items="${requestScope.slotPrices}">
                    <tr>
                        <td>
                          <input  name="<c:out value='${slotPrice.key}'/>" value="<c:out value='${slotPrice.key}'/>" >
                        </td>
                        <td>
                            <input type="number" placeholder="Price of ${slotPrice.value}" name="priceOf${slotPrice.key}"
                                   value="${slotPrice.value}">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <a href="" style="float:left;width:30%;margin-left: 5%;margin-top: 2%">
                <button type="submit" value="" name="Save">Save</button>
            </a>
        </form>
    </div>

    <div id="smallContainer" style="float:left;width: 90%">

        <h5>Add new slot</h5>
        <form action="/admin/addSlot" action="POST">
            <select name="newSlotSize" required ="required">
                <option value="" disabled selected>Choose appropriate slot</option>
                <option value="SMALL"> SMALL</option>
                <option value="MEDIUM"> MEDIUM</option>
                <option value="LARGE"> LARGE</option>
            </select>

            <select name="newSlotStatus" required="required">
                <option value="" disabled selected>Choose appropriate status</option>
                <option value="VACANT"> VACANT</option>
                <option value="OCCUPIED"> OCCUPIED</option>

            </select>

            <button type="submit" name="addNewParkingSlot">Add new parking slot</button>

        </form>
    </div>
</nav>

<div id="container" style="width: 70%;float: right">
    <table>
        <thead>
        <tr>
            <th>Slot ID</th>
            <th>Size</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="slot" items="${requestScope.allSlots}">
            <tr>
                <td>
                    <c:out value="${slot.parkingSlotID}"/>
                </td>
                <td>
                    <c:out value="${slot.sizeOfSlot}"/>
                </td>
                <td>
                    <c:out value="${slot.status}"/>
                </td>
                <td>

                    <c:if test="${slot.status=='VACANT'}">
                        <a href="/admin/deleteSlot?parkingSlotID=<c:out value='${slot.parkingSlotID}'/>&slotSize=<c:out value='${slot.sizeOfSlot}'/>">
                            Delete</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>


<div style="float:left">

    <a href="/home" style="float: left;width:30%;margin-left: 25%;margin-top: 2%">
        <button type="button" value="" name="BackToMenu">Back to menu</button>
    </a>

</div>
</body>
</html>
