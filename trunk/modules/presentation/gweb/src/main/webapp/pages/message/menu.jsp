<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>

<div id="menuAction">
    <a href="search.do" class="iconlink"> <img src="/gweb/images/filefind24x24.png" alt="Search">
        <spring:message code="message.menu.action.search"  />
    </a>
    
    <a href="list.do" class="iconlink"><img src="/gweb/images/view_detailed_24x24.png" alt="List"/>
        <spring:message code="message.menu.action.list"/>
    </a>

    


    <div id="form" align="right">
        <form action="/gweb/message/search.do" enctype="multipart/form-data" name="searchUsersForm"
              method="get">
            <input id="searchText" value="" name="searchText" type="text">
            <input type="submit" value="Search" name="search" size="10"/>
        </form>
    </div>


</div>
