<html>
<head>
    <title>
        Login
    </title>
    <link href="../../css/page_main.css" rel="stylesheet"/>
</head>
<body>

<div id="contentHeader">Grouter login.</div>

<div id="content">
    Choose grouter and enter user name and pwd to login.


    <form action="/gweb/j_acegi_security_check" method="POST">
        <table>
            <tr>
                <td>Användare:</td>
                <td><input type='text' id="j_username" name='j_username' /> </td>
            </tr>
            <tr>
                <td>Lösenord:</td>
                <td><input type='password' name='j_password' /></td>
            </tr>
            <tr>
                <td align="right" colspan='2'><input name="submit" type="submit" value="Logga in"/></td>
            </tr>
        </table>
    </form>

</div>

<script type="text/javascript">
    $("j_username").focus();
</script>

</body>
</html>

