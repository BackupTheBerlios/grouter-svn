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


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.grouter.common.exception.RemoteGrouterException;
import org.grouter.common.jndi.JNDIUtils;
import static org.grouter.common.jndi.ServiceLocatorContextAware.getInstance;
import org.grouter.common.jndi.ServiceLocatorException;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.lang.IllegalStateException;
import java.util.HashMap;

/**
 * See {@link org.grouter.common.jms.AbstractDestination} and use abstract interface to concrete
 * implementations.
 *
 * @author Georges Polyzois
 */
public class QueueSenderDestination extends AbstractSenderDestination
{
    //The logger.
    private static Logger logger = Logger.getLogger(QueueSenderDestination.class);
    // The ConnectionFactory used to connect to the Queue.
    QueueConnectionFactory queueConnectionFactory;
    // Connection to the Queue.
    protected QueueConnection queueConnection;
    // The actual Queue or message channel.
    protected Queue queue;
    //Sender to the Queue.
    protected javax.jms.QueueSender queueSender;
    // Session to the Queue.
    protected QueueSession queueSession;
    /**
     * Used for request / reply on the same session.
     */
    private TemporaryQueue temporaryQueue;

    /**
     * Constructor for use of a transactional (commit/rollback) way of handling
     * acknowledge of messages (code needs to use session and explicitly do a commit
     * to send a message). Doing a send using this strategy will not send anything -
     * a commit MUST be set.
     * <p/>
     * Acknowledgemode will be AcknowledgeMode.NONE.
     * <p/>
     * E.g.
     * <pre>
     * <p/>
     * InitialContext iniCtx = JMSUtils.getJbossInitialContext();
     * Destination queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, true, "ConnectionFactory",null, iniCtx, 4000, null);
     * queueDestination.bind();
     * queueDestination.sendMessage(text);
     * try
     * {
     *    queueDestination.getQueueSession().commit();
     * } catch (JMSException e)
     * {  ...
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
     *                               to rebind to this destination {@link org.grouter.common.jms.RebindBehavior}. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               (@see javax.naming.Context).
     * @param timeToLiveMs           set to 0 if no time to live should be uses - TTL is specified
     *                               in millisecons!!!
     */
    @SuppressWarnings({"SameParameterValue", "SameParameterValue", "SameParameterValue"})
    public QueueSenderDestination(final String queueName,
                                  final String queueConnectionFactory,
                                  RebindBehavior rebindBehavior,
                                  final Context context,
                                  final long timeToLiveMs)
    {
        init(queueName, true, AcknowledgeMode.NONE, queueConnectionFactory, rebindBehavior, timeToLiveMs, context, false);
    }

