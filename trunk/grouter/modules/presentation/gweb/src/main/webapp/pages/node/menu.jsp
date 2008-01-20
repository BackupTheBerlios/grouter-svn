<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<div id="menuAction">
    <a href="search.do" class="iconlink"> <img src="/gweb/images/filefind24x24.png" alt="Search">
        <spring:message code="node.menu.action.search"  />
    </a>
    
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
        <spring:message code="node.menu.action.list"/>
    </a>

    <span class="warning">
        <c:out value="${object.warning}"/>
    </span>

    <span class="message">
        <c:out value="${object.message}"/>
    </span>
</div>
