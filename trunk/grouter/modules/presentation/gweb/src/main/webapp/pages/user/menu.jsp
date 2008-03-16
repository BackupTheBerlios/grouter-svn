<div id="menuAction">
    
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

    <div id="form">
        <table border="0">
            <tr>
                <td>
                    <form action="/gweb/user/search.do" enctype="multipart/form-data" name="mainForm" method="get">
                        Search:
                        <input id="searchText" value="" name="searchText" type="text">
                        <input type="submit" value="Search" name="search" size="10"/>
                    </form>
                </td>

            </tr>
        </table>

</div>

</div>