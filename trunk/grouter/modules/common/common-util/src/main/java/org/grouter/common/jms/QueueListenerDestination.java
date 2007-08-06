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

package org.grouter.common.jms;


import static org.grouter.common.jndi.ServiceLocatorContextAware.getInstance;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.naming.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.*;
import org.grouter.common.jndi.ServiceLocatorException;
import org.grouter.common.exception.RemoteGrouterException;

/**
 * See {@link AbstractDestination} and use abstract interface to concrete
 * implementations.
 *
 * @author Georges Polyzois
 */
public class QueueListenerDestination extends AbstractListenerDestination
{
    //The logger.
    private static Logger logger = Logger.getLogger(QueueListenerDestination.class);
    // Connection to the Queue.
    private QueueConnection queueConnection;
    // The actual Queue or message channel.
    private Queue queue;
    //Sender to the Queue.
    protected QueueSender queueSender;
    // Session to the Queue.
    protected QueueSession queueSession;
    /**
     * Used for request / reply on the same session.
     */
    private TemporaryQueue temporaryQueue;

    /**
     * Constructor for use of a transactional (commit/rollback) way of handling
     * acknowledge of messages (code needs to use session and explicitly do a commit
     * to send a message).
     * E.g.
     * <pre>
     * InitialContext iniCtx = JMSUtils.getJbossInitialContext();
     * Destination queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, true, "ConnectionFactory",   null, iniCtx, 4000, null);
     * queueDestination.bind();
     * queueDestination.sendMessage(text);
     * try
     * {
     * queueDestination.getQueueSession().commit();
     * } catch (JMSException e)
     * {...
     * <p/>
     * <(pre>
     * <p/>
     * <p/>
     * See other constructor if you are using acknowledge mode based messaging.
     *
     * @param queueName              the JNDI name of the destination queue.
     * @param queueConnectionFactory the JNDI name of the Queue connection factory to use,
     *                               if null provided then the default factory will be used.
     * @param rebindBehavior         a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination {@link RebindBehavior}. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               (@see javax.naming.Context).
     * @param listener               a message listener implementing @see javax.jms.MessageListener  -
     *                               i.e. a class that will be receiving callbacks from jms provider. If null specified
     *                               then you will have to implement a receive behaviour in your consuming thread.
     */
    public QueueListenerDestination(String queueName,
                                    String queueConnectionFactory,
                                    RebindBehavior rebindBehavior,
                                    Context context,
                                    MessageListener listener
    )
    {
        init(queueName, true, AcknowledgeMode.NONE, queueConnectionFactory, rebindBehavior, listener, context, false);
    }

    /**
     * Constructor for use with an acknowledge mode specified. The JMS message provider
     * will take care of acknowledging messages based on mode set, can be overridden by
     * sendmessage implementations.
     * <p/>
     * E.g. for  sender implementation
     * <pre>
     * InitialContext iniCtx = JMSUtils.getJbossInitialContext();
     * queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, true, "ConnectionFactory",   null, iniCtx, 4000, null, AcknowledgeMode.NONE);
     * queueDestination.bind();
     * queueDestination.sendMessage("A message");
     * logger.info("Message sent");
     * </pre>
     *
     * @param queueName              the JNDI name of the destination queue.
     * @param queueConnectionFactory the JNDI name of the Queue connection factory to use,
     *                               if null provided then the default factory will be used specified in
     *                               {@link AbstractDestination}
     * @param theRebindBehavior      a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination @link RebindBehavior. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               {@link javax.naming.Context}. If null provided then the default will be used -
     *                               depends on your environemnt (jndi.properties, -Djava..., or InitialContext(new Hashtable())).
     * @param listener               a message listener implementing {@link javax.jms.MessageListener}   -
     *                               i.e. a class that will be receiving callbacks from jms provider. If null specified
     *                               then you will have to implement a receive behaviour in your consuming thread.
     * @param ackmode                this maps direclty to {@link javax.jms.Session} and the different types of
     *                               acknowledge modes existing there.
     */
    public QueueListenerDestination(String queueName,
                                    final String queueConnectionFactory,
                                    RebindBehavior theRebindBehavior,
                                    Context context,
                                    MessageListener listener,
                                    AcknowledgeMode ackmode
    )
    {
        init(queueName, false, ackmode, queueConnectionFactory, theRebindBehavior, listener, context, false);
    }


