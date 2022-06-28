<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.suitableSuites"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>


<c:set var="suites" value="${requestScope[Constants.ATTRIBUTE_SUITABLE_SUITES]}"/>
<c:if test="${suites ne null}">
    <c:choose>
        <c:when test="${suites.size() eq 0}">
            <h2 style="color: coral">
                <fmt:message key="label.noSuites"/>
            </h2>
        </c:when>
        <c:otherwise>
            <div style="text-align: center;">
                <table>
                    <tr>
                        <th><fmt:message key="label.number"/></th>
                        <th><fmt:message key="label.roomNumber"/></th>
                        <th><fmt:message key="label.maxGuests"/></th>
                        <th><fmt:message key="label.suiteClass"/></th>
                        <th><fmt:message key="label.costPerNight"/></th>
                        <th><fmt:message key="button.answer"/></th>
                    </tr>
                    <c:forEach var="suite" items="${suites}" varStatus="suiteStatus">
                        <fmt:formatNumber var="cost" value="${suite.costPerNight/100}" type="currency"/>
                        <tr>
                            <td>${suite.id}</td>
                            <td>${suite.roomNumber}</td>
                            <td>${suite.maxGuests}</td>
                            <td>${suite.suiteClass.name}</td>
                            <td>${cost}</td>
                            <td>
                                <button form="selectSuiteForRequestForm" name="${Constants.PARAMETER_SUITE_FOR_REQUEST}" value="${suite.id}"><fmt:message key="button.answer"/></button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <p><h2 style="color: coral"><fmt:message key="label.totalRequests"/>:<c:out value="${suites.size()}"/></h2></p>

            <form id="selectSuiteForRequestForm" name="selectSuiteForRequestForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="selectSuiteForRequest">
                <input type="hidden" name="${Constants.PARAMETER_REQUEST_FOR_SUITE}" value="${requestScope[Constants.ATTRIBUTE_REQUEST_ID]}">
            </form>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
