<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <title>
        <spring:message code="user.title.list"/>
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript">
        function init()
        {
            DWRUtil.useLoadingMessage();
        }
    </script>
</head>

<body onload="init();">

<jsp:include page="menu.jsp"/>

<br/>

<div id="paragraph" >
    <c:out value="${message}"/>
</div>

<div id="mainContent">
    <form action="">
        <display:table name="${users}" export="true" id="row" class="dataTable" pagesize="5" cellspacing="0"
                       decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/user/list.do">

            <display:column property="id" titleKey="user.list.table.id" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="userName" titleKey="user.list.table.username" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="firstName" titleKey="user.list.table.firstname" sortable="true"
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
</body>
</html>
