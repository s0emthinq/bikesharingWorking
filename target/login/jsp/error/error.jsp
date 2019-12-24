<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head><title>Error Page</title></head>
<body>
<div align="center" class="deposit-form-placement">
    Request from ${pageContext.errorData.requestURI} is failed
    <br/>
    Servlet name or type: ${pageContext.errorData.servletName}
    <br/>
    Status code: ${pageContext.errorData.statusCode}
    <br/>
    Exception: ${pageContext.errorData.throwable}
    <br/>
    <a href="controller?command=login">Back to main page</a>
    <br/>
    ${wrongCommand}

</div>


</body>
</html>