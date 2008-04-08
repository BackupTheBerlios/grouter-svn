<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <meta http-equiv="CONTENT-TYPE" content="text/html; charset=ISO-8859-1">
  <title>Welcome to GRouter</title>
  <style type="text/css" media="all">
@import url("./css/site.css");
.style2 {color: #003399}
  </style>
</head>


<body style="direction: ltr;" lang="en-GB">


<div id="banner" dir="ltr">
	<img src="pics/browser.png" alt="logo"/> 
	<p style="margin-bottom: 0cm;"><span class="style2"><a name="bannerLeft"></a></span><br></p>
</div>

<div id="breadcrumbs" dir="ltr">
	<div class="xright"> 
		<p style="margin-bottom: 0cm;"><a href="http://grouter.berlios.de/">Grouter</a> |  <a href="http://developer.berlios.de/">BerliOS</a> | <a href="http://maven.apache.org/">Maven 2</a>  </p>
	</div>
</div>

<!-- Navigation -->
<?php include ("navigation.php"); ?>

<div id="bodyColumn" dir="ltr">
<div id="contentBox" dir="ltr">

<div id="downloadbox" dir="ltr">
<p><a href="download.html"><img src="pics/folder-open.gif" alt="" align="bottom" border="0" height="14" width="26"></a><a href="download.php"> Download grouter - ver 0.1.1 </a> </p>
<ul>
  <li>
    <p style="margin-bottom: 0cm;"><a href="systemrequirements.html">System Requirements</a>
    </p>
  </li>
  <li>
    <p style="margin-bottom: 0cm;"><a href="download.html#installation">Installation Instructions</a></p>
  </li>
  <li>
    <p style="margin-bottom: 0cm;"><a href="releasenotes.php">Release Notes</a> </p>
  </li>
  <li>
    <p style="margin-bottom: 0cm;"><a href="gettingstarted.php">Getting Started</a>
    </p>
  </li>
  <li>
    <p><a href="documentation.php">Documentation</a>
    </p>
  </li>
</ul>
</div>

<h2 class="western">Grouter intro</h2>

<p>Grouter is a message router and job scheduler all in one. Althought the grouter can be used as a fancy tool for editing,creating and viewing jobs and their execution - 
the main purpose is for receiving messages and sending
messages on different endpoints. A Grouter contains one or several nodes and each node holds on inbound endpoint and one outbound endpoint.  <br/>
An endpoint can be of different types like file, ftp, http etc. handling different types of protocols. <br/>
Attached to a Node one can specify a filter or even a transformation. Finally, associated to an endpoint is an endpointcontext holding information needed for a specific endpointtype - e.g. for a ftp endpointtype a url, a port and a file list can be given. <br/>
The grouter web component enables users to manage users, nodes, jobs and query against the database or against a Lucened index for messages and other indexed entities.<br/>
The core grouter uses a command pattern decoupling inbound and outbound enpoints for great flexibility and also to enable a template pattern for how a message is handled on in and out bound endpoints.
<br/><br/>
The domain model looks like below UML class:<br/>
<img src="pics/small_domain.png" alt="uml diagram" />
<br/>
<p>The groutercomponents </p>
<ul>
<li>core
    <ul> 
	<li>router : the central and most important component runs as a Java process on any platform. Application domains with several Grouters can exist running on same host
	or different hosts. 
	Nodes are created by the router component and specified in an xsd governed xml file for configuration. Each node
	holds a reader and a writer thread scheduled using cron expressions and decoupled using an internal queue. A reader thread reads on its Endpoint and prodcues commands
	pushed on the internal queue which in turn are popped by the writer thread (also scheduled). Upon succesful read, write (transformation) a backup of the message is stored
	on file and also - and this is optional - if configured a messages is sent to a central hub which stores the message and meta data in a database. A router lso exposes an
	remote interface so administrative clients (gswing and gweb) can use callbacks into the router to suspend a working node. Global jobs will run through all nodes and backup
	messages on a scheduled intervall (to preserve disk space).
	<li>domain : is the central component where the messages are persisted in a database. The domain component holds transaction demarcation in the service layer, which utilizes 
	a data access layer against domain entities. The domain entities are defined both as ejb3 entities and Hibernate entitis. A generic DAO pattern is used to accomdate this. 
	The domain component should be deployed to a JEE container like Resin, Tomcat or JBoss. </p>
	</ul>
<li> presentation
	<ul>
	<li>gweb is a component for administrating the grouter and receiving real time events on updates from the domain component 
(through reverse Ajax). The component uses Spring MVC and Spring Security providing full role based authorization services.
	<li>gswing is a component for administrating the grouter and receiving real time event just like the gweb component. The difference
	is that this component is Swing based, i.e. it is a full blown client application. It uses components from Jide.
	</ul>
</ul>
</div>

</div>

</body>
</html>
