<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.adminResponses"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>

<c:set var="responses" value="${requestScope[Constants.ATTRIBUTE_ALL_RESPONSES]}"/>
<c:if test="${responses ne null}">
    <c:choose>
        <c:when test="${responses.size() eq 0}">
            <h2 style="color: coral">
                <fmt:message key="label.noResponses"/>
            </h2>
        </c:when>
        <c:otherwise>
            <div style="text-align: center;">
                <table>
                    <tr>
                        <th><fmt:message key="label.number"/></th>
                        <th><fmt:message key="label.responseTime"/></th>
                        <th><fmt:message key="label.responseCreator"/></th>
                        <th><fmt:message key="label.requestCreator"/></th>
                        <th><fmt:message key="label.checkInDate"/></th>
                        <th><fmt:message key="label.checkOutDate"/></th>
                        <th><fmt:message key="label.comment"/></th>
                        <th><fmt:message key="label.guests"/></th>
                        <th><fmt:message key="label.roomNumber"/></th>
                        <th><fmt:message key="label.suiteClass"/></th>
                        <th><fmt:message key="label.totalCost"/></th>
                        <th><fmt:message key="label.isCancelled"/></th>
                    </tr>
                    <c:forEach var="response" items="${responses}">
                        <fmt:parseDate var="created" value="${response.request.requestTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        <fmt:parseDate var="from" value="${response.request.checkInDate}" pattern="yyyy-MM-dd"/>
                        <fmt:parseDate var="to" value="${response.request.checkOutDate}" pattern="yyyy-MM-dd"/>
                        <c:set var="duration" value="${(to.time - from.time) / (1000 * 60 * 60 * 24)}"/>
                        <fmt:formatNumber var="cost" value="${response.suite.costPerNight * duration/100}" type="currency" currencySymbol="BYN"/>
                        <tr>
                            <td>${response.id}</td>
                            <td><fmt:formatDate value="${created}" type="both" dateStyle = "medium" timeStyle = "medium"/></td>
                            <td>${response.creator.email}</td>
                            <td>${response.request.creator.email}</td>
                            <td><fmt:formatDate value="${from}" type="date" dateStyle="long"/></td>
                            <td><fmt:formatDate value="${to}" type="date" dateStyle="long"/></td>
                            <td>${response.request.comment}</td>
                            <td>${response.request.guests}</td>
                            <td>${response.suite.roomNumber}</td>
                            <td>${response.suite.suiteClass.name}</td>
                            <td>${cost}</td>
                            <td>${response.request.cancelled}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <p><h2 style="color: coral"><fmt:message key="label.totalResponses"/>:<c:out value="${responses.size()}"/></h2></p>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
