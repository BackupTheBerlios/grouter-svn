package org.grouter.core;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;
import org.grouter.common.config.ConfigHandler;
import org.grouter.config.NodeType;
import org.grouter.core.config.NodeConfig;
import org.grouter.core.config.NodeConigFactory;
import org.grouter.core.config.GrouterConfig;
import org.grouter.core.util.NodeThreadPoolHandler;

import java.util.*;

/**
 * Main class for GRouter.
 * <p/>
 * Reads in all config and starts up all services, archiving thread, and binds to
 * jndi in j2ee app server.
 * <p/>
 * Adds shutdown hook to vm so that we get to send an email when we close down gracefully
 * using ctrl+c (win) or kill -15 (*nix).
 *
 * @author Georges Polyzois
 */
public class GRouter implements Runnable
{
    //Logger.
    private static Logger logger = Logger.getLogger(GRouter.class);
    //private static Category logger;
    private String emailonshutdown;
    private HashMap nodeThreads = new HashMap();
    private static String CONFIGFILE = "grouter.xml";
    private ConfigHandler configHandler;
    private GrouterConfig grouterConfig;
    private static final String C_GEPO_NEWGROUTER_GROUTER_MODULES_ROUTER_CORE_SRC_CONFIG_GROUTER_FILE_FILE_XML = "C:\\gepo\\newgrouter\\grouter\\modules\\router\\core\\src\\config\\grouter-file-file.xml";
    private NodeThreadPoolHandler nodeThreadPoolHandler = new NodeThreadPoolHandler();
    private static final String GROUTER_CONFIG = "grouter.config";


    /**
     *
     * @param nodeConfigs
     */
    public GRouter(NodeConfig[] nodeConfigs)
    {

    }

    /**
     * Constructor tries to locate config file using System.getProperty("grouter.config")
     * @throws IllegalArgumentException if grouterConfig == null
     */
    public GRouter()
    {
        String grouterConfig = System.getProperty(GROUTER_CONFIG);
        if(grouterConfig == null)
        {
            throw new IllegalArgumentException("Could not get property with key :" + GROUTER_CONFIG + ". Have you " +
                    "provided a -D parameter for Java vm?");
        }
        new GRouter(grouterConfig);
    }

    /**
     * Constructor with a config path parameter.
     * @param configPath
     * @throws IllegalArgumentException if configPath == null
     */
    public GRouter(String configPath)
    {
        if(configPath == null)
        {
            throw new IllegalArgumentException("Config path was null!!  Can not read any configuration settings.");
        }
        try
        {
            logger.info("Using config path : " + configPath);
      //      logger = Logger.getLogger(GRouter.class.getName());
            this.grouterConfig = getGrouterConfig(configPath);
            nodeThreadPoolHandler.initNodeThreadScheduling(this.grouterConfig);
            //emailonshutdown = System.getProperty("grouter.emailonshutdown");
        }
        catch (Exception ex)
        {
            logger.error("Failed setup - exiting", ex);
            System.exit(0);
        }
    }

    /**
     * Load config file and store reference for further processing of config data.
     * @return GrouterConfig
     */
    private GrouterConfig getGrouterConfig(String configPath)
    {
        configHandler = new ConfigHandler(configPath, null);
        if(configHandler == null)
        {
            throw new IllegalArgumentException("Config path was invalid - could not initiate config from that location! : " + configPath);
        }
        NodeType[] nodeTypes = configHandler.getGrouterConfigDocument().getGrouterConfig().getNodeArray();
        NodeConfig[] nodeConfigs = NodeConigFactory.getNodes(nodeTypes);
        GrouterConfig grouterConfig = new GrouterConfig(nodeConfigs);
        return grouterConfig;
        //configHandler.printBootInfo();
    }

/*    private ArrayList getServices()
    {
        ArrayList arrListOfServices = null;
        try
        {
            arrListOfServices = loadSettingsFromXmlConfigFile(arrListOfServices);
        }
        catch (Exception ex)
        {
            logger.error("Failed reading system properties from xml file", ex);
        }
        return arrListOfServices;
    }
*/

    // todo fix
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

    /**
     * Binds to rmi registry for remote operations.
     *
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
     * Starts GRouter... and adds shutdown hook.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        GRouter grouter = new GRouter();
        Thread thr = new Thread(grouter);
        Runtime.getRuntime().addShutdownHook(thr);
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
            System.out.println("Iris not started - see log file");
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
     * Sends an email on shutting down iris
     */
    /*   private void sendEmail()
        {
            logger.info("Trying to send an email alert using file : " + emailonshutdown);
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
                logger.error("Could not send an email alert", ex);
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

/*    private String getArchiverCronIntervall()
    {
        String complete = ServerSystemConfigHandler.getInstance().getGlobalSettings().getArchivehandler().getCronintervall();
        return ServerSystemConfigHandler.extractCronintervall(complete);
    }
*/
}
