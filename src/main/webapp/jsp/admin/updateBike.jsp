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
    <title>Title</title>


    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="property.text" var="textBundle" scope="session"/>

    <fmt:message bundle="${textBundle}" key="label.value.updatingBike" var="updatingBike"/>
    <fmt:message bundle="${textBundle}" key="label.value.latitude" var="latitude"/>
    <fmt:message bundle="${textBundle}" key="label.value.longitude" var="longitude"/>
    <fmt:message bundle="${textBundle}" key="label.value.costPerHour" var="costPerHour"/>
    <fmt:message bundle="${textBundle}" key="label.value.status" var="status"/>
    <fmt:message bundle="${textBundle}" key="label.value.idOrganisation" var="idOrganisation"/>
    <fmt:message bundle="${textBundle}" key="button.value.update" var="update"/>

    <fmt:message bundle="${textBundle}" key="button.value.changeLanguage" var="change_language"/>
</head>
<body>
<c:import url="/jsp/admin/adminHeader.jsp"/>
<form name="updateBikeForm" action="controller" method="get">
    <div class="deposit-form-placement">
        <input type="hidden" name="command" value="update_bike">
        <input type="hidden" name="id" readonly value="${param.id}">
        <div align="center">
            <h3 class="text-info">${updatingBike} #${param.id}</h3>
        </div>
        <br/>
        <div align="center">
            ${latitude}:
        </div>
        <div align="center">
            <input pattern="\d{2}.\d{2,4}" class="input" type="text" name="new_latitude" value="${param.oldLatitude}"/>
        </div>
        <div align="center">
            ${longitude}:
        </div>
        <div align="center">
            <input pattern="\d{2}.\d{2,4}" class="input" type="text" name="new_longitude"
                   value="${param.oldLongitude}"/>
        </div>
        <div align="center"> ${costPerHour}:
        </div>
        <div align="center">
            <input pattern="\d{0,2}(\.\d{1,2})?" class="input" type="text" name="new_cost_per_hour"
                   value="${param.oldCostPerHour}"/>
        </div>
        <div align="center">
            ${status}:
        </div>
        <div align="center">
            <input pattern="[0-1]" class="input" type="text" name="new_status" value="${param.oldStatus}"/>
        </div>
        <div align="center">
            ${idOrganisation}:
        </div>
        <div align="center">
            <input pattern="\d{1,6}" class="input" type="text" name="new_id_organisation" value="${param.oldIdOrganisation}"/>

        </div>
        <div align="center">
            <br/>
            <input class="btn btn-success" type="submit" value=${update}>
        </div>
        <div align="center">
            <br/>
            <h3 class="text-info">${message}</h3>
        </div>
    </div>
</form>
</body>
</html>
