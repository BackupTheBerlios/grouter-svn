<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        <spring:message code="user.title.search"  />
    </title>

</head>
<body>

<jsp:include page="menunode.jsp"/>

<div id="mainContent">

    <form action="">
        <display:table name="${nodes}" export="true" id="row" class="dataTable" pagesize="5" cellspacing="0"
                       decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/user/list.do">

            <display:column property="id" titleKey="user.list.table.id" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="inBound.uri" titleKey="user.list.table.email" sortable="true"
                            class="orderNumber" headerClass="orderNumber"/>
            <display:column property="outBound.uri" titleKey="user.list.table.companyname" sortable="true"
                            class="orderNumber" headerClass="orderNumber"/>
            <display:column  title="Action" sortable="false" >
                <a href="edit.do?id=${row.id}" >Edit</a> |
                <a href="delete.do?id=${row.id}" >Delete</a>
            </display:column>

        </display:table>
    </form>
</div>
</body>
</html>