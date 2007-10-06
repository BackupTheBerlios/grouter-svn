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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.grouter.common.config.XmlConfigHandler;
import org.grouter.core.config.ConfigFactory;
import org.grouter.core.util.SchedulerService;
import org.grouter.core.util.file.FileUtils;
import org.grouter.domain.RouterCache;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.SettingsContext;
import org.grouter.domain.servicelayer.RouterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.io.File;
import java.rmi.RemoteException;

/**
 * Main class for grouter - parses config file, updates database, exposes remote interface,
 * fires of jobs, schedules nodes etc.
 * <p/>
 * Implements an interface exposed for remote clients.
 * <p/>
 * Adds shutdown hook to vm so that we get to write an email when we close down gracefully
 * using ctrl+c (win) or kill -15 (*nix).
 *
 * @author Georges Polyzois
 */
public class RouterServerImpl implements Runnable, RemoteRouterService
{

    private static Logger logger = Logger.getLogger(RouterServerImpl.class);
    // spring context
    ApplicationContext context;
    // handler for configuration file
    private XmlConfigHandler xmlConfigHandler;
    private Router router;
    // scheduler service handles start / stop etc of nodes
    private static SchedulerService schedulerService;
    // operation against database are handled through this service
    static RouterService routerService;
    private static final String ROUTER_SERVICE = "routerService";
    private static final String RMI_SERVICE_EXPORTER_FACTORY_BEAN = "rmiServiceExporterFactoryBean";

