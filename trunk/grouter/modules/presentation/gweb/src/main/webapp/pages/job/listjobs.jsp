<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->



<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Jobs :: List
    </title>
    <link href='<c:out value='${requestScope.CONTEXT_PATH}'/>/css/main.css' rel="stylesheet"/>
</head>
<body>


<div id="paragraph" nowrap>
    Jobs are scheduled via cron expression. A job can have different states.
</div>

<jsp:include page="jobmenu.jsp"/>


<div id="content">
    <form id="mainForm" action="">
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td></td>
                <td align="right">Number of jobs :
                    <c:out value="${jobsSize}"/>
                </td>
            </tr>
        </table>
        <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">

            <thead>

                <tr>
                    <th><a href="?sortBy=id">State</a></th>
                    <th><a href="?sortBy=firstName">Started</a></th>
                    <th><a href="?sortBy=firstName">Finished</a></th>
                    <th><a href="?sortBy=firstName">Name</a></th>
                    <th><a href="?sortBy=firstName">Cron</a></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${jobs}" var="object">
                <tr>


                    <td>
                        <c:if test="${object.jobState.id eq 1}"><img src="/gweb/images/icon_notstarted_15x15.gif"  /></c:if>
                        <c:if test="${object.jobState.id eq 2}"><img src="/gweb/images/icon_running_15x15.png"  /></c:if>
                        <c:if test="${object.jobState.id eq 3}"><img src="/gweb/images/icon_success_15x15.gif"  /></c:if>
                        <c:if test="${object.jobState.id eq 4}"><img src="/gweb/images/icon_stopped_15x15.gif"  /></c:if>
                        <c:if test="${object.jobState.id eq 5}"><img src="/gweb/images/icon_error_15x15.gif"  /></c:if>
                    </td>
                    <td>
                        <c:out value="${object.startedOn}"/>
                    </td>
                    <td>
                        <c:out value="${object.finishedAt}"/>
                    </td>
                    <td>
                        <c:out value="${object.displayName}"/>
                    </td>
                    <td>
                        <c:out value="${object.cronExpression}"/>
                    </td>
                    <td>
                        <a href='edit.do?id=<c:out value="${object.id}"/>' class="iconlink"> <img
                                src="/gweb/images/edit_24x24.png" alt="Delete" width="14"
                                height="14"> Edit </a> &nbsp;

                        <a href='delete.do?id=<c:out value="${object.id}"/>' class="iconlink"> <img
                                src="/gweb/images/remove_24x24.png" alt="Delete" width="14"
                                height="14">Delete </a>
                    </td>

                </tr>
                </c:forEach>
        </table>

    </form>
</div>
</body>
</html>





        