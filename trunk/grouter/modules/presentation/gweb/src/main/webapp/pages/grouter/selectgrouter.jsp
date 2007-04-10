<%@ page session="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->


<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Messages
    </title>
    <link href='<c:out value='${requestScope.CONTEXT_PATH}'/>/css/main.css' rel="stylesheet"/>
</head>
<body>

<div id="paragraph" nowrap>
    Test
</div>

<div id="menuAction" nowrap>


    <div id="form">

    <form  action="" enctype="multipart/form-data" name="mainForm" method="post">
           <table border="0">

            <tr>
                <td>
                    <input id="searchText" value="" name="searchText" type="text"  >
                    <select id="column" name="column">
                        <option value="">--- kolumn ---</option>
                        <option value="CONTENT">Content</option>
                        <option value="SENDER">Sender</option>
                        <option value="RECEIVER">Receiver</option>
                    </select>
                    <input type="submit" value="Sök" name="searchCustomer" size="10" />

            </tr>
               </table>

    </form>

    <form action="">
        <select name="nodeSelect" onchange="this.form.submit()">
            <c:forEach items="${nodes}" var="object">
                <option value="${object.name}" >${object.name}</option>
            </c:forEach>
        </select>
    </form>

        </div>
</div>
<!-- display:table name="" defaultsort="1" id="element" class="pagedList" -->
    <!-- display:column property="id" sortable="true" sortName="id" title="Id"/ -->
    <!-- display:column property="content" sortable="true" sortName="content" title="Content"/ -->
<!--/display:table -->


<form id="mainForm" action="">
    <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">

        <thead>
            <tr>
                <th></th>
                <th>Number of message : <c:out value="${messagesSize}"/></th>
            </tr>

            <tr>
                <th><a href="?sortBy=id">Id</a></th>
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
                    <c:out value="${object.content}"/>
                </td>
            </tr>
            </c:forEach>
    </table>

</form>
</body>
</html>





        