<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title>User main page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<form name="bookSuiteForm" method="get" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="prepareBooking">
    <input type="submit" value="<fmt:message key="label.bookSuite"/>">
</form>

<form name="viewResponsesForm" method="get" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="viewUserResponses">
    <input type="submit" value="<fmt:message key="label.viewResponses"/>">
</form>

<form name="viewRequestsForm" method="get" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="viewUserRequests">
    <input type="submit" value="<fmt:message key="label.viewRequests"/>">
</form>

<c:set var="createdRequest" value="${requestScope[Constants.ATTRIBUTE_CREATED_REQUEST]}"/>
<c:if test="${createdRequest ne null}">
    <table>
        <tr>
            <th><fmt:message key="label.checkInDate"/></th>
            <th><fmt:message key="label.checkOutDate"/></th>
            <th><fmt:message key="label.guests"/></th>
            <th><fmt:message key="label.creator"/></th>
            <th><fmt:message key="label.suiteClass"/></th>
        </tr>
        <tr>
            <td><c:out value="${createdRequest.checkInDate}"/></td>
            <td><c:out value="${createdRequest.checkOutDate}"/></td>
            <td><c:out value="${createdRequest.guests}"/></td>
            <td><c:out value="${createdRequest.creator.email}"/></td>
            <td><c:out value="${createdRequest.suiteClass.name}"/></td>
        </tr>
    </table>
</c:if>

</body>
</html>
