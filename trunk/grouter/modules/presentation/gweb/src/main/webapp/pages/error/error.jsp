<%@ page session="false" %>
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
    An error was caught
</div>

<jsp:include page="menu.jsp"/>


<div id="content">
    ERROR
    <c:out value="${errorKey}"/>
    <%= request.getParameter("errorKey") %>

</div>
</body>
</html>





        