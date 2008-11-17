<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <title>
        <spring:message code="user.title.list"/>
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>


    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#searchUsersForm").ajaxForm({
                type: 'POST',
                target: '#mainContent'
            });
        });
    </script>
</head>
<body>


<div id="menuAction">
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
        <spring:message code="user.menu.action.list"/>
    </a>
    <a href="edit.do" params="lightwindow_width=400,lightwindow_height=400"
       class="lightwindow page-options iconlink"> <img src="/gweb/images/edit_add_24x24.png" alt="New">
        <spring:message code="user.menu.action.new"/>
    </a>

    <div id="form" align="right">
        <form id="searchUsersForm" action="/gweb/user/search.do" enctype="multipart/form-data" name="searchUsersForm"
              method="POST">
            <span id="searchHelp" title="Google like queries -
            To search for test, tests or tester, you can use the search: <br/>
            test*">?</span>
            <input id="searchText" value="" name="searchText" type="text">
            <input type="submit" value="Search" name="search" size="10"/>
        </form>
    </div>
    <c:out value="${message}"/>
</div>


<div id="message">
    <c:out value="${message}"/>
</div>


<div id="mainContent">
    <form action="">
        <jsp:include page="userTableInclude.jsp"/>
    </form>
</div>

</body>
</html>
