<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>
        <spring:message code="message.title.list"  />
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<jsp:include page="menumessage.jsp"/>
<div id="mainContent">
    <form action="">
        <display:table name="${messages}" export="true" id="row" class="dataTable" pagesize="20"
                       cellspacing="0" decorator="org.displaytag.decorator.TotalTableDecorator"  requestURI="/gweb/message/list.do">
            <display:column property="id" title="Id" sortable="true"  class="orderNumber" headerClass="orderNumber"  />
            <display:column property="auditInfo.createdOn" title="Created" sortable="true"  class="orderNumber" headerClass="orderNumber" format="{0,date,long}" />
            <display:column property="content" title="Content" sortable="true"  class="orderNumber" headerClass="orderNumber"/>
            <display:column value="Detayil" title="" sortable="false" href='edit.do' paramId="id" paramProperty="id" />
        </display:table>
    </form>
</div>
</body>
</html>
