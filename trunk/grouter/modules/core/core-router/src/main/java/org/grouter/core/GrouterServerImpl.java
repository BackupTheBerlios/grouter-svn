/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.core;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.commons.lang.Validate;
import org.grouter.common.config.ConfigHandler;
import org.grouter.core.util.SchedulerService;
import org.grouter.core.util.file.FileUtils;
import org.grouter.core.config.ConfigFactory;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Node;
import org.grouter.domain.servicelayer.RouterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;

/**
 * Main class for GRouter.
 * <p/>
 * Reads in all config and starts up all services, archiving thread, and binds to
 * jndi in j2ee app server.
 * <p/>
 * Adds shutdown hook to vm so that we get to write an email when we close down gracefully
 * using ctrl+c (win) or kill -15 (*nix).
 *
 * @author Georges Polyzois
 */
public class GrouterServerImpl implements Runnable, GrouterServer
{

    private static Logger logger = Logger.getLogger(GrouterServerImpl.class);

    // spring context
    ApplicationContext context;

    // handler for configuration file
    private ConfigHandler configHandler;

    private Router router;

    // scheduler service handles start / stop etc of nodes
    private static SchedulerService schedulerService;

    // operation against database are handled through this service
    static RouterService routerService;

    private static final String ROUTER_SERVICE = "routerService";

    /**
     * Constructor.
     *
     * @param router
     */
    public GrouterServerImpl(Router router)
    {
        Validate.notNull(router, "A null router can not be used to start.");
        try
        {
            initRouter(router);
        } catch (Exception e)
        {
            logger.error(e,e);
        }
    }

    public GrouterServerImpl()
    {
        
    }

    /**
     * Constructor with a config path parameter.
     *
     * @param configPath
     * @throws IllegalArgumentException if configPath == null
     */
    public GrouterServerImpl(String configPath)
    {
        if (!FileUtils.isValidFile(configPath))
        {
            throw new IllegalArgumentException("Invalid path given to config file!!! Path was " + configPath);
        }

        try
        {
            logger.info("Using config path : " + configPath);
            configHandler = new ConfigHandler(configPath, null);
            router = ConfigFactory.createRouter(configHandler.getGrouterConfigDocument().getGrouter());

            initRouter(router);
        }
        catch (Exception ex)
        {
            logger.error("Failed setup - exiting", ex);
            System.exit(0);
        }
    }


    /**
     * Load config file and store reference for further processing of config data.
     */
    private void initRouter(Router router) throws Exception
    {
        logger.info("Initializing router");

        context = new ClassPathXmlApplicationContext(getConfigLocations());
        routerService = (RouterService) context.getBean( ROUTER_SERVICE );
        schedulerService = new SchedulerService(router.getNodes() );
        //RmiServiceExporter rmiServiceExporter = (RmiServiceExporter) context.getBean("serviceExporter");

/*        RmiServiceExporter rmiServiceExporter = (RmiServiceExporter) context.getBean("rmiServiceExporterFactoryBean");
        rmiServiceExporter.setRegistryPort( router.getRmiRegistryPort() );
        rmiServiceExporter.setServicePort( router.getRmiServicePort() );
        rmiServiceExporter.prepare();
*/

//        String host = InetAddress.getLocalHost().getCanonicalHostName();
        //rmiServiceExporter.

        routerService.saveRouter( router );
        logger.info("Router state saved in database");
        logStatus(router);

    }

    private void logStatus(Router router)
    {
        logger.info( "Startup statistics" );
        logger.info( "Number of nodes in conf file :" + router.getNodes().size() );
        for (Node node : router.getNodes())
        {
            logger.info( "{id=" + node.getId() + ",name=" + node.getDisplayName() + "}" );
        }
    }

    /**
     * Starts GRouter... and adds shutdown hook.
     *
     
    public static void main(String[] args) throws Exception
    {
        String grouterHome = System.getProperty("user.dir");
        logger.info("Working dir : " + grouterHome);
        String configFile = "/core/core-router/src/test/resources/grouterconfig_file_file.xml";

        GRouterServer grouter = new GRouterServer(grouterHome + configFile);
        grouter.start();


    }   */


    /**
     * Start up all nodes and add shutdown hook in JVM.
     * @throws Exception
     */
    public void start() throws Exception
    {
        schedulerService.startAllNodes();
        Thread thr = new Thread(this);
        logger.info("Adding shutdown hook");
        //       Runtime.getRuntime().addShutdownHook( thr );
        thr.start();
    }

    /**
     * Reads config files for log4j and grouter. 
     */

    /*
    static
    {
        ResourceBundle resourceBundle = null;
        try
        {
            resourceBundle = ResourceBundle.getBundle("log4j");
            Enumeration proplist = resourceBundle.getKeys();
            proplist = resourceBundle.getKeys();
            Properties props = new Properties();
            while (proplist.hasMoreElements())
            {
                String tKey = (String) proplist.nextElement();
                props.setProperty(tKey, resourceBundle.getString(tKey));
            }
            PropertyConfigurator.configure(props);
            System.out.println("->Logging configured (log4j.properties)");
        }
        catch (MissingResourceException e)
        {
            System.out.println("Missing resource : log4j.properties");
            System.out.println("Grouter not started - see log file");
            System.exit(0);
        }
    }

    */


    /**
     * Called by vm when when we get a controlled exit.
     */
    public void onExit()
    {
        logger.debug("Exiting grouter...");
        NDC.remove();
//        sendEmail();
    }

    /**
     * Sends an email on shutting down grouter
     */
    /*   private void sendEmail()
        {
            logger.info("Trying to write an email alert using file : " + emailonshutdown);
            String email = null;
            try
            {
                javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
                Document document;
                factory.setNamespaceAware(true);
                javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
                InputSource source = new InputSource(new FileReaderThread(emailonshutdown));
                document = builder.parse(source);
                InFolderConfig firstchild = document.getFirstChild();
                NamedNodeMap attributes = firstchild.getAttributes();
                InFolderConfig message = attributes.getNamedItem("messagetype");
                InstantEmailMessageHandler.getInstance().handleMessage(document, null, null);
            }
            catch (Exception ex)
            {
                logger.error("Could not write an email alert", ex);
            }
        }
    */

    /**
     * Called by vm when when we get a controlled exit.
     */
    public void run()
    {
        logger.info("In run");
        while (true)
        {
            try
            {
                Thread.sleep(2000);
            } catch (Exception e)
            {
               logger.warn("Thread got an exception : " + e.getMessage() ); 
            }
        }
        //onExit();
    }

    // todo
    private void forceMakeDirectories( )
    {
        for (Node node : router.getNodes())
        {
            //if ( node.getInBound().getUri().startsWith("file://")   )
        }
    }


    /**
     * Context files which will be loaded be the grouter on startup.
     * @return
     */
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        // "context-domain-aop.xml","context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-sessionfactory.xml", "context-domain-service.xml",
                        "context-router.xml","context-router-rmi.xml"
                };
    }


    /**
     * Delegates to scheduler service for stopping a node after looking up a node in db.
     * @param nodeId id of node to stop
     * @throws RemoteException if we encounter som exception trying to stop a node
     */
    public void stopNode(String nodeId) throws RemoteException
    {
        Node node = routerService.findById( nodeId );

        logger.info("Stopping node :" + node.getId() );

        try
        {
            schedulerService.stop( node );
        } catch (Exception e)
        {
            throw new RemoteException( "Could not stop the node :" + nodeId + " Got exception :" + e.getMessage() );
        }

    }
}
