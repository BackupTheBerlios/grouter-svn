<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>

    <title>
        <spring:message code="node.title.list"/>
    </title>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>
    <!-- script type="text/javascript" charset="iso8859-1" src="../../javascripts/engine.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="../../javascripts/util.js"></script>
    <script type="text/javascript" charset="iso8859-1"
            src="/gweb/javascripts/prototype.js"></script>
    <script type="text/javascript" charset="iso8859-1"
            src="/gweb/javascripts/scriptaculous.js"></script>

    <script type="text/javascript" charset="iso8859-1"
            src="/gweb/dwr/interface/RouterService.js"></script>
    <script type="text/javascript" charset="iso8859-1"
            src="/gweb/dwr/interface/NodeCallbackThread.js"></script>

    <script type="text/javascript">
        function handleGetData(str)
        {
            DWRUtil.setValue("id_reply", data);
        }                  

        function handleGetData2()
        {
            routerService.findAllNodes(callbackLoadinfo);
        }

        function callbackLoadinfo(data)
        {
            for (i = 0; i < data.length; i++)
            {
                DWRUtil.setValue(data[i].id, data[i].numberOfMessagesHandled + 2);
            }
        }

        function registerForCallbacks()
        {
            var doRegister = DWRUtil.getValue("checkboxIsRegisterdForCallbacks");
            if (doRegister == true)
            {
                dwr.engine.setActiveReverseAjax(true);
                var selectedRouterId = DWRUtil.getValue("routerid");
                NodeCallbackThread.register(selectedRouterId);
            }
            else
            {
                dwr.engine.setActiveReverseAjax(false);
                NodeCallbackThread.deRegister();
            }
        }

        function init()
        {
            DWRUtil.useLoadingMessage();
        }

        callOnLoad(init);
    </script -->

    <script type="text/javascript">
        $(document).ready(function()
        {
            // hides the slickbox as soon as the DOM is ready
            // (a little sooner than page load)

            $('#searchbox').hide();

            // toggles the slickbox on clicking the noted link
            $('a#search-toggle').click(function()
            {
                $('#searchbox').toggle(100);
                return false;
            });

            // Perform ajax post
            $("#searchNodesForm").ajaxForm({
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
                <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
                    <spring:message code="node.menu.action.list"/>

                </a>

                <a href="edit.do" params="lightwindow_width=400,lightwindow_height=400"
                   class="lightwindow page-options iconlink"> <img src="/gweb/images/edit_add_24x24.png" alt="New">
                </a>
            </td>
            <td>
                <div id="form" align="right">
                    <form action="/gweb/node/search.do" enctype="multipart/form-data" name="searchNodesForm"
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

                <a href="#" id="search-toggle">Search</a>

                <div id="searchbox">
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
                </div>
            </td>
        </tr>
    </table>
</div>


<div id="mainContent">
    <form id="mainForm" action="">
        <jsp:include page="nodeTableInclude.jsp"/>
    </form>
</div>
</body>
</html>
