<div id="menuAction">
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
        <spring:message code="job.menu.action.list"/>
    </a>
    <a href="edit.do" params="lightwindow_width=400,lightwindow_height=400"
       class="lightwindow page-options iconlink"> <img src="/gweb/images/edit_add_24x24.png" alt="New">
        <spring:message code="job.menu.action.new"/>
    </a>

    <div id="form" align="right">
        <form action="/gweb/job/search.do" enctype="multipart/form-data" name="searchUsersForm"
              method="get">
            <span id="searchHelp" title="Google like queries -
            To search for test, tests or tester, you can use the search: <br/>
            test*
            ">?</span>
            <input id="searchText" value="" name="searchText" type="text">
            <input type="submit" value="Search" name="search" size="10"/>
        </form>
    </div>
</div>