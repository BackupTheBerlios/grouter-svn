<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>



<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <title>
        User :: List
    </title>
    <link href="../../css/page_main.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        function init()
        {
            DWRUtil.useLoadingMessage();
        }
    </script>
</head>


<body onload="init();">


<form action="">
    <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <th></th>
            <th align="right"> Number of users :
                <c:out value="${usersSize}"/>
            </th>
        </tr>
    </table>
    <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">

        <thead>
            <tr>
                <th>Id</th>
                <th>Username</th>
                <th>First</th>
                <th>Last</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Company</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="object">
                <tr>
                    <td>
                        <c:out value="${object.id}"/>
                    </td>
                    <td>
                        <c:out value="${object.userName}"/>
                    </td>
                    <td>
                        <c:out value="${object.firstName}"/>
                    </td>
                    <td>
                        <c:out value="${object.lastName}"/>
                    </td>
                    <td>
                        <c:out value="${object.address.phone}"/>
                    </td>
                    <td>
                        <c:out value="${object.address.email}"/>
                    </td>
                    <td>
                        <c:out value="${object.address.companyname}"/>
                    </td>
                    <td>
                        <a href='edit.do?id=<c:out value="${object.id}"/>'> Edit </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>


</form>
</body>
</html>
