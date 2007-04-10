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

</div>

<div id="paragraph">
    Edit user.
</div>



<form:form commandName="user" id="form">
    <table>
        <tr>
            <td>First Name:</td>
            <td><form:input path="firstName" /></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><form:input path="lastName" /></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="submit" value="Save Changes" />
            </td>
        </tr>
    </table>
</form:form>

<form id="mainForm" action="" method="POST">
    <table>
        <tr>
            <td>
                Id
            </td>
            <td>
                <input name="id" disabled="true"/>
            </td>
        </tr>
        <tr>
            <td>
                First name
            </td>
            <td>
                <spring:bind path="command.user.firstName">
                    <input type="text" name="<c:out value="${status.expression}"/>"
                    value="<c:out value="${status.value}"/>" /> <br/>
                    <c:forEach var="error" items="${status.errorMessages}">
                        <c:out value="${error}"/>
                        <br/>
                    </c:forEach>
                </spring:bind>
            </td>
        </tr>
        <tr>
            <td>
                Last name
            </td>
            <td>
                <spring:bind path="user.firstName">
                    <input type="text" name="<c:out value="${status.expression}"/>"
                    value="<c:out value="${status.value}"/>" /> <br/>
                    <c:forEach var="error" items="${status.errorMessages}">
                        <c:out value="${error}"/>
                        <br/>
                    </c:forEach>
                </spring:bind>
            </td>
        </tr>
        <tr>
            <td>
                First name
            </td>
            <td>
                <input name="name"/>
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>
                <input type="button" name="editNode" value="Save"/>
            </td>
        </tr>

    </table>

</form>
</body>
</html>
