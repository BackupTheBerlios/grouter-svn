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
package org.grouter.common.logging;

import org.apache.log4j.xml.DOMConfigurator;

import javax.xml.parsers.FactoryConfigurationError;
import java.net.URL;

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
