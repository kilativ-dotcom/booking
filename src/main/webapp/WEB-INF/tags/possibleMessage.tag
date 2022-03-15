<%@ attribute name="text" required="true" rtexprvalue="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p><h1 style="background-color: crimson; color: thistle">
    <c:out value="${text}"/>
</h1></p>