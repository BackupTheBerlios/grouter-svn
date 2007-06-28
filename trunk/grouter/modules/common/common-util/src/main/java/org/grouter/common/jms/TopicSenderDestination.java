/**
 * TopicDestination.java
 */
package org.grouter.common.jms;


import static org.grouter.common.jndi.ServiceLocatorContextAware.*;

import java.io.*;
import java.util.*;
import java.lang.IllegalStateException;

import javax.jms.*;
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
public class TopicSenderDestination extends AbstractSenderDestination
{
    // The logger.
    private static Logger logger = Logger.getLogger(TopicSenderDestination.class);
    // The ConnectionFactory used to connect to the Topic
    private TopicConnectionFactory topicConnectionFactory;
    // Connection to the Topic.
    private TopicConnection topicConnection;
    // The actual Topic.
    private Topic topic;
     // Sender to the Topic.
    protected TopicPublisher topicPublisher;
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
     * @param timeToLive             set to 0 if no time to live should be uses - TTL is specified
     *                               in millisecons!!!
     */
    public TopicSenderDestination(String topicName,
                                  String topicConnectionFactory,
                                  RebindBehavior rebindBehavior,
                                  Context context,
                                  long timeToLive)
    {
        init(topicName, topicConnectionFactory, rebindBehavior, context, timeToLive, AcknowledgeMode.NONE, true, false);
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
     * @param timeToLive             set to 0 if no time to live should be used - TTL is specified
     *                               in millisecons!!!
     * @param ackmode                this maps direclty to {@link javax.jms.Session} and the different types of
     *                               acknowledge modes existing there.
     */
    public TopicSenderDestination(String topicName,
                                  String topicConnectionFactory,
                                  RebindBehavior rebindBehavior,
                                  Context context,
                                  long timeToLive,
                                  AcknowledgeMode ackmode)
    {

        init(topicName, topicConnectionFactory, rebindBehavior, context, timeToLive, ackmode, false, false);
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
     * @param timeToLive             set to 0 if no time to live should be used - TTL is specified
     *                               in millisecons!!!
     * @param ackmode                this maps direclty to {@link javax.jms.Session} and the different types of
     *                               acknowledge modes existing there.
     * @param useTemporaryQueue      will create a temporary topic for this session
     */
    public TopicSenderDestination(String topicName,
                                  String topicConnectionFactory,
                                  RebindBehavior rebindBehavior,
                                  Context context,
                                  long timeToLive,
                                  AcknowledgeMode ackmode,
                                  boolean useTemporaryQueue)
    {
        init(topicName, topicConnectionFactory, rebindBehavior, context, timeToLive, ackmode, false, useTemporaryQueue);
    }


    /**
     * Initializer used by constructors.
     *
     * @param topicName              String
     * @param topicConnectionFactory String
     * @param theRebindBehavior      RebindBehavior
     * @param thecontext             Context
     * @param timeToLive             long
     * @param ackmode                AcknowledgeMode
     * @param isTransactional        boolean
     * @param useTemporaryTopic      boolean
     */
    private void init(String topicName,
                      String topicConnectionFactory,
                      RebindBehavior theRebindBehavior,
                      Context thecontext,
                      long timeToLive,
                      AcknowledgeMode ackmode,
                      boolean isTransactional,
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
        this.timeToLive = timeToLive;
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
            return;
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
     * Connect to topic  and open a session.
     */
    public void bind()
    {
        try
        {
            // Find ConnectionFactory
            topicConnectionFactory = getInstance().getTopicConnectionFactory(connectionFactory, context);
            // Get queue
            topic = getInstance().getTopic(destinationName, context);
            // Create conneciton to queue
            topicConnection = topicConnectionFactory.createTopicConnection();
            // Register an exceptionlistener
            topicConnection.setExceptionListener(exceptionListener);
            topicSession = topicConnection.createTopicSession(isTransactional, acknowledgeMode);
            topicPublisher = topicSession.createPublisher(topic);
            if (timeToLive > 0)
            {
                topicPublisher.setTimeToLive(timeToLive);
            }
            if (useTemporaryReplyDestination)
            {
                temporaryTopic = topicSession.createTemporaryTopic();
                logger.debug("TemporaryTopic created for this session " + temporaryTopic);
            }
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
     * <b>See documentaion in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(String)}.</b><br>
     * <br>
     */
    public void sendMessage(String message)
    {
        try
        {
            ObjectMessage msg = this.topicSession.createObjectMessage(message);
            setJMSHeader(msg);
            topicPublisher.send(msg);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (JMSException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
        } catch (IllegalStateException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
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
     * <b>See documentation in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(java.io.Serializable,java.util.HashMap)}.</b><br>
     * <br>
     */
    public synchronized void sendMessage(Serializable message, HashMap<String, String> headerProperties)
    {
        try
        {
            ObjectMessage msg = createMessage(message, headerProperties);
            setJMSHeader(msg);
            topicPublisher.send(msg, this.acknowledgeMode, this.messagePriority, this.timeToLive);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (JMSException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage());
        } catch (IllegalStateException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        }
    }

    /**
     * <b>See documentation in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(javax.jms.Message,int,int,long)}.</b><br>
     * <br>
     */
    public synchronized void sendMessage(Message message, int deliveryMode,
                                         int messagePriority, long timeToLive)
    {
        try
        {
            setJMSHeader(message);
            topicPublisher.send(message, deliveryMode, messagePriority, timeToLive);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (JMSException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        } catch (IllegalStateException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        }
    }

    public Session getSession()
    {
        return topicSession;
    }

    /**
     * <b>See documentaion in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(javax.jms.Message,int,int,long)}.</b><br>
     * <br>
     */
    public synchronized void sendMessage(Message message)
    {
        try
        {
            setJMSHeader(message);
            topicPublisher.send(message);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (JMSException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        } catch (IllegalStateException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        }
    }

    /**
     * <b>See documentation in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(java.io.Serializable,int,int,long,java.util.HashMap)}.</b><br>
     * <br>
     */
    public void sendMessage(Serializable message, int deliveryMode,
                            int messagePriority, long timeToLive,
                            HashMap<String, String> headerProperties)
    {
        try
        {
            ObjectMessage msg = createMessage(message, headerProperties);
            setJMSHeader(msg);
            topicPublisher.send(msg, deliveryMode, messagePriority, timeToLive);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (JMSException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        } catch (IllegalStateException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        }
    }

    /**
     * <b>See documentaion in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(java.io.Serializable)}.</b><br>
     * <br>
     */
    public void sendMessage(Serializable message)
    {
        try
        {
            ObjectMessage msg = createMessage(message, null);
            topicPublisher.send(msg);
            logger.debug("Message sent to destination : " + destinationName);
        } catch (JMSException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        } catch (IllegalStateException ex)
        {
            logger.error(
                    "Failed sending message to JMS provider using destination " + destinationName
                            + ". Error message : " + ex.getMessage(), ex);
        }
    }

    /**
     * <b>See documentation in {@link org.grouter.common.jms.AbstractSenderDestination#sendMessage(java.io.Serializable,java.util.HashMap)}.</b><br>
     * <br>
     */
    private ObjectMessage createMessage(Serializable message, HashMap<String,
            String> headerProperties)
    {
        ObjectMessage msg = null;
        try
        {
            msg = topicSession.createObjectMessage(message);
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
     * @throws javax.jms.JMSException
     */
    private void setJMSHeader(Message msg) throws JMSException
    {
        if (useTemporaryReplyDestination && temporaryTopic != null)
        {
            logger.debug("Using a temporary destination for this session.");
            msg.setJMSReplyTo(temporaryTopic);
        }
    }


    /**
     * <br>     for listener...
     */
/*    public void sendReplyToTemporaryDestination(Message request)
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
*/

    public TemporaryTopic getTemporaryDestination()
    {
        if (useTemporaryReplyDestination)
        {
            return temporaryTopic;
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
        TopicSubscriber receiver = null;
        try
        {
            if (!useTemporaryReplyDestination)
            {
                throw new IllegalStateException(
                        "You have used this destination in a wrong way. Have you " +
                                "used correct constructor for temporary destinations?");
            }
            receiver = topicSession.createSubscriber(getTemporaryDestination());
            return receiver.receive(waitForMs);
        } catch (JMSException ex)
        {
            logger.warn("Waiting for reply on temp topic failed", ex);
            return null;
        }
        finally
        {
            try
            {
                receiver.close();
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