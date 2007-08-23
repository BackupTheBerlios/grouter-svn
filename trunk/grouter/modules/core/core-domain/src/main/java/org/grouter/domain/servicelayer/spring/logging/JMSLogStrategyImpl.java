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

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.SettingsContext;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.common.jms.*;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import java.util.Map;
import java.util.Hashtable;


/**
 * @author Georges Polyzois
 */
public class JMSLogStrategyImpl implements LogStrategy
{
    private static Logger logger = Logger.getLogger(JMSLogStrategyImpl.class);
    RouterService routerService;

    private final static String LOG_Q_NAME = "grouter/logq";
    private AbstractSenderDestination queueDestination;
    private static InitialContext initialContext;

    {

        //      queueDestination = new QueueSenderDestination(LOG_Q_NAME, true, "ConnectionFactory",   null, iniCtx, 4000, null, AcknowledgeMode.NONE);
        // queueDestination.bind();
        // queueDestination.sendMessage("A message");

        //     logQDestination = new QueueSenderDestination( LOG_Q_NAME, AbstractDestination.JBOSSCONNECTIONFACTORY, new EternalRebind(), );
    }

    @Override
    public void log(Message message)
    {
        queueDestination.sendMessage(message);
    }

    public void log(Message message, Map<String, String> settingsContext)
    {

        try
        {
            queueDestination = new QueueSenderDestination(LOG_Q_NAME, true, "ConnectionFactory", null, getInitialContext( settingsContext ), 4000, null, AcknowledgeMode.NONE);
            queueDestination.bind();
            queueDestination.sendMessage("A message");

        } catch (NamingException e)
        {
            logger.error("Failed to send a log message on JMS to log queue", e);
        }
    }

    @Override
    public void log(Node node)
    {
        queueDestination.sendMessage(node);
    }

    public void log(Node node, Map<String, String> settingsContext)
    {
        try
        {
            queueDestination = new QueueSenderDestination(LOG_Q_NAME, true, "ConnectionFactory", null, getInitialContext( settingsContext ), 4000, null, AcknowledgeMode.NONE);
            queueDestination.bind();
            queueDestination.sendMessage("A message");

        } catch (NamingException e)
        {
            logger.error("Failed to send a log message on JMS to log queue", e);
        }
    }

    public void log(Node node, SettingsContext settingsContext)
    {
        //To change body of implemented methods use File | Settings | File Templates.
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
