<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.booking.constants.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Locale changer</title>

    <script>
        function reload(val){
            if (val!=='none'){
                let href = document.location.href;
                if (!href.includes('?')) {
                    href += '?';
                }
                document.location = href + '${Constants.PARAMETER_CHANGE_LOCALE}=' + val;
                // location.reload();
            }
        }
    </script>

</head>
<body>
<div style="text-align: right;">
<c:set var="paramLocale" value="${Constants.PARAMETER_CHANGE_LOCALE}"/>
<c:set var="locale" value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="jspText"/>

<select name="${Constants.PARAMETER_CHANGE_LOCALE}" onchange="reload(this.value)">
    <option value="none" hidden=""><fmt:message key="title.changeLocale"/></option>
    <option value="ru">русский</option>
    <option value="en">english</option>
</select>
</div>

</body>
</html>
