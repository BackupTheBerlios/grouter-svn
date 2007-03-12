package org.grouter.core;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.lang.Validate;
import org.grouter.common.config.ConfigHandler;
import org.grouter.core.util.ThreadPoolService;
import org.grouter.core.util.file.FileUtils;
import org.grouter.core.config.ConfigFactory;
import org.grouter.domain.entities.Router;

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
    private HashMap nodeThreads = new HashMap();
    private static String CONFIGFILE = "grouter.xml";
    private ConfigHandler configHandler;
//    private GrouterConfig grouterConfig;
    private ThreadPoolService nodeThreadPoolHandler = new ThreadPoolService();
    private static final String GROUTER_CONFIG = "grouter.config";
    private Router router;

    /**
     * Constructor.
     *
     * @param router
     */
    public GRouterServer( Router router)
    {
        Validate.notNull( router, "A null router can not be used to start." );
    }

    /**
     * Constructor tries to locate config file using System.getProperty("grouter.config")
     *
     * @throws IllegalArgumentException if grou�terConfig == null
     */
    public GRouterServer()
    {
        String grouterConfig = System.getProperty(GROUTER_CONFIG);
        Validate.notNull( grouterConfig, "Could not get property with key :" + GROUTER_CONFIG + ". Have you " +
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
        if(!FileUtils.isValidPath(configPath))
        {
            throw new IllegalArgumentException("Invalid path given to config file!!! Path was " + configPath);
        }

        try
        {
            logger.info("Using config path : " + configPath);
            initRouter( configPath );
//            this.grouterConfig = getGrouterConfig(configPath);
            nodeThreadPoolHandler.initNodeThreadScheduling( this.router.getNodes()  );
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
    private void initRouter(String configPath)
    {
        configHandler = new ConfigHandler(configPath, null);
        router = ConfigFactory.createRouter( configHandler.getGrouterConfigDocument().getGrouter() );
    }


    /**
     * Starts GRouter... and adds shutdown hook.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        String grouterHome = System.getProperty("user.dir");
        logger.info("Working dir : " + grouterHome);
        String configFile = "/router/core/src/config/grouter-file-file.xml";


        GRouterServer grouter = new GRouterServer( grouterHome + configFile);

        Thread thr = new Thread(grouter);
        Runtime.getRuntime().addShutdownHook(thr);
    }


    public void startGrouter()
    {
        Thread thr = new Thread( this );
        thr.start();
    }

    /*   private void startErrorHandlers(Service[] arrServices)
        {
            arrServices = null;
            if (arrServices != null)
            {
                // For every service ..
                for (int serviceNr = 0; serviceNr < arrServices.length; serviceNr++)
                {
                    // If service is in list and has attribute error folder (Pollers do not have this attribute)
                    Service arrService = arrServices[serviceNr];
                    if (arrService.getErrorfoldername() != null)
                    {
                        File readFromErrorFolder = new File(arrService.getErrorfoldername());
                        File[] curFiles = readFromErrorFolder.listFiles();
                        if (!arrService.isErrorhandlerOn())
                        {
                            logger.info("No resending enabled for service  : " + arrService.getId());
                            NDC.pop();
                            return;
                        }
                        else if (curFiles == null || curFiles.length == 0)
                        {
                            logger.info("Error resend is on for service but no files to resend for service at this moment : " + arrService.getId());
                            NDC.pop();
                            return;
                        }
                        else if (curFiles != null && curFiles.length == 1 && curFiles[0].isDirectory())
                        {
                            logger.info("Error resend is on for service but there are only folders in error folder for service (only subfolder exist) : " + arrService.getId());
                            NDC.pop();
                            return;
                        }
                        else
                        {
                            logger.info(curFiles.length + " number of files found for resending for service  : " + arrService.getId());

                            // todo handle all types of services
                            if (arrService.getType().equals(ServiceFactory.FILETOFILE))
                            {
                                //                     FileToFileErrorMessageHandler fileToFileErrorMessageHandler =  new FileToFileErrorMessageHandler( arrService, curFiles , arrService.gett );
                            }
                            else if (arrService.getType().equals(ServiceFactory.FILETOEJB))
                            {

                            }
                            else if (arrService.getType().equals(ServiceFactory.FILETOEMAIL))
                            {
                            }
                        }
                    }
                }
            }
        }
    */
    /*    private void startRemoteServicesServer()
        {
            if (System.getSecurityManager() == null)
            {
                System.setSecurityManager(new RMISecurityManager());
            }

            try
            {
                logger.info("Starting core-server service for remote operations. Binding " + System.getProperty("iris.rmi.service.name"));
                irisOperations = new IrisOperationsImpl(nodeThreads);
                Naming.rebind(System.getProperty("iris.rmi.service.name"), irisOperations);
                logger.info("Started core-server service for remote operations is now bound to RMI registry");
                System.out.println("->Binding to RMI registry succesfull");
            }
            catch (Exception e)
            {
                System.out.println("Could not bind to RMI registry for remote ops.");
                logger.error("Could not bind to RMI registry for remote ops.", e);

            }
        }
    */
    /**
     * Binds to rmi registry for remote operations.
     *
     */

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

/*        try
        {
            resourceBundle = ResourceBundle.getBundle("grouter");
            Enumeration proplist = resourceBundle.getKeys();
            while (proplist.hasMoreElements())
            {
                String tKey = (String) proplist.nextElement();
                System.setProperty(tKey, resourceBundle.getString(tKey));
            }
            System.out.println("->Iris properties loaded (iris.properties)");
        }
        catch (MissingResourceException e)
        {
            System.out.println("Missing resource : no property file iris.properties");
            e.printStackTrace();
            System.out.println("Iris not started - see log file");
            System.exit(0);
        }
        catch (Exception e)
        {
            System.out.println("Fatal exception");
            e.printStackTrace();
            System.out.println("Iris not started - see log file");
            System.exit(0);
        }
        */
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
        onExit();
    }

}