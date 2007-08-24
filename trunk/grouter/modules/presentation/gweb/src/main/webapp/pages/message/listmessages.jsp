<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->
<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Messages
    </title>
                                     
</head>
<body>

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
                                <option <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if>  value="${object.id}">${object.name}</option>
                            </c:forEach>
                        </select>
                        Node:
                        <select id="nodeid" name="nodeid" onchange="this.form.submit()">
                            <option value="">--- node ---</option>
                            <c:forEach items="${nodes}" var="object">
                                <option  <c:if test="${selectedNodeId eq object.id}">selected="selected"</c:if>
                                    value="${object.id}">${object.name}</option>
                            </c:forEach>
                        </select>

                        <input id="searchText" value="" name="searchText" type="text">

                        <select id="column" name="column">
                            <option value="">--- column ---</option>
                            <option value="ID">Id</option>
                            <option value="CONTENT">Content</option>
                            <option value="SENDER">Sender</option>
                            <option value="RECEIVER">Receiver</option>
                        </select>
                        <input type="submit" value="Search" name="searchCustomer" size="10"/>
                    </form>
                    </td>

                </tr>
            </table>
        <a href="list.do"><img src="/gweb/images/remove_24x24.png" alt="Delete all messages"/></a>

    </div>
</div>
<!-- display:table name="" defaultsort="1" id="element" class="pagedList" -->
<!-- display:column property="id" sortable="true" sortName="id" title="Id"/ -->
<!-- display:column property="content" sortable="true" sortName="content" title="Content"/ -->
<!--/display:table -->

<div id="paragraph" >
    Search messages.
</div>


<div id="content">
    <form id="mainForm" action="">
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td></td>
                <td align="right">Number of messages :
                    <c:out value="${nodesSize}"/>
                </td>
            </tr>
        </table>
        <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">

            <thead>
                <tr>
                    <th><a href="?sortBy=id">Id</a></th>
                    <th><a href="?sortBy=firstName">Created</a></th>
                    <th><a href="?sortBy=firstName">Content</a></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${messages}" var="object">
                    <tr>
                        <td>
                            <c:out value="${object.id}"/>
                        </td>
                        <td>
                            <c:out value="${object.creationTimestamp}"/>
                        </td>
                        <td>
                            <c:out value="${object.content}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </form>


</div>
</body>
</html>
