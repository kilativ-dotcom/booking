<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.login"/></title>
</head>
<body>
<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<c:set var="username" value="${sessionScope[Constants.ATTRIBUTE_USER].email}"/>
<c:choose>
    <c:when test="${username ne null}">
        <fmt:message key="message.alreadyLoggedIn" var="alreadyLoggedInMessage"/>
        <my:logoutForm message="${alreadyLoggedInMessage}${username}?"/>
    </c:when>
    <c:otherwise>
        <div style="text-align: center;">
            <form name="loginForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <p><fmt:message key="label.email"/>:<input type="text" name="${Constants.PARAMETER_NAME}"
                                value="${requestScope[Constants.ATTRIBUTE_PREV_NAME]}" required="required"></p>
                <p><fmt:message key="label.password"/>:<input type="password" name="${Constants.PARAMETER_PASSWORD}" required="required"></p>
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="login">
                <input type="submit" value="<fmt:message key="label.login"/>">
            </form>
            <form name="signupForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="prepareSignup">
            </form>
            <p><a href="javascript:document.signupForm.submit()"><fmt:message key="button.gotoSignUp"/></a></p>
        </div>
        <my:possibleMessage text="${requestScope[Constants.LOGIN_PAGE_MESSAGE]}"/>
    </c:otherwise>
</c:choose>
<a href="${Constants.INDEX_PAGE_URL}"><fmt:message key="button.gotoHomePage"/></a>
</body>
</html>