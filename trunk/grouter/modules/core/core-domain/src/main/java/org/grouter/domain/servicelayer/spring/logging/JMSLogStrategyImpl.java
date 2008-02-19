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

package org.grouter.domain.servicelayer.spring.logging;

import org.apache.log4j.Logger;
import org.grouter.common.jms.AbstractSenderDestination;
import org.grouter.common.jms.AcknowledgeMode;
import org.grouter.common.jms.NeverRebind;
import org.grouter.common.jms.QueueSenderDestination;
import org.grouter.domain.RouterCache;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.SettingsContext;
import org.grouter.domain.servicelayer.RouterService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Map;


/**
 * @author Georges Polyzois
 */
public class JMSLogStrategyImpl implements LogStrategy
{
    private static Logger logger = Logger.getLogger(JMSLogStrategyImpl.class);
    RouterService routerService;


    //    private final static String LOG_Q_NAME = "grouter/logq";
    private AbstractSenderDestination queueDestination;
    private static InitialContext initialContext;

    {

        //      queueDestination = new QueueSenderDestination(LOG_Q_NAME, true, "ConnectionFactory",   null, iniCtx, 4000, null, AcknowledgeMode.NONE);
        // queueDestination.bind();
        // queueDestination.sendMessage("A message");

        //     logQDestination = new QueueSenderDestination( LOG_Q_NAME, AbstractDestination.JBOSSCONNECTIONFACTORY, new EternalRebind(), );
    }

    public void log(Message message)
    {
        try
        {
            // The routercache is initialized when the router starts up
            Map<String, String> settingsContext = RouterCache.getSettingsContextCache().getSettings().getSettingsContext();
            queueDestination = new QueueSenderDestination(settingsContext.get(SettingsContext.KEY_SETTINGS_LOGGING_JMSLOGGINGQUEUE)
                    , settingsContext.get(SettingsContext.KEY_SETTINGS_JNDI_QUEUECONNECTIONFACTORY), null,
                    getInitialContext(settingsContext), 4000, AcknowledgeMode.NONE);
            queueDestination.bind();
            queueDestination.sendMessage("A message");

        } catch (NamingException e)
        {
            logger.error("Failed to send a log message on JMS to log queue", e);
        }
    }


    public void log(Node node)
    {
        try
        {
            // The routercache is initialized when the router starts up
            Map<String, String> settingsContext = RouterCache.getSettingsContextCache().getSettings().getSettingsContext();

            queueDestination = new QueueSenderDestination(settingsContext.get(SettingsContext.KEY_SETTINGS_LOGGING_JMSLOGGINGQUEUE),
                    settingsContext.get(SettingsContext.KEY_SETTINGS_JNDI_QUEUECONNECTIONFACTORY), new NeverRebind(),
                    getInitialContext(settingsContext), 4000, AcknowledgeMode.NONE);

            queueDestination.bind();
            queueDestination.sendMessage("A message");

        } catch (NamingException e)
        {
            logger.error("Failed to send a log message on JMS to log queue", e);
        }
    }


    /**
     * Get context for remote app server.
     *
     * @return initialcontext
     * @throws javax.naming.NamingException see {@link javax.naming.NamingException}
     */
    @SuppressWarnings({"UnnecessaryLocalVariable", "SameParameterValue"})
    public static InitialContext getInitialContext(Map map)
            throws NamingException
    {
        if (initialContext == null)
        {
            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put(Context.INITIAL_CONTEXT_FACTORY, (String) map.get(SettingsContext.KEY_SETTINGS_JNDI_JAVA_NAMING_FACTORY_INITIAL));
            hashtable.put(Context.PROVIDER_URL, (String) map.get(SettingsContext.KEY_SETTINGS_JNDI_JAVA_NAMING_PROVIDER_URL));
            hashtable.put(Context.URL_PKG_PREFIXES, (String) map.get(SettingsContext.KEY_SETTINGS_JNDI_JAVA_NAMING_FACTORY_URL));

            initialContext = new InitialContext(hashtable);
        }
        return initialContext;
    }


}