    /**
     * Constructor for use with an acknowledge mode specified. The JMS message provider
     * will take care of acknowledging messages based on mode set, can be overridden by
     * sendmessage implementations.
     * E.g. for  sender implementation
     * <p/>
     * <pre>
     *    InitialContext iniCtx = JMSUtils.getJbossInitialContext();
     *    queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, true, "ConnectionFactory",   null, iniCtx, 4000, null, AcknowledgeMode.NONE);
     *    queueDestination.bind();
     *    queueDestination.sendMessage("A message");
     *    logger.info("Message sent");
     * </pre>
     *
     * @param queueName              the JNDI name of the destination queue.
     * @param queueConnectionFactory the JNDI name of the Queue connection factory to use,
     *                               if null provided then the default factory will be used specified in
     *                               {@link org.grouter.common.jms.AbstractDestination}
     * @param theRebindBehavior      a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination @link RebindBehavior. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               {@link javax.naming.Context}. If null provided then the default will be used -
     *                               depends on your environemnt (jndi.properties, -Djava..., or InitialContext(new Hashtable())).
     * @param timeToLiveMs           set to 0 if no time to live should be used - TTL is specified
     *                               in millisecons!!!
     * @param ackmode                this maps direclty to {@link javax.jms.Session} and the different types of
     *                               acknowledge modes existing there.
     */
    public QueueSenderDestination(final String queueName,
                                  final String queueConnectionFactory,
                                  RebindBehavior theRebindBehavior,
                                  final Context context,
                                  final long timeToLiveMs,
                                  final AcknowledgeMode ackmode)
    {
        init(queueName, false, ackmode, queueConnectionFactory, theRebindBehavior, timeToLiveMs, context, false);
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
     *                               {@link org.grouter.common.jms.AbstractDestination}
     * @param theRebindBehavior      a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination {@link org.grouter.common.jms.RebindBehavior}. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               {@link javax.naming.Context}. If null provided then the default will be used -
     *                               depends on your environemnt (jndi.properties, -Djava..., or InitialContext(new Hashtable())).
     * @param timeToLiveMs           set to 0 if no time to live should be used - TTL is specified
     *                               in millisecons!!!
     * @param ackmode                this maps direclty to {@link javax.jms.Session} and the different types of
     *                               acknowledge modes existing there.
     * @param useTemporaryQueue      will create a temporary queue for this session
     */
    @SuppressWarnings({"SameParameterValue", "SameParameterValue", "SameParameterValue", "SameParameterValue", "SameParameterValue"})
    public QueueSenderDestination(final String queueName,
                                  final String queueConnectionFactory,
                                  final RebindBehavior theRebindBehavior,
                                  final Context context,
                                  final long timeToLiveMs,
                                  final AcknowledgeMode ackmode,
                                  final boolean useTemporaryQueue)
    {
        init(queueName, false, ackmode, queueConnectionFactory, theRebindBehavior, timeToLiveMs, context, useTemporaryQueue);
    }


    /**
     * Initializer used by constructors.
     *
     * @param queueName              String
     * @param isTransactional        boolean
     * @param ackmode                AcknowledgeMode
     * @param queueConnectionFactory String
     * @param theRebindBehavior      RebindBehavior
     * @param timeToLiveMs           how long we should keep message.
     * @param theContext             if null create new, else use given one.
     * @param useTemporaryQueue      to use a temporary queue for reply
     */
    private void init(String queueName,
                      boolean isTransactional,
                      AcknowledgeMode ackmode,
                      String queueConnectionFactory,
                      RebindBehavior theRebindBehavior,
                      long timeToLiveMs,
                      Context theContext,
                      boolean useTemporaryQueue)
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
                //can not do anything without a context
                return;
            }
        } else
        {
            context = theContext;
        }
        useTemporaryReplyDestination = useTemporaryQueue;
        printJNDI(context, logger);
        destinationName = queueName;
        this.acknowledgeMode = mappedacknowledgeMode;
        this.isTransactional = isTransactional;
        this.timeToLive = timeToLiveMs;

        connectionFactory = queueConnectionFactory;
        // Set default rebind behavior - can be changed dynamically using setter
        if (theRebindBehavior != null)
        {
            rebindBehavior = theRebindBehavior;
        }
        // Register an exceptionlistener
        exceptionListener = new SystemJMSExceptionListenerHandler(this);
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
     * Connect to queue  and open a session.
     */
    @Override
    public void bind()
    {
        try
        {
            JNDIUtils.printJNDI(  context, logger );
            logger.info( "Binding to destination :" + destinationName );

            // Find ConnectionFactory
            queueConnectionFactory = getInstance().getQueueConnectionFactory(connectionFactory, context);
            // Get queue
            queue = getInstance().getQueue(destinationName, context);
            // Create conneciton to queue
            queueConnection = queueConnectionFactory.createQueueConnection();
            // Register an exceptionlistener
            queueConnection.setExceptionListener(exceptionListener);
            queueSession = queueConnection.createQueueSession(isTransactional, acknowledgeMode);

            queueSender = queueSession.createSender(queue);
            if (timeToLive > 0)
            {
                queueSender.setTimeToLive(timeToLive);
            }
            if (useTemporaryReplyDestination)
            {
                temporaryQueue = queueSession.createTemporaryQueue();
                logger.debug("TemporaryQueue created for this session " + temporaryQueue);
            }
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
     * <b>See documentaion in {@link org.grouter.common.jms.QueueSenderDestination#sendMessage(String)}.</b><br>
     * <br>
     */
    public synchronized void sendMessage(String message)
    {
        try
        {
            ObjectMessage msg = this.queueSession.createObjectMessage(message);
            setJMSHeader(msg);
            queueSender.send(msg);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (Exception ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
            throw new JMSRuntimeException("Could not send message.", ex);
        }
    }

    @Override
    public void rebind(AbstractDestination dest) throws RemoteGrouterException
    {
        rebindBehavior.rebind(this);
    }


     @Override
    public synchronized void sendMessage(Serializable message, int deliveryMode,
                                         int messagePriority, long timeToLive,
                                         HashMap<String, String> headerProperties)
    {

        try
        {
            ObjectMessage msg = createMessage(message, headerProperties);
            setJMSHeader(msg);
            queueSender.send(msg, deliveryMode, messagePriority, timeToLive);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (Exception ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
            throw new JMSRuntimeException("Could not send message.", ex);
        }

    }

    @Override
    public synchronized void sendMessage(Serializable message, HashMap<String, String> headerProperties)
    {
        try
        {
            ObjectMessage msg = createMessage(message, headerProperties);
            setJMSHeader(msg);
            queueSender.send(msg, this.acknowledgeMode, this.messagePriority, this.timeToLive);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (Exception ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
            throw new JMSRuntimeException("Could not send message.", ex);
        }
    }

    @Override
    public synchronized void sendMessage(Serializable message)
    {
        try
        {
            ObjectMessage msg = createMessage(message, null);
            setJMSHeader(msg);
            queueSender.send(msg);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (JMSException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
            throw new JMSRuntimeException("Could not send message.", ex);
        }
    }

    @Override
    public synchronized void sendMessage(Message message, int deliveryMode,
                                         int messagePriority, long timeToLive)
    {
        try
        {
            setJMSHeader(message);
            queueSender.send(message, deliveryMode, messagePriority, timeToLive);
            logger.debug("Message sent to destination : " + destinationName);

        } catch (Exception ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
            throw new JMSRuntimeException("Could not send message.", ex);
        }
    }


    @Override
    public Session getSession()
    {
        return queueSession;
    }

    @Override
    public synchronized void sendMessage(Message message)
    {
        try
        {
            setJMSHeader(message);
            queueSender.send(message);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (Exception ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
            throw new JMSRuntimeException("Could not send message.", ex);
        }
    }

    /**
     * Helper method that simply puts key value pairs into the JMS header.
     *
     * @param message a serializable object instance
     * @param headerProperties properties to store in header for JMS message
     *
     * @return ObjectMessage an object message
     */
    private ObjectMessage createMessage(Serializable message, HashMap<String,
            String> headerProperties)
    {
        ObjectMessage msg = null;
        try
        {
            msg = this.queueSession.createObjectMessage(message);
            msg.clearProperties();
            if (headerProperties != null)
            {
                for (String key : headerProperties.keySet())
                {
                    String value = headerProperties.get(key);
                    msg.setStringProperty(key, value);
                }
            }
        } catch (JMSException e)
        {
            logger.warn("Failed setting header for message.", e);
        }
        return msg;
    }

    /**
     * Set header for JMS message. Only JMSReplyTo, JMSCorrelationID and JMSType can be set using setters.
     *
     * @param msg Message
     * @throws javax.jms.JMSException a JMS exception when failing to set the header values
     */
    private void setJMSHeader(Message msg) throws JMSException
    {
        if (useTemporaryReplyDestination && temporaryQueue != null)
        {
            logger.debug("Using a temporary destination for this session.");
            msg.setJMSReplyTo(temporaryQueue);
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

    /**
     * Getter for temporary destination.
     *
     * @return TemporaryQueue
     */
    public TemporaryQueue getTemporaryQueue()
    {
        if (useTemporaryReplyDestination)
        {
            return temporaryQueue;
        } else
        {
            throw new IllegalStateException("You have used this destination in a wrong way. Have you " +
                    "used correct constructor for temporary destinations?");
        }
    }

    /**
     * <b>See documentation in {@link org.grouter.common.jms.AbstractSenderDestination#waitAndGetReplyFromTemporaryDestination(long)}.</b><br>
     * <br>
     */
    public Message waitAndGetReplyFromTemporaryDestination(long waitForMs)
    {
        QueueReceiver receiver = null;
        try
        {
            if (!useTemporaryReplyDestination)
            {
                throw new IllegalStateException(
                        "You have used this destination in a wrong way. Have you " +
                                "used correct constructor for temporary destinations? Use constructor" +
                                "where you indicate that you should be using a temporary Q. ");
            }
            receiver = queueSession.createReceiver(getTemporaryQueue());
            return receiver.receive(waitForMs);
        } catch (JMSException ex)
        {
            logger.warn("Waiting for reply on temp queue failed", ex);
            return null;
        }
        finally
        {
            if (receiver != null  )
            {
                try
                {
                    receiver.close();
                } catch (Exception ex1)
                {
                    //ignore
                }
            }
        }
    }

    /**
     * <b>See documentation in {@link org.grouter.common.jms.AbstractDestination#sendReplyToTemporaryDestination(javax.jms.Message)}.</b><br>
     */
    /*   public void sendReplyToTemporaryDestination(Message request)
    {
        TemporaryQueue replyQueue = null;
        javax.jms.QueueSender tempsender = null;
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

    */

}