<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"  %>

<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
    <title>Grouter - <decorator:title/></title>


    <!--script type="text/javascript" src="/javascripts/dojo/dojo.js"></script>
    <script language="JavaScript" type="text/javascript">
        dojo.hostenv.setBaseScriptUri("/javascripts/dojo/");                </script -->
    






    <!-- script type="text/javascript" charset="iso8859-1"
            src="../../javascripts/lightwindow.js"></script>
    <script type="text/javascript" charset="iso8859-1"
            src="/gweb/javascripts/lightwindow.js"></script -->

    <!--link href="../../css/lightwindow.css" type="text/css" rel="stylesheet"/>
    <link href="/gweb/css/lightwindow.css" type="text/css" rel="stylesheet"/ -->

    <link href="../../css/page_main.css" type="text/css" rel="stylesheet"/>
    <link href="../../css/default.css" type="text/css" rel="stylesheet" />

    <link href="../../css/datatable.css" rel="stylesheet" type="text/css" >
    <link href="/gweb/css/datatable.css" rel="stylesheet" type="text/css" >


    <link href="../css/page_main.css" type="text/css" rel="stylesheet"/>
    <link href="../css/decorator_main.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/engine.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/util.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/interface/RouterService.js"></script>





    <link rel="icon" href="http://script.aculo.us/favicon.ico" type="image/x-icon"/>

    <decorator:head/>
</head>

<body  class="tundra" width="800">
<div id="container">
    <div id="logoBar">
        <img id="logo" src="../images/browser.png" width="48" height="48" alt="logo"/>
        <decorator:title/>
        <div id="usersettings">
            <jsp:include page="../pages/user/usersettings.jsp"/>
        </div>
    </div>

    <br/>

    <div id="menu">
        <jsp:include page="../pages/menu/menu.jsp"/>
    </div>

    <div id="body">
        <decorator:body/>
    </div>

    <div id="footer">
        <p class="copyright">&copy; <a href="http://grouter.berlios.de/">Grouter</a></p>

        <div class="links">
            <a href="http://apache.maven.org/" title="Maven">Maven</a> &#183;
        </div>
    </div>

</div>
</body>
</html>