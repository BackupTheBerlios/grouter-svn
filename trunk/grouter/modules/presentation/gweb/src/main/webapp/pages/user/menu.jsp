<div id="menuAction">
    <a href="search.do" class="iconlink"><img src="/gweb/images/filefind24x24.png" alt="Search"/>
        <spring:message code="user.menu.action.search"/>
    </a>
    
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
        <spring:message code="user.menu.action.list"/>
    </a>

    <a href="lwedit.do" params="lightwindow_width=400,lightwindow_height=400"
       class="lightwindow page-options iconlink"> <img src="/gweb/images/edit_add_24x24.png" alt="New">
        <spring:message code="user.menu.action.new"/>
    </a>

    <br/>

    <span class="warning">
        <c:out value="${object.warning}"/>
    </span>

    <span class="message">
        <c:out value="${object.message}"/>
    </span>
</div>