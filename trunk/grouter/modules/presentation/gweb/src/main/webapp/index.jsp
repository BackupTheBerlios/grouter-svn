<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"  uri="http://www.springframework.org/tags"%>



<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>

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



       <script type="text/javascript">
           $(function()
           {
               $('#pretty').tooltip({
                   track: true,
                   delay: 0,
                   showURL: false,
                   showBody: " - ",
                   extraClass: "pretty",
                   fixPNG: true,
                   opacity: 0.95,
                   left: 0
               });
               $('#pretty2').tooltip({
                   track: true,
                   delay: 0,
                   showURL: false,
                   showBody: " - ",
                   extraClass: "pretty",
                   fixPNG: true,
                   opacity: 0.95,
                   left: -0
               });
               $('#pretty3').tooltip({
                   track: true,
                   delay: 0,
                   showURL: true,
                   showBody: " - ",
                   extraClass: "pretty",
                   fixPNG: true,
                   opacity: 0.95,
                   left: 0
               });
               $('#pretty4').tooltip({
                   track: true,
                   delay: 0,
                   showURL: false,
                   showBody: " - ",
                   extraClass: "pretty",
                   fixPNG: true,
                   opacity: 0.95,
                   left: 0
               });

               $('#pretty5').tooltip({
                   track: true,
                   delay: 0,
                   showURL: false,
                   showBody: " - ",
                   extraClass: "pretty",
                   fixPNG: true,
                   opacity: 0.95,
                   left: 0
               });
           });
       </script>

       <link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/jquery/jquery.screen.css"/>
       <!-- JQuery Validation plugin -->
       <!-- link rel="stylesheet" type="text/css" media="screen" href="/gweb/css/jquery/jquery.tooltip.css"/ -->
       <script src="/gweb/javascripts/jquery/jquery.js" type="text/javascript"></script>
       <script src="/gweb/javascripts/jquery/jquery.validate.js" type="text/javascript"></script>
       <script src="/gweb/javascripts/jquery/cmxforms.js" type="text/javascript"></script>




</head>


<body class="tundra" width="800">

<div id="body">
    <div id="logoBar">
        <img id="logo" src="/gweb/images/browser.png" width="48" height="48" alt="logo"/>

    </div>

    <br/>


    <div id="menu">
             <jsp:include page="/pages/menu/menu.jsp"/>
    </div>

    <div id="paragraph" nowrap>
    Provide your login credentials. If you forgot you passord you can use the forgot password.
</div>

    <div id="content">

        

        <form class="decoratedform" id="loginForm" method="POST" action="/gweb/j_acegi_security_check">
            <table>
                <tr>
                    <td nowrap><label for="j_username">User name</label></td>
                    <td nowrap>
                        <input type="text" name="j_username" class="required" minlength="4" maxlength="15"
                                      title="Please enter username with at least 3 and max 15 characters!"/>
                    </td>
                    <td class="status"></td>


                </tr>
                <tr>
                    <td nowrap><label for="j_password">Password</label></td>
                    <td nowrap><input type="password" name="j_password" class="required" minlength="4"
                                      title="Please enter password with at least 4 characters!!"/>
                    </td>
                    <td class="status"></td>

                </tr>
            </table>
            <input name="submit" id="loginsubmit_button" type="submit" value="Log in"/>
        </form>

    </div>
</div>

<div id="footer">
    <p class="copyright">&copy; <a href="http://grouter.berlios.de/">Grouter</a></p>

    <div class="links">
        <a href="http://apache.maven.org/" title="Maven">Maven</a> &#183;
    </div>
</div>


</body>

<script type="text/javascript">
    $("j_username").focus();
</script>

</html>
