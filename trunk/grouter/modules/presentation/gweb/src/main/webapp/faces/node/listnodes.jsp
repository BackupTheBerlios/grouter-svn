<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Nodes      
    </title>


    <script type="text/javascript">
        function handleGetData(str)
        {
            alert(str);
        }
    </script>


</head>
<body>

<div id="menuAction">
    <div id="form">
        <table border="0">
            <tr>
                <td>
                    <form action="" enctype="multipart/form-data" name="mainForm" method="get">
                        Grouter:
                        <select id="routerid" name="routerid" onchange="this.form.submit()">
                            <option value="">--- router ---</option>
                            <c:forEach items="${routers}" var="object">
                                <option value="${object.id}">${object.name}</option>
                            </c:forEach>
                        </select>
                    </form>
                     <input type="checkbox" id="bigselect" onclick="handleGetData();"/>

                </td>
                                                           
            </tr>
        </table>
    </div>
</div>
<!-- display:table name="" defaultsort="1" id="element" class="pagedList" -->
<!-- display:column property="id" sortable="true" sortName="id" title="Id"/ -->
<!-- display:column property="content" sortable="true" sortName="content" title="Content"/ -->
<!--/display:table -->

<div id="paragraph">
    Search nodes.
</div>


<form id="mainForm" action="">
    <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <th></th>
            <th align="right">Number of nodes :
                <c:out value="${nodesSize}"/>
            </th>
        </tr>
    </table>
    <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">
        <thead>
            <tr>
                <th><a href="?sortBy=id">Id</a></th>
                <th><a href="?sortBy=firstName">Name</a></th>
                <th><a href="?sortBy=firstName">In</a></th>
                <th><a href="?sortBy=firstName">Out</a></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${nodes}" var="object">
                <tr>
                    <td>
                        <c:out value="${object.id}"/>
                    </td>
                    <td>
                        <c:out value="${object.name}"/>
                    </td>
                    <td>
                        <c:out value="${object.inBound.uri}"/>
                    </td>
                    <td>
                        <c:out value="${object.outBound.uri}"/>
                    </td>
                    <td>
                       <a href='editnode.do?<c:out value="${object.id}"/>'> Edit </a> 
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</form>
</body>
</html>
