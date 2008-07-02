<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>



<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Jobs :: List
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>

</head>
<body>

<jsp:include page="menujob.jsp"/>

<div id="mainContent">
    <form action="">
        <display:table name="${jobs}" export="true" id="row" class="dataTable" pagesize="5" cellspacing="0"
                       decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/user/list.do">

            <display:column property="id" titleKey="jobs.list.table.id" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="id" titleKey="jobs.list.table.status" sortable="true" class="name"
                            headerClass="name">
                             <c:if test="${row.jobState.id eq 1}"><img src="/gweb/images/icon_notstarted_15x15.gif"  /></c:if>
                        <c:if test="${row.jobState.id eq 2}"><img src="/gweb/images/icon_running_15x15.png"  /></c:if>
                        <c:if test="${row.jobState.id eq 3}"><img src="/gweb/images/icon_success_15x15.gif"  /></c:if>
                        <c:if test="${row.jobState.id eq 4}"><img src="/gweb/images/icon_stopped_15x15.gif"  /></c:if>
                        <c:if test="${row.jobState.id eq 5}"><img src="/gweb/images/icon_error_15x15.gif"  /></c:if>
            </display:column>
            <display:column property="startedOn" titleKey="jobs.list.table.startedon" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="displayName" titleKey="user.list.table.finishedon" sortable="true"
                            class="orderNumber" headerClass="orderNumber" format="{0,date,short}"/>
            <!-- display:column sortable="false" paramId="id" paramProperty="id" -->
                <!--input type='checkbox' id='id'/ -->
            <!--/display:column -->
            <!-- display:column value="Edit" title="" sortable="false" href='edit.do' paramId="id" paramProperty="id"/ -->
            <display:column  title="Action" sortable="false" >
                <a href="edit.do?id=${row.id}" >Edit</a> |
                <a href="delete.do?id=${row.id}" >Delete</a>
            </display:column>

        </display:table>
    </form>
</div>


</body>
</html>





        