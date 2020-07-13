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
<nav style="width: 34%">
    <div style="float: left">
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
                            <input name="<c:out value='${slotPrice.key}'/>" value="<c:out value='${slotPrice.key}'/>">
                        </td>
                        <td>
                            <input type="number" placeholder="Price of ${slotPrice.key}"
                                   name="priceOf${slotPrice.key}"
                                   value="${slotPrice.value}">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div >
                <a href="" class="smallButtons" style="  margin-left: 35%;">
                    <button type="submit" value="" name="Save">Save</button>
                </a>
            </div>
        </form>
    </div>
        <div style="float: left;border: 2px solid deepskyblue;margin-top: 3%">
        <form action="/admin/addSlot" action="POST">

            <div style="float: left;">
            <select name="newSlotSize" required="required" style="width: 50%">
                <option value="" disabled selected>Choose appropriate slot</option>
                <option value="SMALL"> SMALL</option>
                <option value="MEDIUM"> MEDIUM</option>
                <option value="LARGE"> LARGE</option>
            </select>

            <select name="newSlotStatus" required="required" style="width: 50%;margin-top: 3%">
                <option value="" disabled selected>Choose appropriate status</option>
                <option value="VACANT"> VACANT</option>
                <option value="OCCUPIED"> OCCUPIED</option>
            </select>
            </div>

            <div style="float: left;width: 100%">
            <a href="" class="smallButtons" style="  margin-left: 25%;float: left">
                <button type="submit" value="" name="AddNewSlot">Add new slot</button>
            </a>
            </div>
        </form>
    </div>
</nav>

<div id="container" style="width: 60%;float: right">
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

<div style="float:left;width: 20%;margin-top: 5%">

    <a href="/home" style="float: left;margin-left: 35%;">
        <button type="button" value="" name="BackToMenu">Back to menu</button>
    </a>

</div>
</body>
</html>
