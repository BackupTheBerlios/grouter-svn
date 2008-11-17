<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<display:table name="${nodes}" export="true" id="row" class="dataTable" pagesize="5" cellspacing="0"
               decorator="org.displaytag.decorator.TotalTableDecorator" requestURI="/gweb/node/list.do">
    <display:column titleKey="node.list.table.messages" sortable="false"
                    class="orderNumber" headerClass="orderNumber" style="width:10px;">
        <a href="/gweb/node/list.do?nodeid=${row.id}"> <img src="/gweb/images/gtk-goto-first-rtl.png" alt=""/>
        </a>
    </display:column>
    <display:column property="id" titleKey="node.list.table.id" sortable="true" class="name"
                    headerClass="name" style="width: 15px;"/>
    <display:column property="displayName" titleKey="node.list.table.displayName" sortable="true" class="name"
                    headerClass="name"/>
    <display:column property="displayName" titleKey="node.list.table.displayName" sortable="true" class="name"
                    headerClass="name">
        <c:if test="${object.nodeStatus.id eq 1}"><img
                src="/gweb/images/icon_notstarted_15x15.gif"/></c:if>
        <c:if test="${row.nodeStatus.id eq 2}"><img
                src="/gweb/images/icon_running_15x15.png"/></c:if>
        <c:if test="${row.nodeStatus.id eq 3}"><img
                src="/gweb/images/icon_success_15x15.gif"/></c:if>
        <c:if test="${row.nodeStatus.id eq 4}"><img
                src="/gweb/images/icon_stopped_15x15.gif"/></c:if>
        <c:if test="${row.nodeStatus.id eq 5}"><img
                src="/gweb/images/icon_error_15x15.gif"/></c:if>
    </display:column>
    <display:column property="numberOfMessagesHandled" titleKey="node.list.table.numberOfMessagesHandled"
                    sortable="true"
                    class="orderNumber" headerClass="orderNumber"/>
    <display:column property="inBound.uri" titleKey="node.list.table.inbound.uri" sortable="true"
                    class="orderNumber" headerClass="orderNumber"/>
    <display:column property="outBound.uri" titleKey="node.list.table.outbound.uri" sortable="true"
                    class="orderNumber" headerClass="orderNumber"/>

</display:table>