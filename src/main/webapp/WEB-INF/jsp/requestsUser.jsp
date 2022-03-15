<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.userRequests"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>
<c:set var="requests" value="${requestScope[Constants.ATTRIBUTE_REQUESTS_FOR_USER]}"/>
<c:if test="${requests ne null}">
    <c:choose>
        <c:when test="${requests.size() eq 0}">
            <h2 style="color: coral">
                <fmt:message key="label.noRequests"/>
            </h2>
        </c:when>
        <c:otherwise>

            <div style="text-align: center;">
                <table>
                    <tr>
                        <th><fmt:message key="label.number"/></th>
                        <th><fmt:message key="label.requestTime"/></th>
                        <th><fmt:message key="label.requestCreator"/></th>
                        <th><fmt:message key="label.isCancelled"/></th>
                        <th><fmt:message key="label.checkInDate"/></th>
                        <th><fmt:message key="label.checkOutDate"/></th>
                        <th><fmt:message key="label.suiteClass"/></th>
                        <th><fmt:message key="label.comment"/></th>
                        <th><fmt:message key="label.guests"/></th>
                        <th><fmt:message key="button.cancelRequest"/></th>
                    </tr>
                    <c:forEach var="request" items="${requests}" varStatus="requestStatus">
                        <tr>
                            <td>${requestStatus.count}</td>
                            <td>${request.requestTime}</td>
                            <td>${request.creator.email}</td>
                            <td>${request.cancelled}</td>
                            <td>${request.checkInDate}</td>
                            <td>${request.checkOutDate}</td>
                            <td>${request.suiteClass.name}</td>
                            <td>${request.comment}</td>
                            <td>${request.guests}</td>
                            <c:if test="${not request.cancelled}">
                                <td>
                                    <button form="cancelRequestForm" name="${Constants.PARAMETER_CANCELLED_REQUEST_ID}" value="${request.id}"><fmt:message key="button.cancel"/></button>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <p><h2 style="color: coral"><fmt:message key="label.totalRequests"/>:<c:out value="${requests.size()}"/></h2></p>

            <form id="cancelRequestForm" name="cancelRequestForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="cancelRequest">
            </form>
        </c:otherwise>
    </c:choose>
</c:if>

</body>
</html>
