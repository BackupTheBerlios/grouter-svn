package org.grouter.common.logging;

import org.apache.log4j.xml.DOMConfigurator;

import javax.xml.parsers.FactoryConfigurationError;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.URL;

/**
 * Initializer for log4j.
 *
 * @author Georges Polyzois
 */
public class Log4JInit
{
    private static final String LOG4J_CONFIGURATION = "log4j.configuration";

    static
    {
        initLog4J();
    }

    public static void reInitLog4J()
    {
        initLog4J();
    }


    private static void initLog4J()
    {
        try
        {
            String log4jconf = System.getProperty(LOG4J_CONFIGURATION);
            if (log4jconf != null && !log4jconf.equalsIgnoreCase(""))
            {
                System.out.println("log4j.configuration was set to " + log4jconf);
                DOMConfigurator.configure(log4jconf);
            } else
            {
                System.out.println(LOG4J_CONFIGURATION + "was null or empty - trying to load log4j.xml");
                URL url = Log4JInit.class.getResource("log4j.xml");
                if (url != null)
                {
                    DOMConfigurator.configure(url);
                } else
                {
                    System.out.println("Log4j can not start properly!!! Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
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

    /*
    private static String getLocalHostName()
    {
        String hostName = null;
        try
        {
            hostName = InetAddress.getLocalHost().getHostName();
            hostName = (hostName.indexOf(".") == -1) ? hostName : hostName.substring(0, hostName.indexOf("."));
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return hostName;
    }
    */
}
