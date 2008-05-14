<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        <spring:message code="router.title.list"/>
    </title>
</head>
<body>

<jsp:include page="menurouter.jsp"/>

<div id="message">
    <c:out value="${message}"/>
</div>

<div id="mainContentCentered">

<form:form commandName="usereditcommand" id="userEditForm" cssClass="decoratedform">
<div id="formPanel">

    <label >
        User
    </label>

    <table>
        <tr>
            <td><spring:message code="user.edit.form.user.label.firstname"/></td>
            <td><form:input path="user.firstName"/>*
                <br/>
                <form:errors path="user.firstName" cssClass="error"/>

            </td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.label.lastname"/></td>
            <td><form:input path="user.lastName"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.label.username"/></td>
            <td><form:input path="user.userName"/>*
                <br/><form:errors path="user.userName" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.label.password"/></td>
            <td><form:input path="user.password"/>*
                <br/>
                <form:errors path="user.password" cssClass="error"/>
            </td>
        </tr>

    </table>
</div>

<div id="formPanelButtons">
    <table>
        <tr>
            <td colspan="3" align="right">
                <a href="/gweb/router/list.do"><input type="button" value="Cancel"/></a>

                <input type="submit" value="Save Changes"/>
            </td>
        </tr>
    </table>

</div>

</form:form>

</div>

</body>
</html>
