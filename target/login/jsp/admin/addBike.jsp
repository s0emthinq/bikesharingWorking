<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="property.text" var="textBundle" scope="session"/>

    <fmt:message bundle="${textBundle}" key="label.value.latitude" var="latitude"/>
    <fmt:message bundle="${textBundle}" key="label.value.longitude" var="longitude"/>
    <fmt:message bundle="${textBundle}" key="label.value.costPerHour" var="costPerHour"/>
    <fmt:message bundle="${textBundle}" key="label.value.status" var="status"/>
    <fmt:message bundle="${textBundle}" key="label.value.idOrganisation" var="idOrganisation"/>
    <fmt:message bundle="${textBundle}" key="label.value.action" var="action"/>
    <fmt:message bundle="${textBundle}" key="label.value.update" var="update"/>
    <fmt:message bundle="${textBundle}" key="label.value.delete" var="delete"/>
    <fmt:message bundle="${textBundle}" key="label.value.logout" var="logout"/>
    <fmt:message bundle="${textBundle}" key="label.value.addBike" var="addBike"/>
    <title>${addBike}</title>
</head>
<body>
<c:import url="/jsp/admin/adminHeader.jsp"/>
<form name="addBikeForm" action="controller" method="post">
    <div class="deposit-form-placement">
        <div align="center">
            <h1 class="text-info">${addBike}</h1>
        </div>
        <input class="input" type="hidden" name="command" value="add_bike">
        <div align="center">  ${latitude}:
        </div>
        <div align="center">
            <input pattern="\d{2}.\d{2,4}" class="input" type="text" name="latitude"/>
        </div>
        <div align="center">
            ${longitude}:
        </div>
        <div align="center">
            <input pattern="\d{2}.\d{2,4}" class="input" type="text" name="longitude"/>
        </div>
        <div align="center">
            ${costPerHour}:
        </div>
        <div align="center">
            <input pattern="\d{0,2}(\.\d{1,2})?" class="input" type="text" name="cost_per_hour"/>
        </div>
        <div align="center">
            ${status}:
        </div>
        <div align="center">
            <input pattern="[0-1]" class="input" type="text" name="status"/>
        </div>
        <div align="center">
            ${idOrganisation}:
        </div>
        <div align="center">
            <input pattern="\d{1,6}" class="input" type="text" name="id_organisation"/>
        </div>
        <div align="center">
            <br/><input class="btn btn-success" type="submit" value="${addBike}"> <br/>
            <br/>
            <h3 class="text-info">${message}</h3>
        </div>
    </div>
</form>
</body>
</html>
