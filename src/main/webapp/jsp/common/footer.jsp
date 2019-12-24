<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link href="css/styles.css" rel="stylesheet">

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="property.text" var="textBundle" scope="session"/>

<fmt:message bundle="${textBundle}" key="footer.projectName" var="projectName"/>
<fmt:message bundle="${textBundle}" key="footer.author" var="author"/>
<fmt:message bundle="${textBundle}" key="footer.contacts" var="contacts"/>
<footer>
    <div align="center" class="site-footer" >
        <div class="footer-and-header-text-margin">
        <p>${projectName}. ${author}. ${contacts}</p>
        </div>
    </div>
</footer>