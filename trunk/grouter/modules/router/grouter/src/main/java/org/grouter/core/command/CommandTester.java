package org.grouter.core.command;

import org.apache.log4j.xml.DOMConfigurator;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Georges.Polyzois
 * Date: 2005-apr-01
 * Time: 17:02:29
 * To change this template use File | Settings | File Templates.
 */
public class CommandTester
{
    static
    {
        ResourceBundle resourceBundle = null;
        try
        {
            String log4j = System.getProperty("log4j.configuration");
            DOMConfigurator.configure(log4j);
            System.out.println("->Logging configured (log4j.properties)");
        }
        catch (MissingResourceException e)
        {
            System.out.println("Missing resource : log4j.properties");
            System.out.println("Iris not started - see log file");
            System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        /*
        FileReader talker = new FileReader();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(talker,1000,4000);

        QueueListener listener = new QueueListener();
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(listener,1000,2000);
        */

    }
}
