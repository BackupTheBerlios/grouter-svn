<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Settings :: Edit
    </title>
</head>

<body>



<div id="paragraph">
    Edit settings.
</div>


<div id="content">
    <form:form commandName="settingcommand" id="form">
        <table>
            <tr>
                <td>Choose router:</td>
                <td><select id="routerid" name="routerid" onchange="this.form.submit()">
                    <option value="">--- router ---</option>
                        <c:forEach items="${routers}" var="object">
                            <option
                                    <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if>
                                    value="${object.id}">${object.id}</option>
                        </c:forEach>
                </select>

                </td>
                <td></td>
            </tr>
            <tr>
                <td>Change language:</td>
                <td><form:input path="settingcommand.language"/></td>
                <td><form:errors path="settingcommand.language"/></td>
            </tr>
        </table>
    </form:form>

</div>

</body>
</html>
