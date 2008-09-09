<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>


<html>
<head>
    <title>
        <spring:message code="router.title.list"/>
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<jsp:include page="menurouter.jsp"/>
<div id="mainContent">
    <form action="">
        <display:table name="${routers}" export="true" id="row" class="dataTable" pagesize="5" cellspacing="0"
                       decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/router/list.do">

            <display:column property="id" titleKey="router.list.table.id" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="displayName" titleKey="router.list.table.displayName" sortable="true" class="name"
                            headerClass="name"/>
            <display:column property="description" titleKey="router.list.table.description" sortable="true"
                            class="orderNumber" headerClass="orderNumber" />
            <display:column property="startedOn" titleKey="router.list.table.startedOn" sortable="true"
                            class="orderNumber" headerClass="orderNumber" format="{0,date,short}"/>
            <display:column  titleKey="router.list.table.nodes" sortable="false"
                            class="orderNumber" headerClass="orderNumber">
                <a href="/gweb/node/list.do?routerid=${row.id}"> <img src="/gweb/images/gtk-goto-first-rtl.png" alt=""/> </a>
            </display:column>                                              
        </display:table>
    </form>

</div>
</body>
</html>
