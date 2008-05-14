<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        <spring:message code="user.title.list"/>
    </title>
</head>
<body>

<jsp:include page="menu.jsp"/>

<div id="message">
    <c:out value="${message}"/>
</div>

<div id="mainContentCentered">

<form:form commandName="usereditcommand" id="userEditForm" cssClass="decoratedform">
<div id="formPanel">

    <label>
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
        <tr>
            <td><spring:message code="user.edit.form.user.label.role"/></td>
            <td>


                <spring:bind path="usereditcommand.user.userRoles">
                    <select name="${status.expression}" multiple="multiple" style="width: 150px">
                        <c:forEach items="${allroles}" var="role">
                            <c:forEach items="${usereditcommand.user.userRoles}" var="currentRole">
                                <c:if test="${currentRole.role.id == role.id}">
                                    <c:set var="selected" value="true"/> 
                                </c:if>
                                </c:forEach>
                                <option value="${role.id}"
                                <c:if test="${selected}">selected="selected"</c:if>>
                                    ${role.name}
                                </option>
                                <c:remove var="selected"/>
                        </c:forEach>
                    </select>
                </spring:bind>
                <br/><form:errors path="user.userRoles" cssClass="error"/>
            </td>
        </tr>
    </table>
</div>

<div id="formPanel">
    <label >
        Address
    </label>
    <table>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.email"/></td>
            <td><form:input path="user.address.email"/>*
                <br/><form:errors path="user.address.email" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.phone"/></td>
            <td><form:input path="user.address.phone"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.mobilephone"/></td>
            <td><form:input path="user.address.mobilephone"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.fax"/></td>
            <td><form:input path="user.address.fax"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.zip"/></td>
            <td><form:input path="user.address.zip"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.street"/></td>
            <td><form:input path="user.address.street"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.city"/>¬</td>
            <td><form:input path="user.address.city"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.company"/></td>
            <td><form:input path="user.address.companyname"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.homepage"/></td>
            <td><form:input path="user.address.homepageUrl"/></td>
        </tr>
    </table>
</div>

<br/>

<div id="formPanelButtons">
    <table>
        <tr>
            <td colspan="3" align="right">
                <a href="/gweb/user/list.do"><input type="button" value="Cancel"/></a>

                <input type="submit" value="Save Changes"/>
            </td>
        </tr>
    </table>

</div>

</form:form>

</div>

</body>
</html>
