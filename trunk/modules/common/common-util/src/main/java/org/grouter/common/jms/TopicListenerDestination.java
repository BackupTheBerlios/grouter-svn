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


import static org.grouter.common.jndi.ServiceLocatorContextAware.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.*;
import org.grouter.common.jndi.JNDIUtils;
import org.grouter.common.jndi.ServiceLocatorException;

/**
 * See {@link org.grouter.common.jms.AbstractDestination} and use abstract interface to concrete
 * implementations.
 *
 * @author Georges Polyzois
 */
public class TopicListenerDestination extends AbstractListenerDestination
{
    // The logger.
    private static Logger logger = Logger.getLogger(TopicListenerDestination.class);
    // Connection to the Topic.
    private TopicConnection topicConnection;
    // The actual Topic.
    private Topic topic;
    // Session to the Topic.
    private TopicSession topicSession;
    // Used for request / reply on the same session.
    private TemporaryTopic temporaryTopic;


    /**
     * Constructor for use of a transactional (commit/rollback) way of handling
     * acknowledge of messages (code needs to use session to handle this).
     * See other constructor if you are using acknowledge mode based messaging
     * where AcknowledgeMode is needed.
     *
     * @param topicName              the JNDI name of the destination queue.
     * @param topicConnectionFactory the JNDI name of the Queue connection factory to use,
     *                               if null provided then the default factory will be used specified in {@link org.grouter.common.jms.AbstractDestination}
     * @param rebindBehavior         a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination {@link org.grouter.common.jms.RebindBehavior}. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               (@see javax.naming.Context).
     * @param listener               a message listener implementing {@link javax.jms.MessageListener} -
     *                               i.e. a class that will be receiving callbacks from jms provider. If null specified
     *                               then you will have to implement a receive behaviour in your consuming thread.
     */
    @SuppressWarnings({"UnusedDeclaration"})
    public TopicListenerDestination(String topicName,
                                    String topicConnectionFactory,
                                    RebindBehavior rebindBehavior,
                                    Context context,
                                    MessageListener listener)
    {

        init(topicName, true, AcknowledgeMode.NONE, topicConnectionFactory, rebindBehavior, listener,  context, false);
    }

    /**
     * Constructor for use with an acknowledge mode option specified. JMS message provider
     * will take care of acknowledging messages based on mode.
     *
     * @param topicName              the JNDI name of the destination queue.
     * @param topicConnectionFactory the JNDI name of the Queue connection factory to use,
     *                               if null provided then the default factory will be used specified in {@link org.grouter.common.jms.AbstractDestination}
     * @param rebindBehavior         a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination {@link org.grouter.common.jms.RebindBehavior}. If null
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
    public TopicListenerDestination(String topicName,
                                    String topicConnectionFactory,
                                    RebindBehavior rebindBehavior,
                                    Context context,
                                    MessageListener listener,
                                    AcknowledgeMode ackmode)
    {

        init(topicName, false, ackmode , topicConnectionFactory, rebindBehavior, listener, context, false);
    }

    /**
     * Constructor for use with an acknowledge mode option specified. JMS message provider
     * will take care of acknowledging messages based on mode.
     *
     * @param topicName              the JNDI name of the destination queue.
     * @param topicConnectionFactory the JNDI name of the Queue connection factory to use,
     *                               if null provided then the default factory will be used specified in {@link org.grouter.common.jms.AbstractDestination}
     * @param rebindBehavior         a RebindBeahvior specifies how we are supposed
     *                               to rebind to this destination {@link org.grouter.common.jms.RebindBehavior}. If null
     *                               specified an EternalRebind will be used.
     * @param context                provide the context to use for binding to the destination
     *                               {@link javax.naming.Context}. If null provided then the default will be used -
     *                               depends on your environemnt (jndi.properties, -Djava..., or InitialContext(new Hashtable())).
     * @param listener               a message listener implementing {@link javax.jms.MessageListener}   -
     *                               i.e. a class that will be receiving callbacks from jms provider. If null specified
     *                               then you will have to implement a receive behaviour in your consuming thread.
     * @param ackmode                this maps direclty to {@link javax.jms.Session} and the different types of
     *                               acknowledge modes existing there.
     * @param useTemporaryQueue      will create a temporary topic for this session
     */
    public TopicListenerDestination(String topicName,
                                    String topicConnectionFactory,
                                    RebindBehavior rebindBehavior,
                                    Context context,
                                    MessageListener listener,
                                    AcknowledgeMode ackmode,
                                    boolean useTemporaryQueue)
    {
        init(topicName, false, ackmode, topicConnectionFactory, rebindBehavior, listener, context, useTemporaryQueue);
    }


