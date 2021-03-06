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

package org.grouter.core.util;

import org.grouter.common.jms.*;
import org.grouter.core.command.CommandMessage;
//import org.grouter.domain.service.jms.GRouterPublishEventDTO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.HashMap;
                                 
/**
 * Thread send commandMessages from internal queues to a JMS destination for storing in db and
 * pulbication to other potential clients with a registered interest in these events.
 *
 * @author Georges Polyzois
 */
public class JMSDestinationSenderThread implements Runnable
{
    private static Logger logger = Logger.getLogger(JMSDestinationSenderThread.class);
    private static RebindBehavior rebindBehavior = new EternalRebind();
    private static AbstractDestination queueDestination;
    private static final int QUEUE_SIZE = 2000;
    private static final int TIME_TO_LIVE = 10000;
    private static BlockingQueue<CommandMessage[]> queues = new ArrayBlockingQueue<CommandMessage[]>(QUEUE_SIZE);
//    private GrouterConfig grouterConfig;

    /**
     * Constructor.
     *
     * @param grouterConfig not null
     * @throws IllegalArgumentException if grouterConfig == null
     */
    /*
    public JMSDestinationSenderThread(GrouterConfig grouterConfig)
    {
        if (grouterConfig == null || grouterConfig.getGlobalSettingsConfig().getGrouterDomainConfig() == null)
        {
            throw new IllegalArgumentException("Must provide grouter domain configuration to be able to contact" +
                    "central appliction server for JMS messaging");
        }

        this.grouterConfig = grouterConfig;
        try
        {
            queueDestination = new QueueDestination(grouterConfig.getGlobalSettingsConfig().getGrouterDomainConfig().getDestinationJndiName(), true, null,
                    rebindBehavior, getInitialContext(grouterConfig.getGlobalSettingsConfig().getGrouterDomainConfig()), TIME_TO_LIVE, null, AcknowledgeMode.AUTO_ACKNOWLEDGE);
            queueDestination.bind();
        } catch (NamingException e)
        {
            logger.error("Failed connecting to queues", e);
        } finally
        {
        }
    } */

    /**
     * Use grouter config create intialcontext for Destination.
     *
     * @param grouterDomainConfig
     * @return
     * @throws NamingException
     */
    /*
    private Context getInitialContext(GrouterDomainConfig grouterDomainConfig)
            throws NamingException
    {
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, grouterDomainConfig.getJavaNamingFactoryInitial());
        hashtable.put(Context.PROVIDER_URL, grouterDomainConfig.getJavaNamingProviderUrl());
        hashtable.put(Context.URL_PKG_PREFIXES, grouterDomainConfig.getJavaNamingUrlPkgs());
        return new InitialContext(hashtable);
    }
                            */

    /**
     * Poll queueu and send to jms destination.
     */
    public void run()
    {
        logger.info("Sending a batch of commandMessages to jms destination queues");
        CommandMessage[] commandMessages = queues.poll();
        if (commandMessages == null)
        {
            logger.info("No message");
            return;
        }
        // This will enable client to use JMS selectors based on grouterid
        HashMap<String,String> map = new HashMap();
//        map.put("grouterid", grouterConfig.getDisplayName());
//        queueDestination.sendMessage(createGRouterPublishEventDTO(commandMessages), map);
        logger.info("Batch of commandMessages sent!!!");
    }

    /**
     * Build the DTO.
     * 
     * @param commandMessages
     * @return
     */
/*    private GRouterPublishEventDTO createGRouterPublishEventDTO(CommandMessage[] commandMessages)
    {
        NodeConfig[] nodeConfig = grouterConfig.getNodes();
        Message[] messages = new Message[nodeConfig.length];
        int i= 0;
        for (CommandMessage commandMessage : commandMessages)
        {
            Message message = new Message(commandMessage.getMessage());
            messages[i] = message;
        }
        GRouterPublishEventDTO eventDTO = new GRouterPublishEventDTO(grouterConfig.getDisplayName(), nodeConfig[0].getId(), messages);
        return eventDTO;
    }
*/
    /**
     * Use to get queues handle.
     *
     * @return
     */
    public static BlockingQueue<CommandMessage[]> getQueue()
    {
        return queues;
    }
}
