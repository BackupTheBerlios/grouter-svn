<!-- This content is loaded with ajax in maincontent tag on listuers.jsp -->
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<form action="">
    <display:table name="${users}" export="true" id="row" class="dataTable" pagesize="5" cellspacing="0"
                   decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/user/list.do">
        <display:column property="id" titleKey="user.list.table.id" sortable="true" class="name"
                        headerClass="name" style="width: 15px;"/>
        <display:column property="auditInfo.createdOn" titleKey="user.list.table.createdOn" sortable="true" class="name"
                        headerClass="name"/>
        <display:column property="auditInfo.modifiedOn" titleKey="user.list.table.modifiedOn" sortable="true"
                        class="name"
                        headerClass="name"/>
        <display:column property="userName" titleKey="user.list.table.username" sortable="true" class="name"
                        headerClass="name"/>
        <display:column property="firstName" titleKey="user.list.table.firstname" sortable="true"
                        class="orderNumber" headerClass="orderNumber" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>
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