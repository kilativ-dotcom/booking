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
    <title><fmt:message key="title.signup"/></title>
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
        <form name="signupForm" method="post" action="${pageContext.request.contextPath}/authentication/signup">
            <p><label for="loginInputId"><fmt:message key="label.email"/></label>:<input id="loginInputId" type="text" name="${Constants.PARAMETER_NAME}" value="${requestScope[Constants.ATTRIBUTE_PREV_NAME]}" required="required"></p>
            <p><label for="passwordInputId"><fmt:message key="label.password"/></label>:<input id="passwordInputId" type="password" name="${Constants.PARAMETER_PASSWORD}" required="required"></p>
            <input type="submit" value="<fmt:message key="label.signup"/>">
        </form>

        <form name="loginForm" method="post" action="${pageContext.request.contextPath}/authentication/login">
        </form>
        <p><a href="javascript:document.loginForm.submit()"><fmt:message key="button.gotoLogin"/></a> </p>
        <c:if test="${requestScope[Constants.SIGNUP_MESSAGE_BAD_CREDENTIALS]}">
            <h1 style="background-color: crimson; color: thistle">
                <fmt:message key="message.signingUpError"/>
            </h1>
        </c:if>
        <c:if test="${requestScope[Constants.AUTHENTICATION_EMPTY_PARAMETER]}">
            <h1 style="background-color: crimson; color: thistle">
                <fmt:message key="message.emptyLoginOrPassword"/>
            </h1>
        </c:if>
<%--        <my:possibleMessage text="${requestScope[Constants.SIGNUP_MESSAGE_BAD_CREDENTIALS]}"/>--%>
    </c:otherwise>
</c:choose>
<a href="${Constants.INDEX_PAGE_URL}"><fmt:message key="button.gotoHomePage"/></a>
</body>
</html>