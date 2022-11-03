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
                    </tr>
                    <c:forEach var="suiteClass" items="${suiteClasses}">
                        <tr>
                            <td>${suiteClass.id}</td>
                            <td>${suiteClass.name}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <p><h2 style="color: coral"><fmt:message key="label.totalSuiteClasses"/>:<c:out value="${suiteClasses.size()}"/></h2></p>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
