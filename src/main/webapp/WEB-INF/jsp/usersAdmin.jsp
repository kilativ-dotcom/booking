<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>
<html>
<head>
    <title><fmt:message key="title.adminUsers"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script>
        function newUserRow(){
            let divWithNewUsers = document.getElementById("divWithNewUsers")
            divWithNewUsers.style.display="block"

            let tableBody = divWithNewUsers.children[0].children[0]

            let row = document.createElement('tr')
            let removeTD = document.createElement('td')
            let emailTD = document.createElement('td')
            let passwordTD = document.createElement('td')
            let isAdminTD = document.createElement('td')
            let removeButton = document.createElement('button')
            let emailInput = document.createElement('input')
            let passwordInput = document.createElement('input')
            let isAdminInput = document.createElement('input')
            let isAdminSelect = document.createElement('select')
            let adminOption = document.createElement('option')
            let notAdminOption = document.createElement('option')

            tableBody.appendChild(row)
            row.append(removeTD, emailTD, passwordTD, isAdminTD)
            removeTD.appendChild(removeButton)
            emailTD.appendChild(emailInput)
            passwordTD.appendChild(passwordInput)
            isAdminTD.appendChild(isAdminSelect)
            isAdminSelect.append(notAdminOption, adminOption)

            emailInput.name = "${Constants.PARAMETER_NEW_USERS_TABLE_EMAIL}"
            passwordInput.name = "${Constants.PARAMETER_NEW_USERS_TABLE_PASSWORD}"
            isAdminSelect.name = "${Constants.PARAMETER_NEW_USERS_TABLE_IS_ADMIN}"
            adminOption.value = "1"
            adminOption.innerHTML = "<fmt:message key="label.yes"/>"
            notAdminOption.value = "0"
            notAdminOption.innerHTML = "<fmt:message key="label.no"/>"

            removeButton.innerHTML = "-"
            removeButton.onclick = function () {
                removeUserRow(row)
            }
        }

        function removeUserRow(row){
            let parent = row.parentNode
            if (parent.children.length===2){
                document.getElementById("divWithNewUsers").style.display = "none"
            }
            parent.removeChild(row)
        }
    </script>
</head>
<body>

<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>
<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>

<c:set var="users" value="${requestScope[Constants.ATTRIBUTE_ALL_USERS]}"/>
<c:if test="${users ne null}">
    <c:choose>
        <c:when test="${users.size() eq 0}">
            <h2 style="color: coral">
                <fmt:message key="label.noUsers"/>
            </h2>
        </c:when>
        <c:otherwise>
            <div style="text-align: center;">
                <table>
                    <tr>
                        <th><fmt:message key="label.number"/></th>
                        <th><fmt:message key="label.email"/></th>
                        <th><fmt:message key="label.isAdmin"/></th>
                        <th><fmt:message key="button.change"/></th>
                        <th><fmt:message key="button.delete"/></th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.email}</td>
                            <td>${user.admin}</td>
                            <td>
                                <button form="changeUserForm" name="${Constants.PARAMETER_USER_TO_CHANGE}" value="${user.id}"><fmt:message key="button.change"/></button>
                            </td>
                            <td>
                                <button form="deleteUserForm" name="${Constants.PARAMETER_USER_TO_DELETE}" value="${user.id}"><fmt:message key="button.delete"/></button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <p><h2 style="color: coral"><fmt:message key="label.totalUsers"/>:<c:out value="${users.size()}"/></h2></p>

            <form id="changeUserForm" name="changeUserForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="changeUser">
            </form>
            <form id="deleteUserForm" name="deleteUserForm" method="post" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
                <input type="hidden" name="${Constants.PARAM_COMMAND}" value="deleteUser">
            </form>
        </c:otherwise>
    </c:choose>
</c:if>
<form method="post" name="createUsersForm" action="${Constants.FRONT_CONTROLLER_SERVLET_VALUE}">
    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="createUsers">
    <input type="button" name="newUserButton" value="+" onclick="newUserRow()">
    <div id="divWithNewUsers" style="display: none">
        <table>
            <tr>
                <th></th>
                <th><fmt:message key="label.email"/></th>
                <th><fmt:message key="label.password"/></th>
                <th><fmt:message key="label.isAdmin"/></th>
            </tr>
        </table>
        <input type="submit" value="<fmt:message key="button.change"/>">
    </div>
</form>

</body>
</html>
