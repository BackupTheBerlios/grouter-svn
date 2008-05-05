<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <title>
        Routers :: List
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

<jsp:include page="menurouter.jsp"/>

<br/>

<div id="message" style="display:none;">
    <c:out value="${message}"/>
</div>

<div id="content">
    <form action="">
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td></td>
                <td align="right"> Number of router :
                    <c:out value="${usersSize}"/>
                </td>
            </tr>
        </table>

        <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Created</th>
                    <th>Modified</th>
                    <th># nodes</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${routers}" var="object">
                    <tr>
                        <td>
                            <c:out value="${object.id}"/>
                        </td>
                        <td>
                            <c:out value="${object.displayName}"/>
                        </td>
                        <td>

                        </td>
                        <td>

                        </td>
                        <td>

                        </td>
                        <td>
                            <a href='edit.do?id=<c:out value="${object.id}"/>' class="iconlink">
                                <img src="/gweb/images/edit_24x24.png" alt="Delete" width="14"
                                     height="14"> Edit </a> &nbsp;

                            <a href='delete.do?id=<c:out value="${object.id}"/>' class="iconlink">
                                <img src="/gweb/images/remove_24x24.png" alt="Delete" width="14"
                                     height="14">Delete </a>
                            
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
