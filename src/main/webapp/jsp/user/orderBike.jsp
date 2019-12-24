<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru-RU&amp;apikey=<ваш API-ключ>" type="text/javascript"></script>
    <script src="https://yandex.st/jquery/2.2.3/jquery.min.js" type="text/javascript"></script>
    <script src="js/placemark.js" type="text/javascript"></script>
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="property.text" var="textBundle" scope="session"/>

    <fmt:message bundle="${textBundle}" key="page.title.orderBike" var="title"/>
    <fmt:message bundle="${textBundle}" key="button.value.ordBike" var="ord_bike"/>
    <fmt:message bundle="${textBundle}" key="label.value.endTime" var="end_time"/>
    <fmt:message bundle="${textBundle}" key="label.value.id" var="id"/>
    <fmt:message bundle="${textBundle}" key="page.title.orderBike" var="title"/>
    <fmt:message bundle="${textBundle}" key="label.value.orderBike" var="label_order_bike"/>
    <fmt:message bundle="${textBundle}" key="label.value.chooseCard" var="label_choose_card"/>
    <title>${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <link href="css/styles.css" rel="stylesheet">
    <style>
        #map {
            position: relative;
            width: 1000px;
            height: 625px;
            /*border: 5px solid green;*/
        }
    </style>
</head>
<body>
<c:import url="/jsp/user/userHeader.jsp"/>
<div>
    <div id="map"></div>
    <c:import url="/jsp/common/footer.jsp"/>
</div>

<div class="order-bike-form">
    <form action="controller" method="get">
        <input type="hidden" name="command" value="order_bike"/> <br/>
        <div class="label-order-bike" align="center">
            <h1 class="text-info">${label_order_bike}</h1>
        </div>
        <div align="center">
            <input pattern="\d{1,6}" type="text" class="input" name="id">
        </div>
        <div class="order-bike-input-margin" align="center">
            <input pattern="^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$" type="text" name="end_time" class="input"/>
        </div>
        <div class="order-bike-input-margin" align="center">
            <input type="submit" value="${ord_bike}" class="btn btn-primary "/>
        </div>
        <div class="order-bike-input-margin" align="center">
            <h3 class="text-info">${label_choose_card}</h3>
        </div>
        <c:forEach var="card" items="${userCardsList}" varStatus="loop">
            <div class="order-bike-cards-placement" align="left">
                <input checked type="radio" name="serial_number" value="${card.serialNumber}" id="${card.id}">
                <label for="${card.id}">${card.serialNumber} (${card.balance} BYN)</label>
            </div>
        </c:forEach>
    </form>
    <div align="center"><h3 class="text-info">${message}</h3>
    </div>
</div>
</body>
</html>
