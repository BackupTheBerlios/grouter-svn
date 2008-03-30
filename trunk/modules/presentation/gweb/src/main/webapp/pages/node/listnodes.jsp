<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Nodes :: List
    </title>
    <script type="text/javascript" charset="iso8859-1" src="../../javascripts/engine.js"></script>
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




<jsp:include page="../node/menu.jsp"/>



<br>

<div id="menuAction">
    <table border="0">
        <tr>
            <td>
                <form id="menuform" action="" enctype="multipart/form-data" name="mainForm"
                      method="get">
                    List nodes for router:
                    <select id="routerid" name="routerid" onchange="this.form.submit()">
                        <option value="">--- router ---</option>
                        <c:forEach items="${routers}" var="object">
                            <option
                                    <c:if test="${selectedRouterId eq object.id}">selected="selected"</c:if>
                                    value="${object.id}">${object.id}</option>
                        </c:forEach>
                    </select>
                    <!-- Update in realtime: -->
                    Register for updates (realtime):<input id="checkboxIsRegisterdForCallbacks"
                                                           type="checkbox" value="Read"
                                                           onclick="registerForCallbacks()"/>

                </form>
            </td>
        </tr>
    </table>
</div>

<br>

<div id="content">
    <form id="mainForm" action="">


        <table border="0" width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td></td>
                <td align="right">Number of nodes :<c:out value="${nodesSize}"/>
                </td>
            </tr>
        </table>
        <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th><a href="?sortBy=id">Status</a></th>
                    <th><a href="?sortBy=firstName">Name</a></th>
                    <th><a href="?sortBy=firstName">#Messages</a></th>
                    <th><a href="?sortBy=firstName">In</a></th>
                    <th><a href="?sortBy=firstName">Out</a></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${nodes}" var="object">
                    <tr>
                        <td width="5">
                            <c:if test="${object.nodeStatus.id eq 1}"><img
                                    src="/gweb/images/icon_notstarted_15x15.gif"/></c:if>
                            <c:if test="${object.nodeStatus.id eq 2}"><img
                                    src="/gweb/images/icon_running_15x15.png"/></c:if>
                            <c:if test="${object.nodeStatus.id eq 3}"><img
                                    src="/gweb/images/icon_success_15x15.gif"/></c:if>
                            <c:if test="${object.nodeStatus.id eq 4}"><img
                                    src="/gweb/images/icon_stopped_15x15.gif"/></c:if>
                            <c:if test="${object.nodeStatus.id eq 5}"><img
                                    src="/gweb/images/icon_error_15x15.gif"/></c:if>

                        </td>

                        <td>
                            <c:out value="${object.displayName}"/>
                        </td>
                        <td id="${object.id}">
                            <c:out value="${object.numberOfMessagesHandled}"/>
                        </td>
                        <td>
                            <c:out value="${object.inBound.uri}"/>
                        </td>
                        <td>
                            <c:out value="${object.outBound.uri}"/>
                        </td>
                        <td>
                            <a href='edit.do?id=<c:out value="${object.id}"/>'> Details </a>
                            <a href='send.do?id=<c:out value="${object.id}"/>&operation=stop'> Stop </a>
                            <a href='send.do?id=<c:out value="${object.id}"/>&operation=start'> Start </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </form>
</div>
</body>
</html>
