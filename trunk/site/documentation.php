<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; charset=windows-1252">
    <title>Welcome to Grouter - documentation</title>
    <style type="text/css" media="all">
        @import url( "./css/site.css" );
        .style2 {
            color: #003399
        }
    </style>
</head>


<body style="direction: ltr;" lang="en-GB">

<div id="banner" dir="ltr">
	<img src="pics/browser.png"/> 
    
</div>


<!-- Navigation -->
<?php include ("breadcrumb.php"); ?>


<!-- Navigation -->
<?php include ("navigation.php"); ?>

<div id="bodyColumn" dir="ltr">

    <div id="contentBox" dir="ltr">
        <h2 class="western">Documentation</h2>
        <h4 class="western">Check out the source </h4>
        <p>The grouter project's BerliOS Developer SVN repository can be checked out through anonymous HTTP with the following instruction set. </p>
        <p class="source">svn checkout http://svn.berlios.de/svnroot/repos/grouter/trunk </p>

        The grouter  project's developers can access the SVN tree via this method. Substitute developername with the proper value. Enter your site password when prompted.</p>
        <p class="source">svn checkout https://developername@svn.berlios.de/svnroot/repos/grouter/trunk </p>
		Viewing the source online using a web interface:    <br/>    
        <p class="source">http://svn.berlios.de/wsvn/grouter/trunk/grouter/?rev=0&sc=0</p>

      <h4>Building grouter</h4>
        <p>First thing you will do is issue a build command to Maven. On first run this will pull down all third party dependencies for you and store them in your 
        local dependecy cache ( ~/.m2/repository). <br/>
        Under root directory go to /modules/common/common-configbeans and do (only needed on first build) </p>
        <p class="source">mvn clean install</p>
        <p>Now change back to root and do:
        <p class="source">mvn clean install -Dmaven.test.skip=true</p>
        
        <p> This should produce something like:</p>
        <p class="source">[INFO] ------------------------------------------------------------------------<br/>
          [INFO] BUILD SUCCESSFUL<br/>
          [INFO] ------------------------------------------------------------------------<br/>
          [INFO] Total time: 14 seconds<br/>
          [INFO] Finished at: Tue Oct 03 12:58:04 CEST 2006<br/>
          [INFO] Final Memory: 9M/16M<br/>
          [INFO] ------------------------------------------------------------------------ <br/>
       </p>
        <p>All components built for the grouter can be found in your local dependecy cache under ~~.m2/repository/org/grouter or under the target folder for every Maven project. </p>
        
        <h4 class="western">Building an assembly (release)</h4>
        <p class="western">Grouter is using Maven's assembly plugin to produce different type of artifacts. Under the root folder you will find a folder named assemblies containing the different deployment artifacts. The pom file under assemblies specifies which deployment artifacts will be built - comment out the ones you are not interested in. The single most important one is called server.</p>
        <h4 class="western">Installing the grouter </h4>
        <p>There are two ways to run the grouter - either standalone or together with a JEE container and a database (Mysql). Running with the JEE container is the preferred way since it enables statistics for messages and also gives you the
            oppurtunity to administer the grouter using a web client interface. <br/>
            Running standalone basically means running the grouter only - which is fine but gives you no feedback on what is going on except through manual inspection or triggering of email alarms.</p><br/>
            Download or build the grouter deployment artifacts using Maven. Copy and unpack the file to the location where you intend to install the grouter. Under the root folder you will find an INSTALL.txt - read and follow instructions.
</p>
    </div>

</div>

</body>
</html>
