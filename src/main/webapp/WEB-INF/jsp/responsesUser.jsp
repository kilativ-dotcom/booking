<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.example.booking.constants.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.userResponses"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>

<c:set var="responses" value="${requestScope[Constants.ATTRIBUTE_RESPONSES_FOR_USER_REQUESTS]}"/>
<c:if test="${responses ne null}">
    <c:choose>
        <c:when test="${responses.size() eq 0}">
            <h2 style="color: coral"><fmt:message key="label.noResponses"/>, <c:out value="${sessionScope[Constants.ATTRIBUTE_USER].email}"/></h2>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th><fmt:message key="label.number"/></th>
                    <th><fmt:message key="label.requestCreator"/></th>
                    <th><fmt:message key="label.responseCreator"/></th>
                    <th><fmt:message key="label.isCancelled"/></th>
                    <th><fmt:message key="label.checkInDate"/></th>
                    <th><fmt:message key="label.checkOutDate"/></th>
                    <th><fmt:message key="label.suiteClass"/></th>
                    <th><fmt:message key="label.roomNumber"/></th>
                    <th><fmt:message key="label.totalCost"/></th>
                </tr>
                <c:forEach var="response" items="${responses}" varStatus="requestStatus">
                    <fmt:parseDate var="from" value="${response.request.checkInDate}" pattern="yyyy-MM-dd"/>
                    <fmt:parseDate var="to" value="${response.request.checkOutDate}" pattern="yyyy-MM-dd"/>
                    <c:set var="duration" value="${(to.time - from.time) / (1000 * 60 * 60 * 24)}"/>
                    <c:set var="stringCost" value="${response.suite.costPerNight * duration}"/>
                    <fmt:formatNumber var="cost" value="${response.suite.costPerNight * duration/100}" type="currency" currencySymbol="BYN"/>
                    <tr>
                        <td>${requestStatus.count}</td>
                        <td>${response.request.creator.email}</td>
                        <td>${response.creator.email}</td>
                        <td>${response.request.cancelled}</td>
                        <td><fmt:formatDate value="${from}" type="date" dateStyle="long"/></td>
                        <td><fmt:formatDate value="${to}" type="date" dateStyle="long"/></td>
                        <td>${response.suite.suiteClass.name}</td>
                        <td>${response.suite.roomNumber}</td>
                        <td>${cost}</td>
                    </tr>
                </c:forEach>
            </table>
            <p><h2 style="color: coral"><fmt:message key="label.totalResponses"/>:<c:out value="${responses.size()}"/></h2></p>
        </c:otherwise>
    </c:choose>
</c:if>

</body>
</html>
