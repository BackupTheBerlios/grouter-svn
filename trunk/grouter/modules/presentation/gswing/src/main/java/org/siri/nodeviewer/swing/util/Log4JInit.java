package org.siri.nodeviewer.swing.util;


import org.apache.log4j.xml.DOMConfigurator;

import javax.xml.parsers.FactoryConfigurationError;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Georges Polyzois
 */
public class Log4JInit
{
    static
    {
        try
        {
            DOMConfigurator.configure(System.getProperty("log4j.configuration"));
            //org.apache.log4j.MDC.put("host", getLocalHostName());
        }
        catch (FactoryConfigurationError ex)
        {
            System.out.println("Log4j can not start properly!!! Have you added -Dlog4j.configuration=<path to your log4j.xml file>?");
            ex.printStackTrace();
        }
    }

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

}
