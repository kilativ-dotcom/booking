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
    <title><fmt:message key="title.index"/></title>
</head>
<body>
<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<form name="toLoginForm" action="${pageContext.request.contextPath}/authentication/login" method="post">
    <input type="submit" value="<fmt:message key="button.gotoLogin"/>">
<%--    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="prepareLogin">--%>
</form>
<form name="toSignupForm" action="${pageContext.request.contextPath}/authentication/signup" method="post">
    <input type="submit" value="<fmt:message key="button.gotoSignUp"/>">
<%--    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="prepareSignup">--%>
</form>
<my:possibleMessage text="${requestScope[Constants.PARAMETER_WRONG_COMMAND]}"/>
</body>
</html>