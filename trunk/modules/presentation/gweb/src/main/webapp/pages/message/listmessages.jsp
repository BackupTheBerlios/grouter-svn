<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>


<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->
<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        <spring:message code="message.title.list"  />
    </title>
                                     
</head>
<body>


<jsp:include page="menumessage.jsp"/>



<div id="menuAction" >
    <div id="form">
            <table border="0">
                <tr>
                    <td>
                        <form action="" enctype="multipart/form-data" name="mainForm" method="get">
                        Grouter:
                        <select id="routerid" name="routerid" onchange="this.form.submit()">
                            <option value="">--- router ---</option>
                            <c:forEach items="${routers}" var="object">
                                <option <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if>  value="${object.id}">${object.id}</option>
                            </c:forEach>
                        </select>
                        Node:
                        <select id="nodeid" name="nodeid" onchange="this.form.submit()">
                            <option value="">--- node ---</option>
                            <c:forEach items="${nodes}" var="object">
                                <option  <c:if test="${selectedNodeId eq object.id}">selected="selected"</c:if>
                                    value="${object.id}">${object.displayName}</option>
                            </c:forEach>
                        </select>

                        <!-- input id="searchText" value="" name="searchText" type="text">

                        <select id="column" name="column">
                            <option value="">--- column ---</option>
                            <option value="ID">Id</option>
                            <option value="CONTENT">Content</option>
                            <option value="SENDER">Sender</option>
                            <option value="RECEIVER">Receiver</option>
                        </select>
                        <input type="submit" value="Search" name="searchCustomer" size="10"/ -->
                    </form>
                    </td>

                </tr>
            </table>

    </div>
</div>



<div id="mainContent">



        <display:table name="${messages}" export="true" id="row" class="dataTable" pagesize="3"
                       cellspacing="0" decorator="org.displaytag.decorator.TotalTableDecorator"  requestURI="/gweb/message/list.do">

            <display:column property="counter" title="NAME" sortable="true"  class="name" headerClass="name"/>
            <display:column property="creationTimestamp" title="CREATED" sortable="true"  class="orderNumber" headerClass="orderNumber" format="{0,date,short}" />
            <display:column property="content" title="CONTENT" sortable="true"  class="orderNumber" headerClass="orderNumber"/>

            <display:column value="Detail" title="" sortable="false" href='edit.do' paramId="id" paramProperty="id" />
        </display:table>




</div>
</body>
</html>
