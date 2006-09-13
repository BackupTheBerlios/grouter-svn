package org.grouter.common.logging;

import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Document;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;

/**
 * Initializer for log4j.
 *
 * @author Georges Polyzois
 */
public class Log4JInit
{
    private static final String LOG4J_CONFIGURATION = "log4j.configuration";
    private static final String LOG4J_CONFIGURATION_FILE = "log4j.xml";

    /**
     * Prior to this call make sure that you have -Dlog4j.configuration set to
     * the log4j config file to be used.
     */
    public static void initLog4J()
    {
        try
        {
            String log4jconf = System.getProperty(LOG4J_CONFIGURATION);
            if (log4jconf != null && !log4jconf.equalsIgnoreCase(""))
            {
                System.out.println("Log4JInit - property -Dlog4j.configuration was set to " + log4jconf);
                DOMConfigurator.configure(log4jconf);
            } else
            {
                System.out.println("Log4JInit - " + LOG4J_CONFIGURATION + "was null or empty - trying to load log4j.xml");
                ClassLoader classLoader = Log4JInit.class.getClassLoader();
                URL url = classLoader.getResource(LOG4J_CONFIGURATION_FILE);
                if (url != null)
                {
                    DOMConfigurator.configure(url);
                } else
                {
                    System.out.println("Url was null. Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
                }
            }
            //org.apache.log4j.MDC.put("host", getLocalHostName());
        }
        catch (FactoryConfigurationError ex)
        {
            System.out.println("Caught FactoryConfigurationError. Log4j can not start properly!!! Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
            ex.printStackTrace();
        }
        catch (Throwable thr)
        {
            System.out.println("Caught Throwable. Log4j can not start properly!!! Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
            thr.printStackTrace();
        }
    }

}
