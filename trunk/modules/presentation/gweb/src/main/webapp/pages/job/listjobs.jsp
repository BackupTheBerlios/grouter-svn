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
            <display:column property="startedOn" titleKey="jobs.list.table.startedon" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="firstName" titleKey="user.list.table.finishedon" sortable="true"
                            class="orderNumber" headerClass="orderNumber" format="{0,date,short}"/>
            <display:column property="lastName" titleKey="user.list.table.lastname" sortable="true" class="orderNumber"
                            headerClass="orderNumber"/>
            <display:column property="address.phone" titleKey="user.list.table.phone" sortable="true"
                            class="orderNumber" headerClass="orderNumber"/>
            <display:column property="address.email" titleKey="user.list.table.email" sortable="true"
                            class="orderNumber" headerClass="orderNumber"/>
            <display:column property="address.companyname" titleKey="user.list.table.companyname" sortable="true"
                            class="orderNumber" headerClass="orderNumber"/>
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





        