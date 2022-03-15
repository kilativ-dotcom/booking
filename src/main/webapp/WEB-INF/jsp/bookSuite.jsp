<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page import="com.example.booking.constants.Constants" %>
<fmt:setLocale value="${sessionScope[Constants.ATTRIBUTE_SESSION_LOCALE]}"/>
<fmt:setBundle basename="jspText"/>

<html>
<head>
    <title><fmt:message key="title.bookSuite"/></title>
    <script>
        function correctForm(radioName){
            let radioButtons = document.getElementsByName(radioName)

            if (radioButtons.length<=0){
                alert("Cannot find which suite class was chosen.")
                return false
            }
            let hiddenInput = document.getElementsByName(radioName + "Id")
            if (hiddenInput.length<=0){
                alert("Cannot find element with name " + radioName + "Id")
                return false
            }

            let checkedValue =  document.querySelector("input[name= " + radioName + "]:checked").value
            let checkedId=0
            let i = 0
            for (let radio of radioButtons.values()) {
                if (radio.value === checkedValue){
                    checkedId = i+1
                    break
                }
                i++
            }

            if (checkedId===0){
                alert("Cannot find which radio was checked")
                return false
            }

            hiddenInput[0].setAttribute("value", checkedId.toString())
            let areDatesCorrect = correctDates()
            let isGuestsCorrect = correctGuests()
            let isCheckedIdCorrect = isPositiveInteger(checkedId.toString())
            if (!areDatesCorrect){
                alert("Check out date and/or check in date are not correct")
            }
            if (!isGuestsCorrect){
                alert("Amount if guests is not correct(not positive integer)")
            }
            if (!isCheckedIdCorrect){
                alert("Something is wrong with chosen suite class")
            }
            return true
        }


        function correctDates() {
            let checkIn = document.getElementById("checkInDate");
            let checkOut = document.getElementById("checkOutDate");
            let begin = checkIn.valueAsNumber
            let end = checkOut.valueAsNumber
            return isStringMatchDateFormat(checkIn.value) && isStringMatchDateFormat(checkOut.value) && (end-begin > 0)
        }

        function correctGuests() {
            let guests = document.getElementById("guests").value
            return isPositiveInteger(guests)
        }

        function isPositiveInteger(test){
            let num = Number(test)
            return (Number.isInteger(num) && num>0)
        }

        function isStringMatchDateFormat(test){
            return /^([0-9]{4}-[0-9]{2}-[0-9]{2})$/.test(test)
        }
    </script>
</head>
<body>
<jsp:include page="/${Constants.LOCALE_HEADER_PAGE_URL}"/>

<a href="${pageContext.request.contextPath}/welcome"><fmt:message key="button.back"/></a>
<c:set var="suiteClasses" value="${requestScope[Constants.ATTRIBUTE_SUITE_CLASSES]}"/>
<c:set var="checkInDate" value="${Constants.PARAMETER_CHECK_IN_DATE}"/>
<c:set var="checkOutDate" value="${Constants.PARAMETER_CHECK_OUT_DATE}"/>
<c:set var="guests" value="${Constants.PARAMETER_GUESTS}"/>
<c:set var="comment" value="${Constants.PARAMETER_COMMENT}"/>
<c:set var="suite" value="${Constants.PARAMETER_SUITE_CLASS}"/>

<my:possibleMessage text="${requestScope[Constants.ATTRIBUTE_BOOKING_MESSAGE]}"/>

<form id="submitForm" name="submitForm" onsubmit="return correctForm('${suite}')" method="post" accept-charset="UTF-8">
    <input type="hidden" name="${Constants.PARAM_COMMAND}" value="booking">
    <fieldset>
        <legend><fmt:message key="label.requestCreationForm"/></legend>
        <p><input id="checkInDate" type="date" name="${checkInDate}" value="${requestScope[Constants.ATTRIBUTE_BOOKING_PREV_CHECK_IN_DATE]}" required="required"/> <label for="${checkInDate}"><fmt:message key="label.checkInDate"/></label></p>
        <p><input id="checkOutDate" type="date" name="${checkOutDate}" value="${requestScope[Constants.ATTRIBUTE_BOOKING_PREV_CHECK_OUT_DATE]}" required="required"/> <label
                for="${checkOutDate}"><fmt:message key="label.checkOutDate"/></label>
        </p>
        <p>1<input id="guests" list="tickmarks" max="10" min="1" name="${guests}" type="range"
                   value="${requestScope[Constants.ATTRIBUTE_BOOKING_PREV_GUESTS_AMOUNT]}"/>10
            <datalist id="tickmarks">
                <option label="2" value="2">2</option>
                <option label="4" value="4">4</option>
                <option label="6" value="6">6</option>
                <option label="8" value="8">8</option>
                <option label="10" value="10">10</option>
            </datalist>
            &emsp; <label for="guests"><fmt:message key="label.guests"/></label></p>
        <p>&nbsp;</p>
        <p>
            <label for="comment"><fmt:message key="label.comment"/></label>
            <br>
            <textarea id="comment" name="${comment}" style="resize: none;" cols="20" form="submitForm"
                      rows="7"><c:out value="${requestScope[Constants.ATTRIBUTE_BOOKING_PREV_COMMENT]}"/></textarea></p>
        <fieldset>
            <legend><fmt:message key="label.suiteClass"/></legend>
            <input id="suiteClassId" type="hidden" name="${suite}Id" value="something">
            <c:set var="prevSuiteClassId" value="${requestScope[Constants.ATTRIBUTE_BOOKING_PREV_SUITE_CLASS_ID]}"/>
            <c:forEach var="suiteClass" items="${suiteClasses}" varStatus="status">
                <p>
                    <input type="radio"
                        <c:if test="${(empty prevSuiteClassId) || (prevSuiteClassId eq status.count)}"> checked="checked" </c:if>
                            name="${suite}" id="${suite}${status.count}"
                           value="${suiteClass.name}">
                    <label for="${suite}${status.count}"><c:out value="${suiteClass.name}"/></label>
                </p>
            </c:forEach>
        </fieldset>
        <input type="submit" value="<fmt:message key="button.bookSuite"/>"/></fieldset>
</form>

</body>
</html>
