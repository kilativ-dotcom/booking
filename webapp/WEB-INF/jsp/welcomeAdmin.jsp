<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title>Admin main page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<form name="startAnswerRequestForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="startAnswerRequest">
    <input type="submit" value="<fmt:message key="label.answerRequest"/>">
</form>

<c:set var="createdResponse" value="${requestScope[Constants.ATTRIBUTE_CREATED_RESPONSE]}"/>
<c:if test="${createdResponse ne null}">
    <table>
        <tr>
            <th><fmt:message key="label.checkInDate"/></th>
            <th><fmt:message key="label.checkOutDate"/></th>
            <th><fmt:message key="label.guests"/></th>
            <th><fmt:message key="label.requestCreator"/></th>
            <th><fmt:message key="label.responseCreator"/></th>
            <th><fmt:message key="label.suiteClass"/></th>
            <th><fmt:message key="label.roomNumber"/></th>
        </tr>
        <tr>
            <td><c:out value="${createdResponse.request.checkInDate}"/></td>
            <td><c:out value="${createdResponse.request.checkOutDate}"/></td>
            <td><c:out value="${createdResponse.request.guests}"/></td>
            <td><c:out value="${createdResponse.request.creator.email}"/></td>
            <td><c:out value="${createdResponse.creator.email}"/></td>
            <td><c:out value="${createdResponse.request.suiteClass.name}"/></td>
            <td><c:out value="${createdResponse.suite.roomNumber}"/></td>
        </tr>
    </table>
</c:if>


<form name="viewResponsesForm" method="post" action="${pageContext.request.contextPath}/view/responses">
    <input type="submit" value="<fmt:message key="label.viewResponses"/>">
</form>

<form name="viewRequestsForm" method="post" action="${pageContext.request.contextPath}/view/requests">
    <input type="submit" value="<fmt:message key="label.viewRequests"/>">
</form>

<form name="viewUsersForm" method="post" action="${pageContext.request.contextPath}/view/users">
    <input type="submit" value="<fmt:message key="label.viewUsers"/>">
</form>

<form name="viewSuitesForm" method="post" action="${pageContext.request.contextPath}/view/suites">
    <input type="submit" value="<fmt:message key="label.viewSuites"/>">
</form>

<form name="viewSuiteClassesForm" method="post" action="${pageContext.request.contextPath}/view/suiteClasses">
    <input type="submit" value="<fmt:message key="label.viewSuiteClasses"/>">
</form>

</body>
</html>
