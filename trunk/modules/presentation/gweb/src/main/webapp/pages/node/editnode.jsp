<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Node :: Edit
    </title>
</head>
<body>


<div id="mainContentCentered">
    <form:form commandName="nodeEditCommand" id="nodeCommandForm" cssClass="decoratedform">

    <label >
        Node
    </label>
        <table>
            <tr>
                <td><spring:message code="user.edit.form.user.label.firstname"/></td>
                <td><form:input path="node.id" disabled="true"/></td>
                <td><form:errors path="node.id"cssClass="error"/></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><form:input path="node.displayName"/></td>
                <td><form:errors path="node.displayName"/></td>
            </tr>
            <tr>
                <td></td>
                <td align="right" colspan="3">
                    <input type="submit" value="Save"/>
                </td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>
