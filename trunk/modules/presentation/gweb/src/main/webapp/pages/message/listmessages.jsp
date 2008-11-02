<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                            <option value="">--- router ---</option>
                            <c:forEach items="${routers}" var="object">
                                <option
                                        <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if>
                                        value="${object.id}">${object.id}</option>
                            </c:forEach>
                        </select>
                        Node:
                        <select id="nodeid" name="nodeid" onchange="this.form.submit()">
                            <option value="">--- node ---</option>
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
                    <form action="" enctype="multipart/form-data" name="mainForm" method="get">
                        Id:<input id="id" value=""/>
                        From date:<input id="fromdate" value=""/>
                        To date: <input id="todate" value=""/>
                    </form>
                </div>
            </td>
        </tr>
    </table>
</div>


<div id="mainContent">
    <form action="">
        <display:table name="${messages}" export="true" id="row" class="dataTable" pagesize="20"
                       cellspacing="0" decorator="org.displaytag.decorator.TotalTableDecorator"
                       requestURI="/gweb/message/list.do">
            <display:column property="id" title="Id" sortable="true" class="orderNumber" headerClass="orderNumber"
                            style="width: 15px;"/>
            <display:column property="auditInfo.createdOn" title="Created" sortable="true" class="orderNumber"
                            headerClass="orderNumber" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>
            <display:column property="content" title="Content" sortable="true" class="orderNumber"
                            headerClass="orderNumber"/>
            <display:column value="Detayil" title="" sortable="false" href='edit.do' paramId="id" paramProperty="id"/>
        </display:table>
    </form>
</div>
</body>
</html>
