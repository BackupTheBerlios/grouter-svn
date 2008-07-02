<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>Welcome to Grouter</title>
    <link href="css/page_main.css" type="text/css" rel="stylesheet" />
    <link href="css/lightwindow.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" charset="iso8859-1" src="javascripts/common.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="javascripts/engine.js"></script>
    <script type="text/javascript" charset="iso8859-1" src="javascripts/util.js"></script>
    <link rel="icon" href="/images/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div id="mainContent">
        <?php include ("menu_logo_and_search.php"); ?>     

		<h1>Welcome to Grouter! </h1><br/>
        Grouter is a message service bus that can be used to read data and write data to and from different
        endpoints. An endpoint can be of type file, ftp, http etc.<br/>
        One Grouter instance holds a set of nodes which act as scheduled workers independently from each other.
        A node can be configured to transform input fom one endpoint into another format on the output endpoint
        using a plugins.<br/>
        <center><img src="images/small_domain.png" alt = "umlpic"/></center>
        <br/>

        Further more a Grouter instance might be part of a set of Grouter instances in an application domain,
        which helps to scale up the message service bus within an enterpise environment.<br/>
        <br/>
        In the simplest use case scenario one Grouter instance runs as a single service on a network node as a Java
        process. Monitoring is done through log4j log files.<br/>
        In a second use case scenario a web application - Gweb - can be installed into a JEE container and
        thereby get easier monitoring and managing of nodes, users and jobs for the Grouter.
        <br/>
        <br/>
        Read more under the Documentation tab, dowload and give it a try. Feeback is always welcome.
        <br/>
        <?php include ("footer.php"); ?>
</div>

</body>

</html>
