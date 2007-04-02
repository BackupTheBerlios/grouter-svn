package org.grouter.core;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.lang.Validate;
import org.grouter.common.config.ConfigHandler;
import org.grouter.core.util.ThreadPoolService;
import org.grouter.core.util.SchedulerService;
import org.grouter.core.util.file.FileUtils;
import org.grouter.core.config.ConfigFactory;
import org.grouter.domain.servicelayer.spring.logging.JDBCLogStrategy;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Node;
import org.grouter.domain.servicelayer.RouterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.BeanFactory;

import java.util.*;

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
public class GRouterServer implements Runnable
{

    private static Logger logger = Logger.getLogger(GRouterServer.class);
    // spring context
    ApplicationContext context;
    private static String CONFIGFILE = "grouter.xml";
    private ConfigHandler configHandler;
    //    private GrouterConfig grouterConfig;
    private ThreadPoolService nodeThreadPoolHandler = new ThreadPoolService();
    private static final String GROUTER_CONFIG = "grouter.config";
    private Router router;
    SchedulerService schedulerService;
    private static final String ROUTER_SERVICE = "routerService";

    /**
     * Constructor.
     *
     * @param router
     */
    public GRouterServer(Router router)
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

    /**
     * Constructor tries to locate config file using System.getProperty("grouter.config")
     *
     * @throws IllegalArgumentException if grouÒterConfig == null
     */
    public GRouterServer()
    {
        String grouterConfig = System.getProperty(GROUTER_CONFIG);
        Validate.notNull(grouterConfig, "Could not get property with key :" + GROUTER_CONFIG + ". Have you " +
                "provided a -D parameter for Java vm?");
    }

    /**
     * Constructor with a config path parameter.
     *
     * @param configPath
     * @throws IllegalArgumentException if configPath == null
     */
    public GRouterServer(String configPath)
    {
        if (!FileUtils.isValidPath(configPath))
        {
            throw new IllegalArgumentException("Invalid path given to config file!!! Path was " + configPath);
        }

        try
        {
            logger.info("Using config path : " + configPath);
            configHandler = new ConfigHandler(configPath, null);
            router = ConfigFactory.createRouter(configHandler.getGrouterConfigDocument().getGrouter());

            initRouter(router);
            //nodeThreadPoolHandler.initNodeThreadScheduling( this.router.getNodes()  );
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
        schedulerService = new SchedulerService(router.getNodes());

        BeanFactory factory = (BeanFactory) context;
        RouterService routerService = (RouterService) factory.getBean( ROUTER_SERVICE );

        routerService.saveRouter( router );

    }


    /**
     * Starts GRouter... and adds shutdown hook.
     *
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        String grouterHome = System.getProperty("user.dir");
        logger.info("Working dir : " + grouterHome);
        String configFile = "/core/core-router/src/test/resources/grouterconfig_file_file.xml";

        GRouterServer grouter = new GRouterServer(grouterHome + configFile);
        grouter.startGrouter();


    }


    public void startGrouter() throws Exception
    {
        schedulerService.startAllNodes();


        Thread thr = new Thread(this);
        logger.info("Adding shutdown hook");
        //       Runtime.getRuntime().addShutdownHook( thr );
        thr.start();
    }

    /**
     * Reads config files for log4j and siri. 
     */
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
            } catch (InterruptedException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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


    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        // "context-domain-aop.xml","context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-sessionfactory.xml", "context-domain-service.xml",
                        "context-router.xml"
                };
    }


}
