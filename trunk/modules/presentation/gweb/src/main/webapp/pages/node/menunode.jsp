<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<div id="menuAction">
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
        <spring:message code="node.menu.action.list"/>
    </a>

    <div id="form" align="right">
        <form action="/gweb/node/search.do" enctype="multipart/form-data" name="searchUsersForm"
              method="get">
                        <span id="searchHelp" title="Google like queries -
            To search for test, tests or tester, you can use the search: <br/>
            test*
            <br/>
            To search for 'text' or 'test' you can use the search: <br/>
            te?t   <br/>
            More info : http://lucene.apache.org/
            ">?</span>
            <input id="searchText" value="" name="searchText" type="text">
            <input type="submit" value="Search" name="search" size="10"/>
        </form>
    </div>
</div>
