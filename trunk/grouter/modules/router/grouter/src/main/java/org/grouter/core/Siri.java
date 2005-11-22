package org.grouter.core;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;

import java.util.*;

/**
 * Main class for Siri. 
 *
 * Reads in all config and starts up all services, archiving thread, and binds to
 * jndi in j2ee app server.
 *
 * Adds shutdown hook to vm so that we get to send an email when we close down gracefully
 * using ctrl+c (win) or kill -15 (*nix). 
 * 
 * @author Georges Polyzois
 * @version
 *
 */
public class Siri implements Runnable
{
    static Category myLogger;
    private String emailonshutdown;
    HashMap myServiceThreads = new HashMap();
  //  IrisOperationsImpl irisOperations;

    public Siri()
    {
        try
        {
            myLogger = Logger.getLogger(Siri.class.getName());
            setUp();
            emailonshutdown = System.getProperty("iris.emailonshutdown");
        }
        catch (Exception ex)
        {
            myLogger.error("Failed setup - exiting", ex);
            System.exit(0);
        }
    }

    protected void setUp()
    {
    /*	FileReader talker = new FileReader(null);
         Timer timer = new Timer();
         //timer.scheduleAtFixedRate(talker,1000,4000);

         QueueListener listener = new QueueListener();
         Timer timer2 = new Timer();
         //timer2.scheduleAtFixedRate(listener,1000,2000);
     */
        System.out.println(">> See log files or start iris gui");
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
            myLogger.error("Failed reading system properties from xml file", ex);
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
                        myLogger.info("No resending enabled for service  : " + arrService.getId());
                        NDC.pop();
                        return;
                    }
                    else if (curFiles == null || curFiles.length == 0)
                    {
                        myLogger.info("Error resend is on for service but no files to resend for service at this moment : " + arrService.getId());
                        NDC.pop();
                        return;
                    }
                    else if (curFiles != null && curFiles.length == 1 && curFiles[0].isDirectory())
                    {
                        myLogger.info("Error resend is on for service but there are only folders in error folder for service (only subfolder exist) : " + arrService.getId());
                        NDC.pop();
                        return;
                    }
                    else
                    {
                        myLogger.info(curFiles.length + " number of files found for resending for service  : " + arrService.getId());

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
            myLogger.info("Starting core-server service for remote operations. Binding " + System.getProperty("iris.rmi.service.name"));
            irisOperations = new IrisOperationsImpl(myServiceThreads);
            Naming.rebind(System.getProperty("iris.rmi.service.name"), irisOperations);
            myLogger.info("Started core-server service for remote operations is now bound to RMI registry");
            System.out.println("->Binding to RMI registry succesfull");
        }
        catch (Exception e)
        {
            System.out.println("Could not bind to RMI registry for remote ops.");
            myLogger.error("Could not bind to RMI registry for remote ops.", e);

        }
    }
*/
    /**
     * Starts Iris... and adds shutdown hook.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        Siri iris = new Siri();
        Thread thr = new Thread(iris);
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

        try
        {
            resourceBundle = ResourceBundle.getBundle("iris");
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
    }

    /**
     * Called by vm when when we get a controlled exit.
     */
    public void onExit()
    {
        myLogger.debug("Exiting Iris...");
        NDC.remove();
//        sendEmail();
    }

    /**
     * Sends an email on shutting down iris
     */
 /*   private void sendEmail()
    {
        myLogger.info("Trying to send an email alert using file : " + emailonshutdown);
        String email = null;
        try
        {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            Document document;
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource source = new InputSource(new FileReader(emailonshutdown));
            document = builder.parse(source);
            Node firstchild = document.getFirstChild();
            NamedNodeMap attributes = firstchild.getAttributes();
            Node message = attributes.getNamedItem("messagetype");
            InstantEmailMessageHandler.getInstance().handleMessage(document, null, null);
        }
        catch (Exception ex)
        {
            myLogger.error("Could not send an email alert", ex);
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
