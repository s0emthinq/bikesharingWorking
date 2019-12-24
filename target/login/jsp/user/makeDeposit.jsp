<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="property.text" var="textBundle" scope="session"/>

    <fmt:message bundle="${textBundle}" key="label.value.deposit" var="deposit"/>
    <fmt:message bundle="${textBundle}" key="label.value.serialNumber" var="serialNumber"/>
    <fmt:message bundle="${textBundle}" key="label.value.amount" var="amount"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <link href="css/styles.css" rel="stylesheet">
    <title>${deposit}</title>
</head>
<body>
<c:import url="/jsp/user/userHeader.jsp"/>
<div class="deposit-form-placement">
<form name="depositForm" method="POST" action="controller">
    <div align="center">
        <h1 class="text-info">${deposit}</h1>
    <input type="hidden" name="command" value="make_deposit"/>
        <h4 class="text-info">${serialNumber}:</h4>
    <input pattern="\d{16}" type="text" name="serial_number" class="input"/>
        <br/> <h4 class="text-info">${amount}: </h4>
    <input pattern="\d{0,3}(\.\d{1,2})?" type="text" name="amount" class="input"/>
    <br/>
    <br/>
    <input type="submit" value="${deposit}" class="btn btn-primary"/>
    </div>
</form>
<br/>
    <h3 class="text-info">${message}</h3>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</body>
</html>
