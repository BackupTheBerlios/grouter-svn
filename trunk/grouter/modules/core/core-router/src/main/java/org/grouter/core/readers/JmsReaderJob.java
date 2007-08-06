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

package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandMessage;
import org.grouter.domain.entities.Node;
import org.grouter.common.jms.*;
import org.grouter.common.guid.GuidGenerator;
import org.quartz.JobExecutionContext;
import org.quartz.UnableToInterruptJobException;

import javax.jms.Message;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.File;

/**
 * Connects to JMS server on execution and binds. Acknowledges on the session after reeceive
 * was called
 * Then we create commandmessages and put them on queue for further processing.
 * <p/>
 * TODO: What kind of messages are we listening to? String / Objects ?
 * Need to handle selectors in JMS?
 *
 * @author Georges Polyzois
 */
public class JmsReaderJob extends AbstractReader
{
    private static Logger logger = Logger.getLogger(JmsReaderJob.class);
    private BlockingQueue<AbstractCommand> queue;
    private static final String JMS_CONNECTIONFACTORY = "jms.connectionFactory";
    private static final String JMS_DESTINATIONNAME = "jms.destinationName";
    private static final String JMS_ISQUEUE = "jms.isQueue";
    private static final String JMS_CONTEXTFACTORY = "jndi.contextFactory";
    private static final String JMS_URLPKGPREFIXES = "jndi.urlPkgPrefixes";
    private static final int WAIT_TIME = 1000;


    /**
     * Empty - needed by Quartz framework.
     */
    public JmsReaderJob()
    {
    }


    @Override
    protected List<CommandMessage> readFromSource()
    {
        logger.info("Reading JMS messages from :" + node.getInBound().getUri());

        // a list of full paths on ftp server we will download from                                      node
        List<CommandMessage> commandMessages = new ArrayList<CommandMessage>();
        AbstractDestination client = null;
        try
        {
            client = initConnection(node);
            if (client == null)
            {
                logger.error("Could not create a client instance to JMS server");
                return null;
            }
            // if type is queue
            AbstractListenerDestination clientQ = (AbstractListenerDestination) client;
            if (clientQ.getMessageConsumer() != null)
            {
                Message msg = clientQ.getMessageConsumer().receive(WAIT_TIME);
                if (msg != null)
                {
                    File internalInFile = new File(node.getRouter().getHomePath() + File.separator + "nodes" + File.separator + node.getId() + File.separator + "internal" + File.separator + "in" + File.separator + GuidGenerator.getInstance().getGUID());

                    FileUtils.writeStringToFile(internalInFile, msg.toString());

                    String message = getMessage(internalInFile);
                    CommandMessage cmdMessage = new CommandMessage(message, internalInFile);
                    commandMessages.add(cmdMessage);
                    logger.info("Got a message ? :" + msg);
                }
            }
        }
        catch (Exception e)
        {
            logger.warn("Could not read data from destination. Probably connection problem with JMS server.", e);
        }
        finally
        {
            if (client != null)
            {
                client.unbind();
            }
        }
        return commandMessages;
    }

    private AbstractDestination initConnection(Node node) throws Exception
    {
        Map endPointContext = node.getInBound().getEndPointContext();
        String destinationName = (String) endPointContext.get(JMS_DESTINATIONNAME);
        String queueConnectionFactory = (String) endPointContext.get(JMS_CONNECTIONFACTORY);
        String contextFactory = (String) endPointContext.get(JMS_CONTEXTFACTORY);
        String urlPkgPrefixes = (String) endPointContext.get(JMS_URLPKGPREFIXES);
        String providerUrl = node.getInBound().getUri();   
        boolean isQueue = Boolean.parseBoolean((String) endPointContext.get(JMS_ISQUEUE));

        AbstractDestination destination;
        if (isQueue)
        {
            // Queue destination
            destination = new QueueListenerDestination(destinationName, queueConnectionFactory,
                    new NeverRebind(), getInitialContext(providerUrl, contextFactory, urlPkgPrefixes),
                    null, AcknowledgeMode.CLIENT_ACKNOWLEDGE);
        } else
        {
            // Topic destination
            destination = new TopicListenerDestination(destinationName, queueConnectionFactory,
                    new NeverRebind(), getInitialContext(providerUrl, contextFactory, urlPkgPrefixes),
                    null, AcknowledgeMode.CLIENT_ACKNOWLEDGE);
        }
        try
        {
            destination.bind();
        } catch (Exception e)
        {
            throw new Exception(e.getMessage(), e);
        }
        return destination;
    }

    private static InitialContext getInitialContext(String providerUrl,
                                                    String initialContextFactory,
                                                    String urlPkgPrefixes
    )
            throws NamingException
    {
        Hashtable<String,String> hashtable = new Hashtable<String,String>();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
        hashtable.put(Context.PROVIDER_URL, providerUrl);
        hashtable.put(Context.URL_PKG_PREFIXES, urlPkgPrefixes);
        return new InitialContext(hashtable);
    }


    @Override
    protected void validate(Node node)
    {
        Map endPointContext = node.getInBound().getEndPointContext();
        if (endPointContext == null)
        {
            throw new RuntimeException("Context needs to be set for this type of EndPoint.");
        }

        if (node.getInBound().getUri() == null || node.getInBound().getUri().equals(""))
        {
            throw new RuntimeException("Can not use an empty provider url to fetch data from.");
        }

        String destinationName = (String) endPointContext.get(JMS_DESTINATIONNAME);
        Validate.notNull(destinationName, "A destination name can not be empty or null");

        String connectionFactory = (String) endPointContext.get(JMS_CONNECTIONFACTORY);
        Validate.notNull(connectionFactory, "A connectionfactory name can not be empty or null");

        String contextFactory = (String) endPointContext.get(JMS_CONTEXTFACTORY);
        Validate.notNull(contextFactory, "A contextfactory name can not be empty or null");

        //String urlPkgPrefixes = (String) endPointContext.get(JMS_URLPKGPREFIXES);
        //Validate.notNull( contextFactory, "A contextfactory name can not be empty or null" );

        String isQueue = (String) endPointContext.get(JMS_ISQUEUE);
        Validate.notNull(isQueue, "A destination must be either q Queue or a Topic.");

    }


    @Override
    void pushToIntMemoryQueue()
    {
        logger.debug("Putting cmd on queue " + command.toStringUsingReflection());
        queue.offer(command);
    }


    /**
     * Called to init every time Quartz innvokes execute.
     *
     * @param node          the node
     * @param blockingQueue the blocking queue
     */
    void init(final Node node, BlockingQueue<AbstractCommand> blockingQueue)
    {
        if (node == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.node = node;
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(node);

    }

    public void execute(JobExecutionContext jobExecutionContext)
    {
        setJobExecutionContext(jobExecutionContext);
        execute();
    }


    public void setQueue(BlockingQueue<AbstractCommand> queue)
    {
        this.queue = queue;
    }

    public void interrupt() throws UnableToInterruptJobException
    {
        logger.info(node.getId() + " got request to stop");
    }


}