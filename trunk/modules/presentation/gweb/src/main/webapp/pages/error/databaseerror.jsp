<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- %@ taglib uri="http://displaytag.sf.net" prefix="display" % -->



<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>

<html>
<head>
    <title>
        Errors 
    </title>
    <link href='<c:out value='${requestScope.CONTEXT_PATH}'/>/css/main.css' rel="stylesheet"/>
</head>
<body>


<div id="paragraph" nowrap>
    AAAAAAAA
    An unexpected error was caught by the system.
</div>

<jsp:include page="menuerror.jsp"/>


<div id="content">
    ERROR
    <c:out value="${errorKey}"/>
    <%= request.getParameter("errorKey") %>

</div>


 <div id="paragraph" nowrap>

        <table>
            <tr>
                <td valign="top"><img src="/gweb/images/icon_error_15x15.gif" alt=""/> Caught exception from databse operation:</td>
                <td> <%= exception.getMessage() %>
                </td>
            </tr>
            <tr>
                <td valign="top" nowrap="true">Exception Class:</td>
                <td><%= exception.getClass() %>
                </td>
            </tr>
            <tr>
                <td valign="top" nowrap="true">Stacktrace:
                    <a href="#" onclick="Element.hide('stacktrace'); return true;">hide</a>
                    <a href="#" onclick="Element.show('stacktrace'); return false;">show</a>
                </td>
                <td>
                    <div id="stacktrace" id="hide-on-load">
                        <% if (exception != null) exception.printStackTrace(new PrintWriter(out)); %>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>





        