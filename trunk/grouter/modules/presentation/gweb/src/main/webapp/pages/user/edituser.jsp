<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        User :: Edit
    </title>
</head>

<body>
<div id="menuAction">
    <a href="list.do"><img src="/images/view_detailed_24x24.png" alt="List"/></a>
    &nbsp;&nbsp;&nbsp;
    <a href="edit.do"> <img src="/gweb/images/edit_add_24x24.png" alt="New"></a>
</div>


<div id="paragraph">
    Edit user.
</div>


<form:form commandName="usercommand" id="form">
    <table>
        <tr>
            <td>User name:</td>
            <td><form:input path="user.userName"/></td>
            <td><form:errors path="user.userName"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:input path="user.password"/></td>
            <td><form:errors path="user.password"/></td>
        </tr>
        <tr>
            <td>Role:</td>
            <td>
                <form:select path="user.roles">
                    <form:option value="-" label="--Please Select"/>
                    <form:options items="${roles}" itemValue="id" itemLabel="name"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td>First name:</td>
            <td><form:input path="user.firstName"/></td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td><form:input path="user.lastName"/></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="submit" value="Save Changes"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
