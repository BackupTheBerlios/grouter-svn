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
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.grouter.common.config.XmlConfigHandler;
import org.grouter.core.config.ConfigFactory;
import org.grouter.core.util.SchedulerService;
import org.grouter.core.util.file.FileUtils;
import org.grouter.domain.RouterCache;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.SettingsContext;
import org.grouter.domain.service.RouterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.io.File;
import java.io.IOException;
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
final public class RouterServerImpl implements Runnable, RemoteRouterService
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

            overrideContext();

            initApplicationContext();

            forceMakeDirectories();

            RouterCache.init(router);

            updatePersistentState();

     //       initRmi();

            logger.info(router.printNodes());

            logStatus();
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
     */
    private void initApplicationContext() throws Exception
    {

        logger.info("Initializing router context");
        context = new ClassPathXmlApplicationContext(getConfigLocations());
        routerService = (RouterService) context.getBean(ROUTER_SERVICE);
        schedulerService = new SchedulerService(router.getNodes());
    }

    /**
     * Override properties set in Spring context files.
     */
    private void overrideContext()
    {
        String driverClassName = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_DRIVERCLASSNAME);
        String url = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_URL);
        String userName = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_USERNAME);
        String password = (String) router.getSettings().getSettingsContext().get(SettingsContext.KEY_SETTINGS_DATASOURCE_PASSWORD);

        if (StringUtils.isNotEmpty(driverClassName) && StringUtils.isNotEmpty(url) &&
                StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password))
        {
            logger.info("Overriding datasource context from router config file");

            logger.info("Overriding datasource context from router config file: grouter.db.driver=" + driverClassName);
            System.setProperty("grouter.db.driver", driverClassName);
            logger.info("Overriding datasource context from router config file: grouter.db.url=" + url);
            System.setProperty("grouter.db.url", url);
            logger.info("Overriding datasource context from router config file: grouter.db.username=" + userName);
            System.setProperty("grouter.db.username", userName);
            logger.info("Overriding datasource context from router config file: grouter.db.password=" + password);
            System.setProperty("grouter.db.password", password);
        }


        final String registryPort =    Integer.toString( router.getRmiRegistryPort() );
        final String rmiSericePort = Integer.toString(router.getRmiServicePort());
        if (StringUtils.isNotEmpty(rmiSericePort) && StringUtils.isNotEmpty(registryPort) )
        {
            logger.info("Overriding rmi properties in context files from grouter config");

            logger.info("grouter.rmi.registryPort:" + registryPort);
            System.setProperty("grouter.rmi.registryPort", registryPort);
            logger.info("grouter.rmi.servicePort:" + rmiSericePort);
            System.setProperty("grouter.rmi.servicePort", rmiSericePort);


        }
    }


    /**
     * Save configuration of raouter in database and also update any nodes that do not have
     * a parent.
     */
    private void updatePersistentState()
    {
        logger.info("Saving router state in database");
        routerService.saveRouter(router);
        routerService.updateStateForNotConfiguredNodes(router.getId(), router.getNodes());
        logger.info("Router state saved in database");
    }

    private void forceCreateHomePathOnStartUp()
    {

    }


    /**
     * If there is a rmi port configured then we register the router in jndi.
     * <p/>
     * Lookup up bean in  context and use our config files parameters to set
     * port etc and finally call prepare to fire things up.
     */
    private void initRmi()
    {
        logger.info("Check if we are to register as RMI service");
        if (router.getRmiRegistryPort() > 0 || router.getRmiServicePort() > 0)
        {
            try                   
            {
                logger.info("Trying to register in router as RMI service jndi");
                logger.info("Rmiregistryport :" + router.getRmiRegistryPort());
                logger.info("Rmiserviceport :" + router.getRmiServicePort());
                logger.debug("Get rmi exporter bean from context :" + RMI_SERVICE_EXPORTER_FACTORY_BEAN);
                RmiServiceExporter rmiServiceExporter = (RmiServiceExporter) context.getBean(RMI_SERVICE_EXPORTER_FACTORY_BEAN);
                rmiServiceExporter.setRegistryPort(router.getRmiRegistryPort());
                rmiServiceExporter.setServicePort(router.getRmiServicePort());
                logger.debug("Calling prepare on rmi exporter" );
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

    private void logStatus()
    {
        logger.info("Startup statistics");
        logger.info("Number of nodes in conf file :" + router.getNodes().size());
        for (Node node : router.getNodes())
        {
            logger.info("{id=" + node.getId() + ",name=" + node.getDisplayName() + "}");
        }
    }


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
        String homePath = router.getHomePath();
        for (Node node : router.getNodes())
        {
            if (node.getCreateDirectories().booleanValue() == true)
            {
                logger.info("Trying to create node directories. Path for this node:" + homePath + node.getId());
                try
                {
                    org.apache.commons.io.FileUtils.forceMkdir(new File(homePath + File.separator + "nodes" + File.separator + node.getId() + File.separator + "internal" + File.separator + "in"));
                    org.apache.commons.io.FileUtils.forceMkdir(new File(homePath + File.separator + "nodes" + File.separator + node.getId() + File.separator + "internal" + File.separator + "out"));
                } catch (IOException e)
                {
                    logger.error("Could not create directory for :" + homePath + node.getId());
                }

                if (node.getInBound().getEndPointType().equals(EndPointType.FILE_READER))
                {

                    try
                    {
                        org.apache.commons.io.FileUtils.forceMkdir(new File(homePath + File.separator + "nodes" + File.separator + node.getId() + File.separator + "in"));
                    } catch (IOException e)
                    {
                        logger.error("Could not create directory for :" + homePath + node.getId());
                    }
                }
            } else
            {
                logger.info("Not creating directories for node:" + node.getId());

            }

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
                        "context-domain-aop.xml",
                        "context-domain-datasource.xml",
                        "context-domain-service.xml",
                        "context-domain-dao.xml",
                        "context-domain-sessionfactory.xml" ,
                        "context-router.xml"//, "context-router-rmi.xml"
                };
    }


    /**
     * Delegates to scheduler service for stopping a node after looking up a node in db.
     *
     * @param nodeId id of node to stop
     * @throws RemoteException if we encounter som exception trying to stop a node
     */
    public void stopNode(Long nodeId) throws RemoteException
    {
        Node node = routerService.findNodeById(nodeId);

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
            String configPath = System.getProperty("grouter.configfile");
            File configFile = new File(configPath);
            RouterServerImpl router = new RouterServerImpl(configFile.getPath());
            router.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
