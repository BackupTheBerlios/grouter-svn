<html>
<head>
    <title>
        Login
    </title>

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

</head>
<body>


<div id="paragraph" nowrap>
    Provide your login credentials. If you forgot you passord you can use the forgot password.
</div>


<div id="content">
    <form class="cmxform" id="loginForm" method="POST" action="/gweb/j_acegi_security_check">
        <table>
            <tr>
                <td nowrap><label for="j_username">User name</label></td>
                <td  nowrap><input type="text" name="j_username" class="required" minlength="4"
                           title="Please enter username with at least 3 and max 15 characters!"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td nowrap><label for="j_password">Password</label></td>
                <td nowrap><input type="password" name="j_password" class="required" minlength="4"
                           title="Please enter password with at least 4 characters!!"/>
                </td>
                <td></td>
            </tr>
        </table>
        <input name="submit" id="loginsubmit_button" type="submit" value="Log in"/>
    </form>



</div>

<script type="text/javascript">
    $("j_username").focus();
</script>

</body>
</html>

