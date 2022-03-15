<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.adminSuiteClasses"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>

<c:set var="suiteClasses" value="${requestScope[Constants.ATTRIBUTE_ALL_SUITE_CLASSES]}"/>
<c:if test="${suiteClasses ne null}">
    <c:choose>
        <c:when test="${suiteClasses.size() eq 0}">
            <h2 style="color: coral">
                <fmt:message key="label.noSuiteClasses"/>
            </h2>
        </c:when>
        <c:otherwise>
            <div style="text-align: center;">
                <table>
                    <tr>
                        <th><fmt:message key="label.number"/></th>
                        <th><fmt:message key="label.suiteClass"/></th>
                        <th><fmt:message key="button.change"/></th>
                        <th><fmt:message key="button.delete"/></th>
                    </tr>
                    <c:forEach var="suiteClass" items="${suiteClasses}">
                        <tr>
                            <td>${suiteClass.id}</td>
                            <td>${suiteClass.name}</td>
                            <td>
                                <button form="changeSuiteClassForm" name="${Constants.PARAMETER_SUITE_TO_CHANGE}" value="${suiteClass.id}"><fmt:message key="button.change"/></button>
                            </td>
                            <td>
                                <button form="deleteSuiteClassForm" name="${Constants.PARAMETER_SUITE_TO_DELETE}" value="${suiteClass.id}"><fmt:message key="button.delete"/></button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <p><h2 style="color: coral"><fmt:message key="label.totalSuiteClasses"/>:<c:out value="${suiteClasses.size()}"/></h2></p>

            <form id="changeSuiteClassForm" name="changeSuiteClassForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="changeSuiteClass">
            </form>
            <form id="deleteSuiteClassForm" name="deleteSuiteClassForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="deleteSuiteClass">
            </form>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
