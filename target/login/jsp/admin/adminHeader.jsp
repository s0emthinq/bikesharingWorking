<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="property.text" var="textBundle" scope="session"/>

<fmt:message bundle="${textBundle}" key="label.value.bikes" var="bikes"/>
<fmt:message bundle="${textBundle}" key="label.value.users" var="users"/>
<fmt:message bundle="${textBundle}" key="label.value.logout" var="logout"/>
<link href="css/styles.css" rel="stylesheet">
<div class="site-header" align="center">
    <header>
        <div class="footer-and-header-text-margin">
            <a class="bikes-admin-link-placement" href="controller?command=show_all_bikes" style="color:white">
                <h3>${bikes}</h3></a>
            <a class="users-link-placement" href="controller?command=show_all_users" style="color:white">
                <h3>${users}</h3></a>
            <div class="image-placement">
                <img src="img/admin.jpg" height="40px" width="40px" class="round">
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