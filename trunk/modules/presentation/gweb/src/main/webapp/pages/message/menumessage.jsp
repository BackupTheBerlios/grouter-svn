<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="menuAction">

    <table border="0" width="100%">
        <tr>
            <td>

                <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
                    <spring:message code="message.menu.action.list"/>
                </a>
            </td>
            <td>


                <div id="form" align="right">
                    <form action="/gweb/message/search.do" enctype="multipart/form-data" name="searchUsersForm"
                          method="get">
            <span id="searchHelp" title="Google like queries -
            To search for test, tests or tester, you can use the search: <br/>
            test*
            <br/>
            To search for 'text' or 'test' you can use the search: <br/>
            te?t   <br/>
            More info : http://lucene.apache.org/">?</span>
                        <input id="searchText" value="" name="searchText" type="text">
                        <input type="submit" value="Search" name="search" size="10"/>
                    </form>
                </div>
            </td>
        </tr>
        <tr>
            <td>
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
            </td>
        </tr>
    </table>

</div>
