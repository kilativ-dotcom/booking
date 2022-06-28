
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.pageNotFound"/></title>
</head>
<body>
<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<h1 style="color: blueviolet"><fmt:message key="message.pageNotFound"/></h1>
</body>
</html>
