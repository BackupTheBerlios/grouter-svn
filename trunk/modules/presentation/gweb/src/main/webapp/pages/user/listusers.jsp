<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>


<html>
<head>
    <title>
        <spring:message code="user.title.list"/>
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>


    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#searchUsersForm").ajaxForm({
                type: 'POST',
                target: '#mainContent'
            });
        });
     </script>
</head>
<body >


<div id="menuAction">
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
        <spring:message code="user.menu.action.list"/>
    </a>
    <a href="edit.do" params="lightwindow_width=400,lightwindow_height=400"
       class="lightwindow page-options iconlink"> <img src="/gweb/images/edit_add_24x24.png" alt="New">
        <spring:message code="user.menu.action.new"/>
    </a>

    <div id="form" align="right">
        <form id="searchUsersForm" action="/gweb/user/search.do" enctype="multipart/form-data" name="searchUsersForm"
              method="POST">
            <span id="searchHelp" title="Google like queries -
            To search for test, tests or tester, you can use the search: <br/>
            test*">?</span>
            <input id="searchText" value="" name="searchText" type="text">
            <input type="submit" value="Search" name="search" size="10"/>
        </form>
    </div>

        <c:out value="${message}"/>
</div>


<div id="message">
    <c:out value="${message}"/>
</div>


<div id="mainContent">
<form action="">
    <display:table name="${users}" export="true" id="row" class="dataTable" pagesize="20" cellspacing="0"
                   decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/user/list.do">
        <display:column property="id" titleKey="user.list.table.id" sortable="true" class="name"
                        headerClass="orderNumber" style="width: 10px;"/>
        <display:column property="auditInfo.createdOn" titleKey="user.list.table.createdOn" sortable="true" class="name"
                        headerClass="name"/>
        <display:column property="auditInfo.modifiedOn" titleKey="user.list.table.modifiedOn" sortable="true"
                        class="name" headerClass="name" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>
        <display:column property="userName" titleKey="user.list.table.username" sortable="true" class="name"
                        headerClass="name"/>
        <display:column property="firstName" titleKey="user.list.table.firstname" sortable="true"
                        class="orderNumber" headerClass="orderNumber" />
        <display:column property="lastName" titleKey="user.list.table.lastname" sortable="true" class="orderNumber"
                        headerClass="orderNumber"/>
        <display:column property="address.phone" titleKey="user.list.table.phone" sortable="true"
                        class="orderNumber" headerClass="orderNumber"/>
        <display:column property="address.email" titleKey="user.list.table.email" sortable="true"
                        class="orderNumber" headerClass="orderNumber"/>
        <display:column property="address.companyname" titleKey="user.list.table.companyname" sortable="true"
                        class="orderNumber" headerClass="orderNumber"/>
        <display:column title="Action" sortable="false">
            <a href="edit.do?id=${row.id}">Edit</a> |
            <a href="delete.do?id=${row.id}">Delete</a>
        </display:column>
    </display:table>
</form>
    </div>

</body>
</html>
