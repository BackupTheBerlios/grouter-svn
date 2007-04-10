<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Nodes :: List
    </title>
    <script type="text/javascript" charset="iso8859-1" src="../../javascripts/engine.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="../../javascripts/util.js"></script>
    <!-- script type="text/javascript" charset="iso8859-1" src="/javascripts/effects.js"></script -->
    <script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/prototype.js"></script>
    <!-- script type="text/javascript" charset="iso8859-1" src="/javascripts/effects.js"></script -->
    <script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/scriptaculous.js"></script>

    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/interface/RouterService.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/interface/NodeCallbackThread.js"></script>

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
            //DWRUtil.setValue("reply", data[0].numberOfMessagesHandled);
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
    </script>
</head>

<body onload="init();">

<div id="menuAction">
    <div id="form">
        <table border="0">
            <tr>

                <td>
                    <form action="" enctype="multipart/form-data" name="mainForm" method="get">
                        List nodes for router:                                                              
                        <select id="routerid" name="routerid" onchange="this.form.submit()">
                            <option value="">--- router ---</option>
                            <c:forEach items="${routers}" var="object">
                                <option <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if> value="${object.id}" >${object.name}</option>
                            </c:forEach>
                        </select>
                    </form>
                    <!-- Update in realtime: -->
                    Register for updates (realtime):<input id="checkboxIsRegisterdForCallbacks" type="checkbox" value="Read" onclick="registerForCallbacks()"/>

                </td>

            </tr>
        </table>
    </div>
</div>
<!-- display:table name="" defaultsort="1" id="element" class="pagedList" -->
<!-- display:column property="id" sortable="true" sortName="id" title="Id"/ -->
<!-- display:column property="content" sortable="true" sortName="content" title="Content"/ -->
<!--/display:table -->

<div id="paragraph">
    Search nodes.

</div>

<div id="content" >
    <form id="mainForm" action="">
        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td></td>
                <td align="right" >Number of nodes :<c:out value="${nodesSize}"/>
                </td>
            </tr>
        </table>
        <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th><a href="?sortBy=id">Id</a></th>
                    <th><a href="?sortBy=firstName">#Messages</a></th>
                    <th><a href="?sortBy=firstName">Name</a></th>
                    <th><a href="?sortBy=firstName">In</a></th>
                    <th><a href="?sortBy=firstName">Out</a></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${nodes}" var="object">
                    <tr  >
                        <td>
                            <c:out value="${object.id}"/>
                        </td>
                        <td id="${object.id}">
                            <c:out value="${object.numberOfMessagesHandled}"/>
                        </td>
                        <td>
                            <c:out value="${object.name}"/>
                        </td>
                        <td>
                            <c:out value="${object.inBound.uri}"/>
                        </td>
                        <td>
                            <c:out value="${object.outBound.uri}"/>
                        </td>
                        <td>
                            <a href='edit.do?id=<c:out value="${object.id}"/>'> Details </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </form>
</div>
</body>
</html>