    /**
     * Use this if you are implementing a use case with a synchronous request repsonse scenario where you
     * use a temporary queue to receive a response.
     * <p/>
     * Constructor for use with an acknowledge mode specified. The JMS message provider
     * will take care of acknowledging messages based on mode set, can be overridden by
     * sendmessage implementations.
     * E.g. for  sender implementation with temporary queue
     * <pre>
     * InitialContext iniCtx = JMSUtils.getJbossInitialContext();
     * queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, true, "ConnectionFactory",   null, iniCtx, 4000, null, AcknowledgeMode.NONE, true);
     * queueDestination.bind();
     * queueDestination.sendMessage("A message");
     * logger.info("Message sent");
     * </pre>
     *
     * @param queueName              the JNDI name of the destination queue.
     * @param queueConnectionFactory the JNDI name of the Queue connection factory to use,
     *                               if null provided then the default factory will be used specified in
     *                               {@link AbstractDestination}
     * @param theRebindBehavior      a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination {@link RebindBehavior}. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               {@link javax.naming.Context}. If null provided then the default will be used -
     *                               depends on your environemnt (jndi.properties, -Djava..., or InitialContext(new Hashtable())).
     * @param listener               a message listener implementing {@link javax.jms.MessageListener}   -
     *                               i.e. a class that will be receiving callbacks from jms provider. If null specified
     *                               then you will have to implement a receive behaviour in your consuming thread.
     * @param ackmode                this maps direclty to {@link javax.jms.Session} and the different types of
     *                               acknowledge modes existing there.
     * @param useTemporaryQueue      will create a temporary queue for this session
     */
    public QueueListenerDestination(String queueName,
                                    final String queueConnectionFactory,
                                    RebindBehavior theRebindBehavior,
                                    Context context,
                                    MessageListener listener,
                                    AcknowledgeMode ackmode,
                                    boolean useTemporaryQueue
    )
    {
        init(queueName, false, ackmode, queueConnectionFactory, theRebindBehavior, listener, context, useTemporaryQueue);
    }


    /**
     * Initializer used by constructors.
     *
     * @param queueName              String
     * @param isTransactional        boolean
     * @param ackmode                AcknowledgeMode
     * @param queueConnectionFactory String
     * @param theRebindBehavior      RebindBehavior
     * @param listener               a listener
     * @param theContext             if null create new, else use given one.
     * @param useTemporaryQueue      to use a temporary queue for reply
     */
    private void init(String queueName,
                      boolean isTransactional,
                      AcknowledgeMode ackmode,
                      String queueConnectionFactory,
                      RebindBehavior theRebindBehavior,
                      MessageListener listener,
                      Context theContext,
                      boolean useTemporaryQueue
    )
    {
        int mappedacknowledgeMode = getAcknowledgeMode(ackmode);
        if (theContext == null)
        {
            try
            {
                context = new InitialContext();
            } catch (NamingException ex)
            {
                logger.error("Failed creating default InitialContext.", ex);
                return;
            }
        } else
        {
            context = theContext;
        }
        useTemporaryReplyDestination = useTemporaryQueue;
        printJNDI(theContext, logger);
        destinationName = queueName;
        this.acknowledgeMode = mappedacknowledgeMode;
        this.isTransactional = isTransactional;
        if (queueConnectionFactory != null)
        {
            logger.debug("Using non default connection factory:  " + queueConnectionFactory);
            connectionFactory = queueConnectionFactory;
        }
        // Set default rebind behavior - can be changed dynamically using setter
        if (theRebindBehavior != null)
        {
            rebindBehavior = theRebindBehavior;
        }
        // Register an exceptionlistener
        exceptionListener = new SystemJMSExceptionListenerHandler(this);
        // Set the user defined listener
        this.listener = listener;
    }


