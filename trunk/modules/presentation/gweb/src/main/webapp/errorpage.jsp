<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>


<link href="/gweb/css/page_main.css" type="text/css" rel="stylesheet"/>
<!-- link href="../../css/default.css" type="text/css" rel="stylesheet" / -->

<link href="/gweb/css/datatable.css" rel="stylesheet" type="text/css">


<link href="/gweb/css/lightwindow.css" type="text/css" rel="stylesheet"/>


<!-- JQuery Validation plugin -->
<link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/jquery/jquery.screen.css"/>
<link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/jquery/jquery.tooltip.css"/>
<script src="/gweb/javascripts/jquery/jquery.js" type="text/javascript"></script>
<script src="/gweb/javascripts/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="/gweb/javascripts/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="/gweb/javascripts/jquery/cmxforms.js" type="text/javascript"></script>
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



</head>


<body class="tundra" width="800" onload="init();">

<div id="body">
    <div id="logoBar">
        <img id="logo" src="/gweb/images/browser.png" width="48" height="48" alt="logo"/>
    </div>

    <div id="menu">
        <jsp:include page="/pages/menu/menu.jsp"/>
    </div>


    <div id="paragraph" nowrap>

        <table>
            <tr>
                <td valign="top">Message:</td>
                <td><img src="/gweb/images/icon_error_15x15.gif" alt=""/>

                    <% if (exception != null) exception.getMessage(); %>
                </td>
            </tr>
            <tr>
                <td valign="top" nowrap="true">Exception Class:</td>
                <td><% if (exception != null) exception.getClass(); %>
                </td>
            </tr>
            <tr>
                <td valign="top" nowrap="true">Stacktrace:
                    <a href="#" onclick="Element.hide('stacktrace'); return true;">hide</a>
                    <a href="#" onclick="Element.show('stacktrace'); return false;">show</a>
                </td>
                <td>
                    <div id="stacktrace" id="hide-on-load">
                        <% if (exception != null) exception.printStackTrace(new PrintWriter(out)); %>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
