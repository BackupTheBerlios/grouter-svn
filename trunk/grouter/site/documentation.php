<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>

    <meta http-equiv="CONTENT-TYPE" content="text/html; charset=windows-1252">
    <title>Welcome to Grouter</title>
    <style type="text/css" media="all">
        @import url( "./css/site.css" );
        .style2 {
            color: #003399
        }
    </style>
</head>


<body style="direction: ltr;" lang="en-GB">

<div id="banner" dir="ltr">
    <p style="margin-bottom: 0cm;"><span class="style2"><a name="bannerLeft"></a></span><br>
    </p>
</div>


<!-- Navigation -->
<?php include ("breadcrumb.php"); ?>


<!-- Navigation -->
<?php include ("navigation.php"); ?>

<div id="bodyColumn" dir="ltr">

    <div id="contentBox" dir="ltr">
        <h2 class="western">Documentation</h2>

        

        <p>Grouter is a message router for receiving messages, sending
            messages, transforming messages, backing up messages etc. And above all it is my hobby project where I can
            test new cool stuff. </p>

        <h4 class="western">Checking out the source </h4>
        <p>The grouter project's BerliOS Developer SVN repository can be checked out through anonymous HTTP with the following instruction set. </p>
        <p class="source">svn checkout http://svn.berlios.de/svnroot/repos/grouter/trunk </p>

        The grouter  project's developers can access the SVN tree via this method. Substitute developername with the proper value. Enter your site password when prompted.</p>
        <p class="source">svn checkout https://developername@svn.berlios.de/svnroot/repos/grouter/trunk </p>

      <h4>Building components</h4>
        <p>Enter Maven nirvana - if you do not know Maven yet this is the time to learn it. This projects pom file are documented well - read the source and read up on Maven. First thing you will do is issue a build command to Maven. On first run this will pull down all third party dependencies for you and store them in your local dependecy cache ( ~/.m2/repository). Chenge directory to grouter/modules and do: </p>
        <p class="source">mvn clean install</p>
        <p>If you want to skip the tests then add -Dmaven.test.skip=true as a parameter - which of course make the build much faster but more error prone.</p>
        <p class="source">mvn -Dmaven.test.skip=true clean install</p>
        <p> This should produce something like:</p>
        <p class="source">[INFO] ------------------------------------------------------------------------<br>
          [INFO] BUILD SUCCESSFUL<br>
          [INFO] ------------------------------------------------------------------------<br>
          [INFO] Total time: 14 seconds<br>
          [INFO] Finished at: Tue Oct 03 12:58:04 CEST 2006<br>
          [INFO] Final Memory: 9M/16M<br>
          [INFO] ------------------------------------------------------------------------ <br> 
       </p>
        <p>All components built for the grouter can be found in your local dependecy cache under ~~.m2/repository/org/grouter or under the target folder for every Maven project. </p>
        <h4 class="western">Building an assembly</h4>
        <p class="western">An assmembly is a deployment artifact for a standalone process - in this case the grouter core component. Assemblies are built using Maven and produces a compressed artifact with jars, scripts and folder structure.</p>
        <p class="western">TODO - finish this !!!!!!    </p>
        <h4 class="western">Installing the grouter </h4>
        <p>There are two ways to run the grouter - either standalone or together with a JEE container. Running with the
            JEE container is the preferred way since it enables statistics for messages and also gives you the
            oppurtunity to administer the grouter using a fancy client. Running standalone basically means running the grouter core component - which is fine but gives you no feedback on what is going on.</p>
        <p>Unpack the grouter core component in a directory of your choice. Alter the config file for the grouter and add nodes, configure location of jee server etc. Using a good editor with auto completion based on xsd file is advisable (Intellij has this built in - no plugin needed).<br>
          Deploy the ear file under your JEE server. You will need to set up two JMS Destinations - one queue and one topic. 	
</p>
        <p><br>
      </p>
    </div>

</div>

</body>
</html>
