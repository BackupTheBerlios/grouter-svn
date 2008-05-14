<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="menuAction">
    <table border="0" width="100%">
        <tr>
            <td>
                <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
                    <spring:message code="node.menu.action.list"/>

                </a>

                <a href="edit.do" params="lightwindow_width=400,lightwindow_height=400"
                   class="lightwindow page-options iconlink"> <img src="/gweb/images/edit_add_24x24.png" alt="New">
                </a>
            </td>
            <td>
                <div id="form" align="right">
                    <form action="/gweb/node/search.do" enctype="multipart/form-data" name="searchUsersForm"
                          method="get">
                                    <span id="searchHelp" title="Google like queries -
                        To search for test, tests or tester, you can use the search: <br/>
                        test*
                        <br/>
                        To search for 'text' or 'test' you can use the search: <br/>
                        te?t   <br/>
                        More info : http://lucene.apache.org/
                        ">?</span>
                        <input id="searchText" value="" name="searchText" type="text">
                        <input type="submit" value="Search" name="search" size="10"/>
                    </form>
                </div>

            </td>
        </tr>
        <tr>
            <td>
                <form id="menuform" action="" enctype="multipart/form-data" name="mainForm"
                      method="get">
                    Selected router:
                    <select id="routerid" name="routerid" onchange="this.form.submit()">
                        <option value="">--- router ---</option>
                        <c:forEach items="${routers}" var="object">
                            <option
                                    <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if>
                                    value="${object.id}">${object.id}</option>
                        </c:forEach>
                    </select>
                    <!-- Update in realtime: -->
                    Updates (realtime):<input id="checkboxIsRegisterdForCallbacks"
                                              type="checkbox" value="Read"
                                              onclick="registerForCallbacks()"/>
                </form>
            </td>
        </tr>
    </table>


</div>