    /**
     * Disconnect from queue. This method should be called from the ejbRemove method
     * if you are using a stateless session bean.
     */
    public void unbind()
    {
        if (queueConnection == null)
        {
            logger.error("Connection to destination queue was null - " +
                    "can not close null connection, returning.");
        } else
        {
            try
            {
                queueConnection.setExceptionListener(null);
                queueConnection.stop();
                if (temporaryQueue != null)
                {
                    temporaryQueue.delete();
                }
            } catch (JMSException e)
            {
                logger.error("Could not stop connection to destination.");
            } finally
            {
                try
                {
                    if (queueConnection != null)
                    {
                        queueConnection.close();
                    }
                } catch (JMSException e1)
                {
                    logger.error("Could not close queue connection!");
                }
            }
            queueConnection = null;
            temporaryQueue = null;
        }
    }


    /**
     * Retrieves the session for this destination. This method can be used if you
     * are handling trasacted sessions and need to explicitly get hold of the session
     * to do a commit or rollback.
     *
     * @return QueueSession
     */
    public QueueSession getQueueSession()
    {
        return queueSession;
    }

    /**
     * The registered message listener handling callback through implemented interface
     *
     * @return MessageListener
     * @see javax.jms.MessageListener .
     */
    public MessageListener getListener()
    {
        return listener;
    }

    /**
     * Connect to queue  and open a session.
     */
    @Override
    public void bind()
    {
        try
        {
            // Find ConnectionFactory
            final QueueConnectionFactory queueConnectionFactory = getInstance().getQueueConnectionFactory(connectionFactory, context);
            // Get queue
            queue = getInstance().getQueue(destinationName, context);
            // Create conneciton to queue
            queueConnection = queueConnectionFactory.createQueueConnection();
            // Register an exceptionlistener
            queueConnection.setExceptionListener(exceptionListener);
            queueSession = queueConnection.createQueueSession(isTransactional, acknowledgeMode);

            messageConsumer = queueSession.createReceiver(queue);

            // Sets the receiver which onMessage method will be called.
            messageConsumer.setMessageListener(listener);


            queueConnection.start();
            logger.info("Bound to destination " + destinationName);
        } catch (JMSException e)
        {
            logger.error("Got exception with JMS provider during bind to destination " + destinationName + ". Error code : " + e.getErrorCode());
            rebind(this);
        } catch (ServiceLocatorException ex)
        {
            logger.error(
                    "Got exception with JMS provider during bind to destination " +
                            destinationName + ". Got error message : " + ex.getMessage());
            rebind(this);
        }
    }


    /**
     */
    public void rebind(AbstractDestination dest) throws RemoteGrouterException
    {
        rebindBehavior.rebind(this);
    }


    public synchronized void setMessageListenerOnConsumer()
    {
        if (messageConsumer == null)
        {
            try
            {
                logger.debug("Creating new consumer and adding Messagelistener.");
                messageConsumer = queueSession.createReceiver(queue);
                messageConsumer.setMessageListener(listener);
            } catch (JMSException ex)
            {
                logger.error("Failed closing messageconsumer on removeReceiver call", ex);
            }
        }
    }


    /**
     * <br>
     */
    public void sendReplyToTemporaryDestination(Message request)
    {
        TemporaryQueue replyQueue = null;
        QueueSender tempsender = null;
        String temporaryDestinationName = null;
        try
        {
            if (request.getJMSReplyTo() == null)
            {
                throw new IllegalStateException(
                        "The sender of this message has not entered a JMSReplyTo field - " +
                                "impossible to send reply on temporary destination!!");
            }
            temporaryDestinationName = request.getJMSReplyTo().toString();
            request.setJMSCorrelationID(temporaryDestinationName);
            replyQueue = (TemporaryQueue) request.getJMSReplyTo();
            tempsender = queueSession.createSender(replyQueue);
            tempsender.send(request);
            logger.debug("Created a tempsender and sent reply to " + replyQueue);
        }
        catch (JMSException ex)
        {
            //ignore
            logger.warn("Failed sending reply on temporary destination : " + temporaryDestinationName);
        }
        finally
        {
            try
            {
                if (tempsender != null)
                {
                    tempsender.close();
                }
                if (replyQueue != null)
                {
                    replyQueue.delete();
                }
            } catch (JMSException ex1)
            {
                //ignore
            }
        }
    }

    /**
     * Reflection to string - uses org.apache.commons.lang.builder.ToStringBuilder.
     *
     * @return String
     * @see org.apache.commons.lang.builder.ToStringBuilder#reflectionToString
     */
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}