<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <title>
        <spring:message code="user.title.list"  />
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

<jsp:include page="menu.jsp"/>

<br/>

<div id="message" style="display:none;">
    <c:out value="${message}"/>
</div>

<div id="content">
    <form action="">
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td></td>
                <td align="right"> <spring:message code="user.content.number.of.users" />
                    <c:out value="${usersSize}"/>
                </td>
            </tr>
        </table>
        <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th><spring:message code="user.list.table.id" /></th>
                    <th><spring:message code="user.list.table.username" /></th>
                    <th><spring:message code="user.list.table.firstname" /></th>
                    <th><spring:message code="user.list.table.lastname" /></th>
                    <th><spring:message code="user.list.table.phone" /></th>
                    <th><spring:message code="user.list.table.email" /></th>
                    <th><spring:message code="user.list.table.companyname" /></th>
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
                            <a href='edit.do?id=<c:out value="${object.id}"/>' class="iconlink">
                                <img src="/gweb/images/edit_24x24.png" alt="edit" width="14"
                                     height="14"> <spring:message code="user.content.edit" /> </a> &nbsp;

                            <a href='delete.do?id=<c:out value="${object.id}"/>' class="iconlink">
                                <img src="/gweb/images/remove_24x24.png" alt="Delete" width="14"
                                     height="14"><spring:message code="user.content.delete" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</div>
</body>
</html>
