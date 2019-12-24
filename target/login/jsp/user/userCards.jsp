<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="property.text" var="textBundle" scope="session"/>

    <fmt:message bundle="${textBundle}" key="page.title.userCards" var="title"/>
    <fmt:message bundle="${textBundle}" key="label.value.linkCard" var="linkCard"/>
    <fmt:message bundle="${textBundle}" key="label.value.serialNumber" var="serialNumber"/>
    <fmt:message bundle="${textBundle}" key="button.value.link" var="link"/>

    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
<c:import url="/jsp/user/userHeader.jsp"/>
<div class="deposit-form-placement">
    <form name="linkCardForm" method="POST" action="controller">
        <div align="center">
            <h1 class="text-info">${linkCard}</h1>
            <input type="hidden" name="command" value="link_card"/>
            <h4 class="text-info">${serialNumber}:</h4>
            <input pattern="\d{16}" type="text" name="serial_number" class="input"/>
            <br/><br/>
            <input type="submit" value="${link}" class="btn btn-primary"/>
        </div>
    </form>
    <br/>
    <c:forEach var="card" items="${userCardsList}" varStatus="loop">
        <div class="order-bike-cards-placement" align="center">
            <div align="center">
                <label>${card.serialNumber} (${card.balance} BYN)</label>
            </div>
        </div>
    </c:forEach>
    <br/>
    <div align="center">
        <h3 class="text-info">${message}</h3>
    </div>
</div>
<c:import url="/jsp/common/footer.jsp"/>
</body>
</html>
