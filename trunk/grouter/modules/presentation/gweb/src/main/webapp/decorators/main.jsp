<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
    <title>Grouter - <decorator:title/>
    </title>
    <link href="../css/main.css" type="text/css" rel="stylesheet"/>
    <link href="../css/decorator.css" type="text/css" rel="stylesheet"/>
    <style type="text/css">
    </style>
    <decorator:head/>
</head>

<body>
<div id="container">
    <div id="logoBar">
        <img id="logo" src="../images/browser.png" width="48" height="48" alt="logo"/>
        <decorator:title/>

        <div id="userSettings" >
        <table >
            <tr>
                <td>
                    <a  href="/gweb/grouter/select.do">Settings</a>
                </td>
                <td>
                    <a  href="/j_acegi_logout">Log out</a>
                </td>
                <td>
                    <a  href="/gweb/auth/login.do">Log in</a>

                </td>
                <td>
                    <a href="">User : </a>
                </td>

            </tr>
        </table>
            </div>
    </div>

    <div id="menu">
        <c:import url="../faces/menu/menu.jsp"/>
    </div>

    <div id="innerBody">
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