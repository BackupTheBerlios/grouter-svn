<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>
        <spring:message code="job.title.edit"/>
    </title>
</head>
<body>
<jsp:include page="menujob.jsp"/>
<div id="mainContentCentered">
    <form:form commandName="jobcommand" id="jobEditForm" cssClass="decoratedform">
        <div id="formPanel">
            <label>
                Job
            </label>
            <table>
                <tr>
                    <td>Type:</td>
                    <td>
                        <form:select path="job.jobType.id">
                            <form:option value="-" label="---- Please Select -----"/>
                            <form:options items="${jobTypes}" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td>Job name:</td>
                    <td><form:input path="job.displayName"/></td>
                    <td><form:errors path="job.displayName"/></td>
                </tr>
                <tr>
                    <td>Cron:</td>
                    <td><form:input path="job.cronExpression"/></td>
                    <td><form:errors path="job.cronExpression"/></td>
                </tr>
                <tr>
                    <td colspan="3" align="right">
                        <a href="/gweb/job/list.do"><input type="button" value="Cancel"/></a>
                        <input type="submit" value="Save Changes"/>
                    </td>
                </tr>
            </table>
        </div>
    </form:form>
</div>
</body>
</html>
