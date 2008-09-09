<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>


<html>
<head>
    <title>
        <spring:message code="job.title.list"/>
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="menujob.jsp"/>
<div id="mainContent">
    <form action="">
        <display:table name="${jobs}" export="true" id="row" class="dataTable" pagesize="5" cellspacing="0"
                       decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/job/list.do">
            <display:column property="id" titleKey="job.list.table.id" sortable="true" class="name"
                            headerClass="name"/>
            <display:column  titleKey="job.list.table.status" sortable="true">
                        <c:if test="${row.jobState.id eq 1}"><img src="/gweb/images/icon_notstarted_15x15.gif"  /></c:if>
                        <c:if test="${row.jobState.id eq 2}"><img src="/gweb/images/icon_running_15x15.png"  /></c:if>
                        <c:if test="${row.jobState.id eq 3}"><img src="/gweb/images/icon_success_15x15.gif"  /></c:if>
                        <c:if test="${row.jobState.id eq 4}"><img src="/gweb/images/icon_stopped_15x15.gif"  /></c:if>
                        <c:if test="${row.jobState.id eq 5}"><img src="/gweb/images/icon_error_15x15.gif"  /></c:if>
            </display:column>
            <display:column property="startedOn" titleKey="job.list.table.startedon" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="displayName" titleKey="job.list.table.displayName" sortable="true"
                            class="orderNumber" headerClass="orderNumber" />
            <display:column property="cronExpression" titleKey="job.list.table.cronExpression" sortable="true"
                            class="orderNumber" headerClass="orderNumber" />
            <display:column  title="Action" sortable="false" >
                <a href="edit.do?id=${row.id}" >Edit</a> |
                <a href="delete.do?id=${row.id}" >Delete</a>
            </display:column>
        </display:table>
    </form>
</div>
</body>
</html>