    /**
     * Constructor.
     *
     * @param router
     */
    public RouterServerImpl(Router router)
    {
        Validate.notNull(router, "A null router can not be used to start.");
        try
        {
            initApplicationContext(router);
        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }

    public RouterServerImpl()
    {

    }

    /**
     * Constructor with a config path parameter.
     *
     * @param configPath
     * @throws IllegalArgumentException if configPath == null
     */
    public RouterServerImpl(String configPath)
    {
        if (!FileUtils.isValidFile(configPath))
        {
            throw new IllegalArgumentException("Invalid path given to config file!!! Path was " + configPath);
        }

        try
        {
            logger.info("Using config path : " + configPath);
            readConfiguration(configPath);

            initApplicationContext(router);

            RouterCache.init(router);

            updateDatabase(router);
            initRmi(router);

            logger.info(router.printNodes());

            logStatus(router);
        }
        catch (Exception ex)
        {
            logger.error("Failed setup - exiting", ex);
            System.exit(0);
        }
    }

    private void readConfiguration(final String configPath)
    {
        logger.info("Parsing config xml for router");
        xmlConfigHandler = new XmlConfigHandler(configPath, null);
        router = ConfigFactory.createRouter(xmlConfigHandler.getGrouterConfigDocument().getGrouter());
        logger.info("Parsing completed. Router config was read.");
    }


    /**
     * Load config file and store reference for further processing of config data.
     *
     * @param router configuration
     */
    private void initApplicationContext(Router router) throws Exception
    {
        overrideContext(router);

        logger.info("Initializing router context");
        context = new ClassPathXmlApplicationContext(getConfigLocations());
        routerService = (RouterService) context.getBean(ROUTER_SERVICE);
        schedulerService = new SchedulerService(router.getNodes());



    }

    private void overrideContext(final Router router)
    {
        logger.info("Overriding datasource context from router config file");
        String driverClassName = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_DRIVERCLASSNAME );
        String url = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_URL );
        String userName = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_USERNAME );
        String password = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_PASSWORD );

        System.setProperty( "grouter.db.driver",driverClassName );
        System.setProperty( "grouter.db.url",url );
        System.setProperty( "grouter.db.username",userName );
        System.setProperty( "grouter.db.password",password );

        //logger.info("Looking up datasource");
        /*DriverManagerDataSource driverManagerDataSource = (DriverManagerDataSource) context.getBean( "dataSource" );

        if( StringUtils.isNotEmpty( url ) )
        {
            logger.info("Setting datasource url :" + url);
            driverManagerDataSource.setUrl( url );
        }
        if( StringUtils.isNotEmpty( driverClassName ) )
        {
            logger.info("Setting datasource driverClassName :" + driverClassName);
            driverManagerDataSource.setDriverClassName( driverClassName );
        }
        if( StringUtils.isNotEmpty( userName ) )
        {
            logger.info("Setting datasource userName :" + userName);
            driverManagerDataSource.setUsername(userName );
        }
        if( StringUtils.isNotEmpty( password ) )
        {
            logger.info("Setting datasource password :" + password);
            driverManagerDataSource.setPassword( password );
        }
        */
    }


    /**
     * Save configuration of raouter in database and also update any nodes that do not have
     * a parent.
     *
     * @param router configuration
     */
    private void updateDatabase(final Router router)
    {
        logger.info("Saving router state in database");
        routerService.saveRouter(router);
        routerService.updateStateForNotConfiguredNodes(router.getId(), router.getNodes());
        logger.info("Router state saved in database");

    }

    /**
     * If there is a rmi port configured then we register the router in jndi.
     * <p/>
     * Liokup up bean in  context and use our config files parameters to set
     * port etc and finally call prepare to fire things up.
     *
     * @param router configuration
     */
    private void initRmi(final Router router)
    {
        logger.info("Check if we are to register as RMI service");
        if (router.getRmiRegistryPort() != null || router.getRmiServicePort() != null)
        {
            try
            {
                logger.info("Try to register in router as RMI service jndi");
                RmiServiceExporter rmiServiceExporter = (RmiServiceExporter) context.getBean(RMI_SERVICE_EXPORTER_FACTORY_BEAN);
                rmiServiceExporter.setRegistryPort(router.getRmiRegistryPort());
                rmiServiceExporter.setServicePort(router.getRmiServicePort());
                rmiServiceExporter.prepare();
                logger.info("Registered ok as RMI service jndi");
            } catch (RemoteException e)
            {
                logger.error("Failed to register router as RMI service in JNDI", e);
            }
        } else
        {
            logger.info("Router was no tconfigured to use RMI - rmi ports missing in config file");

        }

    }

    private void logStatus(Router router)
    {
        logger.info("Startup statistics");
        logger.info("Number of nodes in conf file :" + router.getNodes().size());
        for (Node node : router.getNodes())
        {
            logger.info("{id=" + node.getId() + ",name=" + node.getDisplayName() + "}");
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
     *
     * @throws Exception
     */
    public void start() throws Exception
    {
        schedulerService.startAllNodes();

        // update the status of the nodes
        routerService.saveRouter(router);

        Thread thr = new Thread(this);
        logger.info("Adding shutdown hook");
        //       Runtime.getRuntime().addShutdownHook( thr );
        thr.start();
    }


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
                logger.warn("Thread got an exception : " + e.getMessage());
            }
        }
        //onExit();
    }

    // todo
    private void forceMakeDirectories()
    {
        for (Node node : router.getNodes())
        {
            //if ( node.getInBound().getUri().startsWith("file://")   )
        }
    }


    /**
     * Context files which will be loaded be the grouter on startup.
     *
     * @return
     */
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        // "context-domain-aop.xml","context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-sessionfactory.xml", "context-domain-service.xml",
                        "context-router.xml", "context-router-rmi.xml"
                };
    }


    /**
     * Delegates to scheduler service for stopping a node after looking up a node in db.
     *
     * @param nodeId id of node to stop
     * @throws RemoteException if we encounter som exception trying to stop a node
     */
    public void stopNode(String nodeId) throws RemoteException
    {
        Node node = routerService.findById(nodeId);

        logger.info("Stopping node :" + node.getId());

        try
        {
            schedulerService.stop(node);
        } catch (Exception e)
        {
            throw new RemoteException("Could not stop the node :" + nodeId + " Got exception :" + e.getMessage());
        }

    }

    public static void main(String[] args)
    {
        
        try
        {
            String configPath = System.getProperty( "grouter.configfile" );
            File configFile = new File( configPath );
            RouterServerImpl router = new RouterServerImpl( configFile.getPath() );
            router.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
