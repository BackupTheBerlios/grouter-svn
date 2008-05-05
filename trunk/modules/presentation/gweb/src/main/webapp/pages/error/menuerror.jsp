<%@ page import="java.io.PrintWriter" %>
<div id="menuAction">
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>List</a>
    &nbsp;&nbsp;&nbsp;
    <a href="/gweb/job/lwedit.do" params="lightwindow_width=400,lightwindow_height=300"
       class="lightwindow page-options iconlink"> <img src="/gweb/images/edit_add_24x24.png" alt="New">New</a>
    <br/>
    <span class="warning">
        <c:out value="${object.warning}"/>
    </span>
    <span class="message">
        <c:out value="${object.message}"/>
    </span>
</div>