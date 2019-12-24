<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Bikes</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>
<c:import url="/jsp/admin/adminHeader.jsp"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="property.text" var="textBundle" scope="session"/>


<fmt:message bundle="${textBundle}" key="label.value.latitude" var="latitude"/>
<fmt:message bundle="${textBundle}" key="label.value.longitude" var="longitude"/>
<fmt:message bundle="${textBundle}" key="label.value.costPerHour" var="costPerHour"/>
<fmt:message bundle="${textBundle}" key="label.value.status" var="status"/>
<fmt:message bundle="${textBundle}" key="label.value.idOrganisation" var="idOrganisation"/>
<fmt:message bundle="${textBundle}" key="label.value.update" var="update"/>
<fmt:message bundle="${textBundle}" key="label.value.delete" var="delete"/>
<fmt:message bundle="${textBundle}" key="label.value.logout" var="logout"/>
<fmt:message bundle="${textBundle}" key="label.value.addBike" var="addBike"/>
<fmt:message bundle="${textBundle}" key="label.value.action" var="action"/>
<body>
<div align="center">
    <input type="hidden" name="command" value="show_all_bikes"/>
    <table class="table">
        <tr>
            <th>№</th>
            <th>${latitude}</th>
            <th>${longitude}</th>
            <th>${costPerHour}</th>
            <th>${status}</th>
            <th>${idOrganisation}</th>
            <th>
                <a href="controller?command=forward_to_add_bike" class="btn btn-success">
                    +${addBike}
                </a>
            </th>

        </tr>
        <c:forEach var="bike" items="${bikesList}" varStatus="loop">

            <tr>
                <td>${bike.id}</td>
                <td>${bike.latitude}</td>
                <td>${bike.longitude}</td>
                <td>${bike.costPerHour}</td>
                <td>${bike.status}</td>
                <td>${bike.idOrganisation}</td>
                <td>
                    <a class="btn btn-primary"
                       href="http://localhost:8080/controller?command=forward_to_update_bike&id=${bike.id}&oldLatitude=${bike.latitude}&oldLongitude=${bike.longitude}&oldCostPerHour=${bike.costPerHour}&oldStatus=${bike.status}&oldIdOrganisation=${bike.idOrganisation}">
                            ${update}
                    </a>
                    <a class="btn btn-danger"
                       href="http://localhost:8080/controller?command=delete_bike&id=${bike.id}">
                            ${delete}
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</body>
</html>
