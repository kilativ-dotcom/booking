<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.adminSuites"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>

<c:set var="suites" value="${requestScope[Constants.ATTRIBUTE_ALL_SUITES]}"/>
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
                        <th><fmt:message key="button.change"/></th>
                        <th><fmt:message key="button.delete"/></th>
                    </tr>
                    <c:forEach var="suite" items="${suites}">
                        <fmt:formatNumber var="cost" value="${suite.costPerNight/100}" type="currency" currencySymbol="BYN"/>
                        <tr>
                            <td>${suite.id}</td>
                            <td>${suite.roomNumber}</td>
                            <td>${suite.maxGuests}</td>
                            <td>${suite.suiteClass.name}</td>
                            <td>${cost}</td>
                            <td>
                                <button form="changeSuiteForm" name="${Constants.PARAMETER_SUITE_TO_CHANGE}" value="${suite.id}"><fmt:message key="button.change"/></button>
                            </td>
                            <td>
                                <button form="deleteSuiteForm" name="${Constants.PARAMETER_SUITE_TO_DELETE}" value="${suite.id}"><fmt:message key="button.delete"/></button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <p><h2 style="color: coral"><fmt:message key="label.totalSuites"/>:<c:out value="${suites.size()}"/></h2></p>

            <form id="changeSuiteForm" name="changeSuiteForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="changeSuite">
            </form>
            <form id="deleteSuiteForm" name="deleteSuiteForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="deleteSuite">
            </form>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
