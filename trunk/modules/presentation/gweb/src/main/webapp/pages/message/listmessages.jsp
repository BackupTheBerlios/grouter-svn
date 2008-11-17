<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>


<html>
<head>
    <title>
        <spring:message code="message.title.list"/>
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript">
        $(document).ready(function()
        {
            // hides the slickbox as soon as the DOM is ready
            // (a little sooner than page load)
            $('#slickbox').hide();
            $('#searchbox').hide();
            // shows the slickbox on clicking the noted link
            $('a#slick-show').click(function()
            {
                $('#filterbox').show('slow');
                return false;
            });
            // hides the slickbox on clicking the noted link
            $('a#slick-hide').click(function()
            {
                $('#filterbox').hide('fast');
                return false;
            });
            // toggles the slickbox on clicking the noted link
            $('a#slick-toggle').click(function()
            {
                $('#filterbox').toggle(100);
                return false;
            });
            // toggles the slickbox on clicking the noted link
            $('a#search-toggle').click(function()
            {
                $('#searchbox').toggle(100);
                return false;
            });

            $("#searchMessagesForm").ajaxForm({
                type: 'POST',
                target: '#mainContent'
            });

        });
    </script>


</head>
<body>


<div id="menuAction">

    <table border="0" width="100%">
        <tr>
            <td>

                <a href="#" id="slick-toggle">Filter</a>

                <div id="filterbox">
                    <form action="" enctype="multipart/form-data" name="mainForm" method="get">
                        Router:
                        <select id="routerid" name="routerid" onchange="this.form.submit()">
                            <option value="">Select router</option>
                            <c:forEach items="${routers}" var="object">
                                <option
                                        <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if>
                                        value="${object.id}">${object.id}</option>
                            </c:forEach>
                        </select>
                        Node:
                        <select id="nodeid" name="nodeid" onchange="this.form.submit()">
                            <option value="">Select node</option>
                            <c:forEach items="${nodes}" var="object">
                                <option
                                        <c:if test="${selectedNodeId eq object.id}">selected="selected"</c:if>
                                        value="${object.id}">${object.displayName}</option>
                            </c:forEach>
                        </select>
                    </form>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <a href="#" id="search-toggle">Search</a>
                <div id="searchbox">
                   <form:form action="/gweb/message/search.do"
                              id="searchMessagesForm" cssClass="decoratedform" method="POST" name="searchMessagesForm" >

                        <spring:message code="message.search.form.label.id"/>
                        <form:input path="messageId" />
    
                        <spring:message code="message.search.form.label.nodeId"/>
                        <select id="nodeId" >
                            <option value="">Select node</option>
                            <c:forEach items="${nodes}" var="object">
                                <option
                                        <c:if test="${selectedNodeId eq object.id}">selected="selected"</c:if>
                                        value="${object.id}">${object.displayName}</option>
                            </c:forEach>
                        </select>
                        <input type="submit" value="Search" name="search" size="10"/>

                    </form:form>
                </div>
            </td>
        </tr>
    </table>
</div>


<div id="message">
    <c:out value="${message}"/>
</div>


<div id="mainContent">
    <form id="mainForm" action="">
        <jsp:include page="messageTableInclude.jsp"/>
    </form>
</div>
</body>
</html>
