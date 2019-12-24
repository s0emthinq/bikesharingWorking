<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="property.text" var="textBundle" scope="session"/>


<fmt:message bundle="${textBundle}" key="label.value.bikes" var="bikes"/>
<fmt:message bundle="${textBundle}" key="label.value.myOrders" var="myOrders"/>
<fmt:message bundle="${textBundle}" key="label.value.logout" var="logout"/>
<fmt:message bundle="${textBundle}" key="label.value.deposit" var="deposit"/>
<fmt:message bundle="${textBundle}" key="label.value.linkCard" var="linkCard"/>

<link href="css/styles.css" rel="stylesheet">

<div class="site-header" align="center">
    <header>
        <div class="footer-and-header-text-margin">
            <a class="bikes-link-placement" href="controller?command=forward_to_order_bike" style="color: white">
                <h3>${bikes}</h3></a>
            <a class="my-orders-link-placement" href="controller?command=show_all_user_orders" style="color:white">
                <h3>${myOrders}</h3></a>
            <a class="deposit-link-placement" href="controller?command=forward_to_make_deposit" style="color: white">
                <h3>${deposit}</h3></a>
            <a class="linkCard-link-placement" href="controller?command=forward_to_link_card" style="color: white">
                <h3>${linkCard}</h3></a>
            <div class="image-placement">
                <img src="img/user.png" height="40px" width="40px" class="round">
            </div>
            <div class="logout-button-placement">
                <a href="controller?command=logout" style="color:white">${logout}</a>
            </div>
            <div class="current-username-placement">
                ${sessionScope.currentUser}
            </div>
        </div>
    </header>
</div>

