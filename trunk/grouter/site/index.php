<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
  <meta http-equiv="CONTENT-TYPE" content="text/html; charset=windows-1252">
  <title>Welcome to Grouter</title>
  <style type="text/css" media="all">
@import url("./css/site.css");
.style2 {color: #003399}
  </style>
</head>


<body style="direction: ltr;" lang="en-GB">


<div id="banner" dir="ltr">
<p style="margin-bottom: 0cm;"><span class="style2"><a name="bannerLeft"></a></span><br>
</p>
</div>

<div id="breadcrumbs" dir="ltr">
<div class="xright"> 
<p style="margin-bottom: 0cm;"><a href="http://developer.berlios.org/">BerliOS</a> | <a href="http://maven.apache.org/">Maven 2</a>  </p>

</div>
</div>

<!-- Navigation -->
<?php include ("navigation.php"); ?>

<div id="bodyColumn" dir="ltr">

<div id="contentBox" dir="ltr">
<h2 class="western">The home of the grouter</h2>

<div id="downloadbox" dir="ltr">

<p><a href="download.html"><img src="pics/folder-open.gif" name="graphics4" align="bottom" border="0" height="14" width="26"></a><a href="download.php"> Download grouter</a>.0.0.1 </p>

<ul>

  <li>
    <p style="margin-bottom: 0cm;"><a href="download.html#requirements">System Requirements</a>

    </p>

  </li>

  <li>
    <p style="margin-bottom: 0cm;"><a href="download.html#installation">Installation Instructions</a>
    </p>

  </li>

  <li>
    <p style="margin-bottom: 0cm;"><a href="release-notes.html">Release Notes</a> </p>

  </li>

  <li>
    <p style="margin-bottom: 0cm;"><a href="guides/getting-started/index.html">Getting Started</a>
    </p>

  </li>

  <li>
    <p><a href="guides/index.html">Documentation</a>
    </p>

  </li>

</ul>

</div>

<p>Grouter is a message router for receiving messages, sending
messages, transforming messages, backing up messages etc. And above all it is my hobby project where I can test new cool stuff. </p>
<p>The grouter consists of different components: </p>
<p>- core is the main and most important component. It consists of nodes and of a global section defined in a xml schema file. A node can be of different types, e.g. a file-to-file node will read files and produce files. A node can also transform a file using an xsl transformation from one format to another. Every message processed can be sent to a central hub called a domain.</p>
<p>- domain is the central component where the messages are persisted in a database. The domain is defined using ejb3 entities and Hibernate and runs within a JEE container. </p>
<h4 class="western"><img src="pics/domain.png" width="700" height="300"></h4>
<p class="western">- presentation is a component for administrating the grouter and receiving real time events on updates from the domain component (throug JMS messaging).</p>
</div>

</div>

</body>
</html>
