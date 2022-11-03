
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
<c:set var="user" value="${sessionScope[Constants.ATTRIBUTE_USER]}"/>
<c:choose>
    <c:when test="${user ne null}">
        <a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.gotoHomePage"/></a>
    </c:when>
    <c:otherwise>
        <a href="${Constants.INDEX_PAGE_URL}"><fmt:message key="button.gotoHomePage"/></a>
    </c:otherwise>
</c:choose>
</body>
</html>
