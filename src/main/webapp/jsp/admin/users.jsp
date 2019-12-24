<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="property.text" var="textBundle" scope="session"/>
    <fmt:message bundle="${textBundle}" key="table.column.login" var="login"/>
    <fmt:message bundle="${textBundle}" key="table.column.email" var="email"/>
    <fmt:message bundle="${textBundle}" key="table.column.role" var="role"/>
    <fmt:message bundle="${textBundle}" key="table.column.status" var="status"/>
    <fmt:message bundle="${textBundle}" key="table.column.image" var="image"/>
    <fmt:message bundle="${textBundle}" key="label.value.addUser" var="addUser"/>
    <fmt:message bundle="${textBundle}" key="label.value.action" var="action"/>
    <fmt:message bundle="${textBundle}" key="button.value.block" var="block"/>
    <fmt:message bundle="${textBundle}" key="button.value.unblock" var="unblock"/>
    <fmt:message bundle="${textBundle}" key="page.title.users" var="users"/>
    <fmt:message bundle="${textBundle}" key="column.value.adminRole" var="adminRole"/>
    <fmt:message bundle="${textBundle}" key="column.value.userRole" var="userRole"/>
    <fmt:message bundle="${textBundle}" key="column.value.active" var="active"/>
    <fmt:message bundle="${textBundle}" key="column.value.blocked" var="blocked"/>
    <title>${users}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
<c:import url="/jsp/admin/adminHeader.jsp"/>
<table class="table table-inverse">
    <tr>
        <th>#</th>
        <th>${login}</th>
        <th>${email}</th>
        <th>${role}</th>
        <th>${status}</th>
    </tr>
    <c:forEach var="user" items="${usersList}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <c:choose>
                <c:when test="${user.role==1}">
                    <td>${adminRole}</td>
                </c:when>
                <c:when test="${user.role==0}">
                    <td>${userRole}</td>
                </c:when>
            </c:choose>
            <td>
                <c:choose>
                    <c:when test="${user.status==0}">
                        ${active}
                    </c:when>
                    <c:when test="${user.status==1}">
                        ${blocked}
                    </c:when>
                </c:choose></td>
            <td>
                <c:choose>
                    <c:when test="${user.status==1}">
                        <a href="http://localhost:8080/controller?command=unblock_user&id=${user.id}"
                           class="btn btn-success">
                                ${unblock}
                        </a>
                    </c:when>
                    <c:when test="${user.status==0}">
                        <a href="http://localhost:8080/controller?command=block_user&id=${user.id}"
                           class="btn btn-danger">
                                ${block}
                        </a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<div align="center">
    <h3 class="text-info">${message}</h3>
</div>
</body>
<c:import url="/jsp/common/footer.jsp"/>
</html>
