<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <link href="css/styles.css" rel="stylesheet">
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="property.text" var="textBundle" scope="session"/>
    <fmt:message bundle="${textBundle}" key="button.value.changeLanguage" var="change_language"/>
    <fmt:message bundle="${textBundle}" key="button.value.register" var="register"/>
    <fmt:message bundle="${textBundle}" key="label.value.login" var="name"/>
    <fmt:message bundle="${textBundle}" key="button.value.login" var="loginButton"/>
    <fmt:message bundle="${textBundle}" key="label.value.login" var="loginLabel"/>
    <fmt:message bundle="${textBundle}" key="label.value.password" var="password"/>
    <fmt:message bundle="${textBundle}" key="label.value.welcomeMessage" var="welcomeMessage"/>

    <title>${loginLabel}</title></head>
<body>


<div class="deposit-form-placement">
    <div align="center">
        <h1 class="text-info">${welcomeMessage}</h1>
        <form name="loginForm" method="POST" action="controller">
            <input class="input" type="hidden" name="command" value="login"/>
            ${loginLabel}:<br/>
            <input class="input" type="text" name="login"/>
            <br/>${password}:<br/>
            <input class="input" type="password" name="password"/>
            <br/>
            <br/>
            <input type="submit" value="${loginButton}" class="btn btn-success"/>
        </form>
        <%--<form action="controller" method="get">--%>
            <%--<input type="hidden" name="command" value="forward_to_register"/>--%>
            <%--<input type="submit" value="${register}" class="text-secondary"/>--%>
        <%--</form>--%>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="change_language"/>
            <input type="submit" value="${change_language}" class="btn btn-primary"/>
        </form>
        <h4 ><a class="text-secondary" href="controller?command=forward_to_register">${register}</a></h4>
        <h3 class="text-info">${message}</h3>
    </div>

</div>
</body>

<c:import url="/jsp/common/footer.jsp"/>
</html>