<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.welcome"/></title>
</head>
<body>
<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>

<c:set var="user" value="${sessionScope[Constants.ATTRIBUTE_USER]}"/>

<c:if test="${user eq null}">
    <c:redirect url="authentication/"/>
</c:if>
<h1 style="color: aquamarine; background-color: cornflowerblue">
    <fmt:message key="message.greetingPrefix"/><c:out value="${user.email}" default="unauthorized user"/><fmt:message key="message.greetingSuffix"/>
</h1>

<c:if test="${user ne null}">
    <c:choose>
        <c:when test="${user.admin}">
            <jsp:include page="/${Constants.WELCOME_ADMIN_PAGE_URL}"/>
            <fmt:message key="message.admin"/>
        </c:when>
        <c:otherwise>
            <jsp:include page="/${Constants.WELCOME_USER_PAGE_URL}"/>
            <fmt:message key="message.notAdmin"/>
        </c:otherwise></c:choose>

    <form name="logoutForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
        <input type="hidden" name="${Constants.PARAM_COMMAND}" value="logout">
        <input type="submit" value="<fmt:message key="label.logout"/>">
    </form>

</c:if>

<a href="${Constants.INDEX_PAGE_URL}"><fmt:message key="button.gotoHomePage"/></a>
</body>
</html>
