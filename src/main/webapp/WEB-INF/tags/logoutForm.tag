<%@ attribute name="message" required="true" rtexprvalue="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>

<p><c:out value="${message}"/></p>
<form name="logoutForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="logout">
    <input type="submit" value="<fmt:message key="label.logout"/>">
</form>