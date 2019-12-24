<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="property.text" var="textBundle" scope="session"/>
<fmt:message bundle="${textBundle}" key="label.value.myOrders" var="myOrders"/>
<fmt:message bundle="${textBundle}" key="table.column.idBike" var="idBike"/>
<fmt:message bundle="${textBundle}" key="label.value.cost" var="cost"/>
<fmt:message bundle="${textBundle}" key="label.value.status" var="status"/>
<fmt:message bundle="${textBundle}" key="label.value.action" var="action"/>
<fmt:message bundle="${textBundle}" key="label.value.endTime" var="endTime"/>
<fmt:message bundle="${textBundle}" key="label.value.beginTime" var="beginTime"/>
<fmt:message bundle="${textBundle}" key="label.value.returnTime" var="returnTime"/>
<fmt:message bundle="${textBundle}" key="label.value.debt" var="debt"/>
<fmt:message bundle="${textBundle}" key="label.value.active" var="active"/>
<fmt:message bundle="${textBundle}" key="label.value.over" var="over"/>
<fmt:message bundle="${textBundle}" key="button.value.finish" var="finish"/>
<fmt:message bundle="${textBundle}" key="page.title.users" var="addBike"/>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <link href="css/styles.css" rel="stylesheet">
    <title>${myOrders}</title>
</head>
<body>
<c:import url="/jsp/user/userHeader.jsp"/>
<table border="1" align="center" class="table">
    <div align="center">
        <h1 class="text-secondary">${myOrders}</h1>
        <tr>
            <th>#</th>
            <th>${idBike}</th>
            <th>${beginTime}</th>
            <th>${endTime}</th>
            <th>${cost}</th>
            <th>${returnTime}</th>
            <th>${debt}</th>
            <th>${status}</th>
            <th>${action}</th>
        </tr>
        <c:forEach var="order" items="${userOrders}" varStatus="loop">

        <tr>
            <td>${loop.index + 1}</td>
            <td>${order.idBike}</td>
            <td>${order.beginTime}</td>
            <td>${order.endTime}</td>
            <td>${order.cost}</td>
            <td>${order.returnTime}</td>
            <td>${order.debt}</td>
            <c:choose>
                <c:when test="${order.status==0}">
                    <td>${active}</td>
                </c:when>
                <c:when test="${order.status==1}">
                    <td>${over}</td>
                </c:when>
            </c:choose>
            <td>
                <a class="btn btn-danger" href="controller?command=finish_order&id=${order.id}">
                        ${finish}
                </a>
            </td>
        </tr>
        </c:forEach>
</table>
<div align="center">
    <h3 class="text-info">${message}</h3>
</div>
<c:import url="../common/footer.jsp"/>
</div>

</body>
</html>
