<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        <spring:message code="user.title.list" />
    </title>



</head>


<body>


<jsp:include page="menu.jsp"/>


<div id="message">
    <c:out value="${message}"/>
</div>


<div id="content">


<form:form commandName="usercommand" id="userEditForm" cssClass="decoratedform">

<div id="formPanel">

    <div id="formPanelHeader">
        User
    </div>

    <table>
        <tr >
            <td><spring:message code="user.edit.form.user.label.firstname" /></td>
            <td><form:input path="user.firstName"/>*
            <br/>
            <form:errors path="user.firstName" cssClass="error"/>
                 
            </td>
        </tr>
        <tr >
            <td><spring:message code="user.edit.form.user.label.lastname" /></td>
            <td><form:input path="user.lastName"/></td>
        </tr>
        <tr >
            <td><spring:message code="user.edit.form.user.label.username" /></td>
            <td><form:input path="user.userName"/>*
                <br/><form:errors path="user.userName" cssClass="error"/>
            </td>
        </tr>
        <tr >
            <td><spring:message code="user.edit.form.user.label.password" /></td>
            <td><form:input path="user.password"/>*
            <br/>
            <form:errors path="user.password" cssClass="error"/>
            </td>
        </tr>
        <tr >
            <td><spring:message code="user.edit.form.user.label.role" /></td>
            <td>

            <form:select path="user.roles">
                <form:option value="-" label="---- Please Select -----"/>
                <form:options items="${roles}" itemValue="id" itemLabel="name"/>
            </form:select>
            </td>
        </tr>
    </table>
</div>

<div id="formPanel">
    <div id="formPanelHeader">
        Address
    </div>
    <table >
        <tr >
            <td><spring:message code="user.edit.form.user.address.label.email" /></td>
            <td><form:input path="user.address.email"/>*
                <br/><form:errors path="user.address.email" cssClass="error"/>
            </td>
        </tr>
        <tr >
            <td><spring:message code="user.edit.form.user.address.label.phone" /></td>
            <td><form:input path="user.address.phone"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.mobilephone" /></td>
            <td><form:input path="user.address.mobilephone"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.fax" /></td>
            <td><form:input path="user.address.fax"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.zip" /></td>
            <td><form:input path="user.address.zip"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.street" /></td>
            <td><form:input path="user.address.street"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.city" />¬</td>
            <td><form:input path="user.address.city"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.company" /></td>
            <td><form:input path="user.address.companyname"/></td>
        </tr>
        <tr>
            <td><spring:message code="user.edit.form.user.address.label.homepage" /></td>
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
