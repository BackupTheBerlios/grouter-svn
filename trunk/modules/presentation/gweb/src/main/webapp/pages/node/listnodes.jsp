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
<jsp:include page="../node/menunode.jsp"/>
<div id="mainContent">
    <form id="mainForm" action="">
        <table class="pagedList" border="0" width="100%" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th><a href="?sortBy=id">Id</a></th>
                    <th><a href="?sortBy=id">Status</a></th>
                    <th><a href="?sortBy=firstName">Name</a></th>
                    <th><a href="?sortBy=firstName">#Messages</a></th>
                    <th><a href="?sortBy=firstName">In</a></th>
                    <th><a href="?sortBy=firstName">Out</a></th>
                    <th>Messages</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${nodes}" var="object">
                    <tr>
                        <td>
                                                    <c:out value="${object.id}"/>
                                                </td>

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
                            <a href="/gweb/message/list.do?nodeid=${object.id}"> <img src="/gweb/images/gtk-goto-first-rtl.png" alt=""/> </a>
                        </td>

                        <td>

                            <a href='edit.do?id=<c:out value="${object.id}"/>'> Edit </a>
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