    /**
     * Initializer used by constructors.
     *
     * @param topicName              String
     * @param topicConnectionFactory String
     * @param theRebindBehavior      RebindBehavior
     * @param listener               MessageListener
     * @param ackmode                AcknowledgeMode
     * @param thecontext             Context
     * @param isTransactional        boolean
     * @param useTemporaryTopic      boolean
     */
    private void init(String topicName,
                      boolean isTransactional,
                      AcknowledgeMode ackmode,
                      String topicConnectionFactory,
                      RebindBehavior theRebindBehavior,
                      MessageListener listener,
                      Context thecontext,
                      boolean useTemporaryTopic)
    {
        int mappedacknowledgeMode = getAcknowledgeMode(ackmode);
        if (thecontext == null)
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
            context = thecontext;
        }
        useTemporaryReplyDestination = useTemporaryTopic;
        JNDIUtils.printJNDI(context, logger);
        destinationName = topicName;
        this.acknowledgeMode = mappedacknowledgeMode;
        this.isTransactional = isTransactional;
        try
        {
            serviceLocatorContextAware = getInstance();
        } catch (ServiceLocatorException ex)
        {
            logger.warn(ex, ex);
        }
        if (topicConnectionFactory != null)
        {
            connectionFactory = topicConnectionFactory;
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
     * Disconnect from topic. This method should be called from the ejbRemove method
     * if you are using a stateless session bean.
     */
    public void unbind()
    {
        if (topicConnection == null)
        {
            logger.error("Connection to destination topic was null - " +
                    "can not close null connection, returning.");
        } else
        {
            try
            {
                topicConnection.setExceptionListener(null);
                topicConnection.stop();
                if (temporaryTopic != null)
                {
                    temporaryTopic.delete();
                }
            } catch (JMSException e)
            {
                logger.error("Could not stop connection to destination.", e);
            } finally
            {
                try
                {
                    if (topicConnection != null)
                    {
                        topicConnection.close();
                    }
                } catch (JMSException e1)
                {
                    logger.error("Could not close topic connection!");
                }
            }
            topicConnection = null;
            temporaryTopic = null;
        }
    }


    /**
     * Retrieves the session for this destination. This method can be used if you
     * are handling trasacted sessions and need to explicitly get hold of the session
     * to do a commit or rollback.
     *
     * @return TopicSession
     */
    public TopicSession getTopicSession()
    {
        return topicSession;
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
     * Connect to topic  and open a session.
     */
    @Override
    public void bind()
    {
        try
        {
            // Find ConnectionFactory
            final TopicConnectionFactory topicConnectionFactory = getInstance().getTopicConnectionFactory(connectionFactory, context);
            // Get queue
            topic = getInstance().getTopic(destinationName, context);
            // Create conneciton to queue
            topicConnection = topicConnectionFactory.createTopicConnection();
            // Register an exceptionlistener
            topicConnection.setExceptionListener(exceptionListener);
            topicSession = topicConnection.createTopicSession(isTransactional, acknowledgeMode);

            messageConsumer = topicSession.createConsumer(topic);

            // Sets the receiver which onMessage method will be called.
            messageConsumer.setMessageListener(listener);

            topicConnection.start();
            logger.info("Bound to destination " + destinationName);
        } catch (JMSException e)
        {
            logger.error("Got exception with JMS provider during bind to destination " + destinationName + ". Error code : " + e.getErrorCode(), e);
            rebind(this);
        } catch (ServiceLocatorException ex)
        {
            logger.error(
                    "Got exception with JMS provider during bind to destination " +
                            destinationName + ".", ex);
            rebind(this);
        }
    }


    /**
     * <b>See documentaion in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(java.io.Serializable)}.</b><br>
     * <br>
     */
    public void rebind(AbstractDestination dest)
    {
        rebindBehavior.rebind(this);
    }


    /**
     * <br>
     */
    public synchronized void setMessageListenerOnConsumer()
    {
        if (messageConsumer == null)
        {
            try
            {
                logger.debug("Creating new consumer and adding Messagelistener.");
                messageConsumer = topicSession.createConsumer(topic);
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
        TemporaryTopic replyTopic = null;
        TopicPublisher tempsender = null;
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
            request.setJMSCorrelationID(request.getJMSMessageID());
            logger.debug("JMSCorrelationID was set!!!" +
                    request.getJMSCorrelationID());
            replyTopic = (TemporaryTopic) request.getJMSReplyTo();
            tempsender = topicSession.createPublisher(replyTopic);
            logger.debug("Created a tempsender and sending reply to " +
                    replyTopic);
            tempsender.send(request);
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
                if (replyTopic != null)
                {
                    replyTopic.delete();
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