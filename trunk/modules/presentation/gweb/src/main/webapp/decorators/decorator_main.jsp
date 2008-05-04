<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"  %>


<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% request.setAttribute("CONTEXT_PATH", request.getContextPath()); %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
    <title>Grouter - <decorator:title/></title>
    <link href="/gweb/css/page_main.css" type="text/css" rel="stylesheet" />
    <link href="/gweb/css/datatable.css" rel="stylesheet" type="text/css" >
    <link href="/gweb/css/lightwindow.css" type="text/css" rel="stylesheet"/>

    <!-- script src="/gweb/javascripts/jquery/jquery.tooltip.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.bigrame.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.dimensions.js" type="text/javascript"></script -->

    <script type="text/javascript" charset="iso8859-1" src="../javascripts/common.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="../../javascripts/engine.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="../../javascripts/util.js"></script>
    <!-- script type="text/javascript" charset="iso8859-1" src="/javascripts/effects.js"></script -->
    <script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/common.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/prototype.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/effects.js"></script>
    <!-- script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/scriptaculous.js"></script -->
    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/engine.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/util.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="/gweb/dwr/interface/RouterService.js"></script>
    <!-- script type="text/javascript" charset="iso8859-1" src="../../javascripts/lightwindow-2.0.js"></script -->
    <script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/lightwindow-2.0.js"></script>
    <!-- script type="text/javascript" charset="iso8859-1" src="../../javascripts/accordion-2.0.js"></script -->
    <!-- script type="text/javascript" charset="iso8859-1" src="/gweb/javascripts/accordion-2.0.js"></script -->
    <link rel="icon" href="/gweb/images/favicon.ico" type="image/x-icon"/>




     <link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/page_main.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/jquery/jquery.screen.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/jquery/jquery.tooltip.css"/>
    <script src="/gweb/javascripts/jquery/jquery.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.validate.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.validate.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/cmxforms.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.tooltip.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.bigrame.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.dimensions.js" type="text/javascript"></script>


    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#loginForm").validate();
        });


        $(function()
        {
            $('#menu1').tooltip({
                track: true,
                delay: 0,
                showURL: false,
                showBody: " - ",
                extraClass: "menuToolTipSmal",
                fixPNG: true,
                opacity: 0.95,
                left: 0
            });
            $('#menu2').tooltip({
                track: true,
                delay: 0,
                showURL: false,
                showBody: " - ",
                extraClass: "menuToolTipSmal",
                fixPNG: true,
                opacity: 0.95,
                left: -0
            });
            $('#menu3').tooltip({
                track: true,
                delay: 0,
                showURL: true,
                showBody: " - ",
                extraClass: "menuToolTipSmal",
                fixPNG: true,
                opacity: 0.95,
                left: 0
            });
            $('#menu4').tooltip({
                track: true,
                delay: 0,
                showURL: false,
                showBody: " - ",
                extraClass: "menuToolTipSmal",
                fixPNG: true,
                opacity: 0.95,
                left: 0
            });

            $('#menu5').tooltip({
                track: true,
                delay: 0,
                showURL: false,
                showBody: " - ",
                extraClass: "menuToolTipSmal",
                fixPNG: true,
                opacity: 0.95,
                left: 0
            });

            $('#searchHelp').tooltip({
                track: true,
                delay: 0,
                showURL: true,
                showBody: " - ",
                extraClass: "menuToolTip",
                fixPNG: true,
                opacity: 0.95,
                left: 0
            });
        });
    </script>


    <!-- JQuery Validation plugin -->
    <!-- link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/jquery/jquery.screen.css"/>
    <script src="/gweb/javascripts/jquery/jquery.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/jquery.validate.js" type="text/javascript"></script>
    <script src="/gweb/javascripts/jquery/cmxforms.js" type="text/javascript"></script -->



    <decorator:head/>
</head>

<body  class="tundra" width="800">
<div id="container">
    <div id="logoBar">
        <img id="logo" src="../images/browser.png" width="48" height="48" alt="logo"/>
        <decorator:title/>
        <jsp:include page="../pages/user/usersettings.jsp"/>
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