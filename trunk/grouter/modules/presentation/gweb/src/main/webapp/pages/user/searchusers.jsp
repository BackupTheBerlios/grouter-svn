<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        <spring:message code="user.title.search"  />
    </title>

</head>
<body>


<jsp:include page="../user/menu.jsp"/>



<div id="menuAction">
    <div id="form">
        <table border="0">
            <tr>
                <td>
                    <form action="" enctype="multipart/form-data" name="mainForm" method="get">
                        Search:
                        <input id="searchText" value="" name="searchText" type="text">
                        <input type="submit" value="Search" name="search" size="10"/>
                    </form>
                </td>

            </tr>                                                                                         
        </table>
    </div>
</div>


<div id="content">
    <display:table name="${users}" export="true" id="row" class="dataTable" pagesize="10"
                   cellspacing="0" decorator="org.displaytag.decorator.TotalTableDecorator"
                   requestURI="/gweb/user/search.do">
        <display:column property="id" title="ID" sortable="true" class="name" headerClass="name"/>
        <display:column property="userName" title="USER" sortable="true" class="name" headerClass="name"/>
        <display:column property="firstName" title="NAME" sortable="true" class="orderNumber"
                        headerClass="orderNumber" format="{0,date,short}"/>
        <display:column property="lastName" title="LASTNAME" sortable="true" class="orderNumber" headerClass="orderNumber"/>

        <display:column value="Detail" title="" sortable="false" href='edit.do' paramId="id" paramProperty="id"/>
    </display:table>

</div>
</body>
</html>
