/**
 * QueueDestination.java
 */
package org.grouter.common.jms;


import static org.grouter.common.jndi.GenericServiceLocator.getInstance;

import java.io.*;
import java.util.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TemporaryQueue;
import javax.naming.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.*;
import org.grouter.common.jndi.JNDIUtils;
import org.grouter.common.jndi.ServiceLocatorException;
import org.grouter.common.exception.RemoteSiriException;

/**
 * See {@link Destination} and use abstract interface to concrete
 * implementations.
 *
 * @author
 * @version
 */
public class QueueDestination extends Destination
{
    /** The logger. */
    private static Logger logger = Logger.getLogger(QueueDestination.class);
    /** The ConnectionFactory used to connect to the Queue. */
    private QueueConnectionFactory queueConnectionFactory;
    /** Connection to the Queue. */
    private QueueConnection queueConnection;
    /** The actual Queue or message channel. */
    private Queue queue;
    /** Sender to the Queue. */
    protected QueueSender queueSender;
    /** Session to the Queue. */
    protected QueueSession queueSession;
    /** Used for request / reply on the same session. */
    private TemporaryQueue temporaryQueue;

    /**
     * Constructor for use of a transactional (commit/rollback) way of handling
     * acknowledge of messages (code needs to use session to handle this).
     * See other constructor if you are using acknowledge mode based messaging.
     *
     * @param queueName the JNDI name of the destination queue.
     * @param isSender wheter destination is used to send messages
     * @param queueConnectionFactory the JNDI name of the Queue connection factory to use,
     * if null provided then the default factory will be used.
     * @param rebindBehavior a RebindBeahvior specifies how we are supposed
     * to rebind to this destination {@link RebindBehavior}. If null
     * specified an EternalRebind will be used.
     * @param context provide the context to use for binding to the destination
     * (@see javax.naming.Context).
     * @param timeToLive set to 0 if no time to live should be uses - TTL is specified
     * in millisecons!!!
     * @param listener a message listener implementing @see javax.jms.MessageListener  -
     * i.e. a class that will be receiving callbacks from jms provider. If null specified
     * then you will have to implement a receive behaviour in your consuming thread.
     */
    public QueueDestination(String queueName,
                            boolean isSender,
                            String queueConnectionFactory,
                            RebindBehavior rebindBehavior,
                            Context context,
                            long timeToLive,
                            MessageListener listener)
    {
        if(isSender && listener!=null)
        {
            throw new IllegalArgumentException("If you intend to send messages on this destination" +
                    " then a messagelistener of null must be specified");
        }
        init(queueName, true, AcknowledgeMode.NONE, isSender, queueConnectionFactory, rebindBehavior, listener, timeToLive, context, false);
    }

    /**
     * Constructor for use with an acknowledge mode specified. The JMS message provider
     * will take care of acknowledging messages based on mode set, can be overridden by
     * sendmessage implementations.
     *
     * @param queueName the JNDI name of the destination queue.
     * @param isSender wheter destination is used to send messages or not.
     * @param queueConnectionFactory the JNDI name of the Queue connection factory to use,
     * if null provided then the default factory will be used specified in
     * {@link Destination}
     * @param theRebindBehavior a RebindBeahvior specifies how we are supposed
     * to rebind to this destination @link RebindBehavior. If null
     * specified an EternalRebind will be used.
     * @param context provide the context to use for binding to the destination
     * {@link javax.naming.Context}. If null provided then the default will be used -
     * depends on your environemnt (jndi.properties, -Djava..., or InitialContext(new Hashtable())).
     * @param timeToLive set to 0 if no time to live should be used - TTL is specified
     * in millisecons!!!
     * @param listener a message listener implementing {@link javax.jms.MessageListener}   -
     * i.e. a class that will be receiving callbacks from jms provider. If null specified
     * then you will have to implement a receive behaviour in your consuming thread.
     * @param ackmode this maps direclty to {@link javax.jms.Session} and the different types of
     * acknowledge modes existing there.
     */
    public QueueDestination(String queueName,
                            boolean isSender,
                            final String queueConnectionFactory,
                            RebindBehavior theRebindBehavior,
                            Context context,
                            long timeToLive,
                            MessageListener listener,
                            AcknowledgeMode ackmode)
    {
        if(isSender && listener!=null)
        {
            throw new IllegalArgumentException("If you intend to send messages on this destination" +
                    " then a messagelistener of null must be specified.");
        }
        init(queueName, false, ackmode, isSender, queueConnectionFactory, theRebindBehavior, listener, timeToLive, context,false);
    }


    /**
     * Constructor for use with an acknowledge mode specified. The JMS message provider
     * will take care of acknowledging messages based on mode set, can be overridden by
     * sendmessage implementations.
     *
     * @param queueName the JNDI name of the destination queue.
     * @param isSender wheter destination is used to send messages or not.
     * @param queueConnectionFactory the JNDI name of the Queue connection factory to use,
     * if null provided then the default factory will be used specified in
     * {@link Destination}
     * @param theRebindBehavior a RebindBeahvior specifies how we are supposed
     * to rebind to this destination {@link RebindBehavior}. If null
     * specified an EternalRebind will be used.
     * @param context provide the context to use for binding to the destination
     * {@link javax.naming.Context}. If null provided then the default will be used -
     * depends on your environemnt (jndi.properties, -Djava..., or InitialContext(new Hashtable())).
     * @param timeToLive set to 0 if no time to live should be used - TTL is specified
     * in millisecons!!!
     * @param listener a message listener implementing {@link javax.jms.MessageListener}   -
     * i.e. a class that will be receiving callbacks from jms provider. If null specified
     * then you will have to implement a receive behaviour in your consuming thread.
     * @param ackmode this maps direclty to {@link javax.jms.Session} and the different types of
     * acknowledge modes existing there.
     * @param useTemporaryQueue will create a temporary queue for this session
     */
    public QueueDestination(String queueName,
                            boolean isSender,
                            final String queueConnectionFactory,
                            RebindBehavior theRebindBehavior,
                            Context context,
                            long timeToLive,
                            MessageListener listener,
                            AcknowledgeMode ackmode,
                            boolean useTemporaryQueue)
    {
        if (isSender && listener != null)
        {
            throw new IllegalArgumentException(
                    "If you intend to send messages on this destination" +
                    " then a messagelistener of null must be specified.");
        }
        init(queueName, false, ackmode, isSender, queueConnectionFactory,
             theRebindBehavior, listener, timeToLive, context,
             useTemporaryQueue);
    }


    /**
     * Initializer used by constructors.
     *
     * @param queueName String
     * @param isTransactional boolean
     * @param ackmode AcknowledgeMode
     * @param isSender boolean
     * @param queueConnectionFactory String
     * @param theRebindBehavior RebindBehavior
     * @param listener a listener
     * @param timeToLive how long we should keep message.
     * @param theContext if null create new, else use given one.
     * @param  useTemporaryQueue to use a temporary queue for reply
     */
    private void init(String queueName, boolean isTransactional,
                      AcknowledgeMode ackmode, boolean isSender,
                      String queueConnectionFactory,
                      RebindBehavior theRebindBehavior,
                      MessageListener listener,
                      long timeToLive,
                      Context theContext,
                      boolean useTemporaryQueue)
    {
        int mappedacknowledgeMode = getAcknowledgeMode(ackmode);
        if(theContext==null)
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
        }
        else
        {
            context = theContext;
        }
        useTemporaryReplyDestination = useTemporaryQueue;
        JNDIUtils.printJNDI(context,logger);
        destinationName = queueName;
        this.acknowledgeMode = mappedacknowledgeMode;
        this.isTransactional = isTransactional;
        this.isSender = isSender;
        this.timeToLive =  timeToLive;

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
            return;
        } else
        {
            try
            {
                queueConnection.setExceptionListener(null);
                queueConnection.stop();
                if(temporaryQueue!=null)
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
     * @see javax.jms.MessageListener .
     *
     * @return MessageListener
     */
    public MessageListener getListener()
    {
        return listener;
    }

    /**
     * Connect to queue  and open a session.
     */
    public void bind()
    {
        try
        {
            // Find ConnectionFactory
            queueConnectionFactory = getInstance().getQueueConnectionFactory(connectionFactory,context);
            // Get queue
            queue = getInstance().getQueue(destinationName,context);
            // Create conneciton to queue
            queueConnection = queueConnectionFactory.createQueueConnection();
            // Register an exceptionlistener
            queueConnection.setExceptionListener(exceptionListener);
            queueSession = queueConnection.createQueueSession(isTransactional,acknowledgeMode);

            if (isSender)
            {
                queueSender = queueSession.createSender(queue);
                if(timeToLive>0)
                {
                  queueSender.setTimeToLive(timeToLive);
                }
                if(useTemporaryReplyDestination)
                {
                    temporaryQueue = queueSession.createTemporaryQueue();
                    logger.debug("TemporaryQueue created for this session " + temporaryQueue);
                }
            } else
            {
                messageConsumer = queueSession.createReceiver(queue);
            }
            if (listener!=null)
            {
                // Sets the receiver which onMessage method will be called.
                messageConsumer.setMessageListener(listener);
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
                    destinationName + ".");
            rebind(this);
        }
    }



    /**
     * <b>See documentaion in {@link Destination#sendMessage(String)}.</b><br>
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
     * <b>See doocumentaion in {@link Destination#sendMessage(Serializable,HashMap\<String, String>)}.</b><br>
     * <br>
     */
    public void rebind(Destination dest) throws RemoteSiriException
    {
        rebindBehavior.rebind(this);
    }


    /**
     * <b>See documentaion in {@link Destination#sendMessage(Serializable,int,int,long,HashMap)}.</b><br>
     * <br>
     */
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
     * <b>See doocumentaion in {@link Destination#sendMessage(Serializable,HashMap\<String, String>)}.</b><br>
     * <br>
     */
    public synchronized void sendMessage(Serializable message, HashMap<String, String> headerProperties)
    {
        try
        {
            ObjectMessage msg = createMessage(message, headerProperties);
            setJMSHeader(msg);
            queueSender.send(msg, this.acknowledgeMode, this.messagePriority, this.timeToLive);
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
     * <b>See doocumentaion in {@link Destination#sendMessage(Serializable)}.</b><br>
     * <br>
     *
     */
    public synchronized void sendMessage(Serializable message)
    {
        try
        {
            ObjectMessage msg = createMessage(message,null);
            setJMSHeader(msg);
            queueSender.send(msg);
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
    * <b>See documentaion in {@link Destination#sendMessage(Message,int,int,long)}.</b><br>
    * <br>
    */
    public synchronized void sendMessage(Message message, int deliveryMode,
                                         int messagePriority, long timeToLive)
    {
        try
        {
            setJMSHeader(message);
            queueSender.send(message, deliveryMode, messagePriority, timeToLive);
            logger.debug("Message sent to destination : " + destinationName );

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
    * <b>See documentaion in {@link Destination#sendMessage(Message,int,int,long)}.</b><br>
    * <br>
    */
    public synchronized void sendMessage(Message message)
    {
        try
        {
            setJMSHeader(message);
            queueSender.send(message);
            logger.debug("Message sent to destination : " + destinationName );
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
     * <b>See documentaion in {@link Destination#sendMessage(Serializable,HashMap)}.</b><br>
     * <br>
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
            logger.warn("Failed setting header for message.",e);
        }
        return msg;
    }

    /**
     * Set header for JMS message. Only JMSReplyTo, JMSCorrelationID and JMSType can be set using setters.
     *
     * @param msg Message
     * @throws JMSException
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
     * <b>See documentaion in {@link Destination#setMessageListenerOnConsumer()}.</b><br>
     * <br>
     */
    public synchronized void setMessageListenerOnConsumer()
    {
        if(messageConsumer==null)
        {
            try
            {
                logger.debug("Creating new consumer and adding Messagelistener.");
                messageConsumer = queueSession.createReceiver(queue);
                messageConsumer.setMessageListener(listener);
            } catch (JMSException ex)
            {
                logger.error("Failed closing messageconsumer on removeReceiver call",ex);
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

    /**
     * Getter for temporary destination.
     * @return TemporaryQueue
     */
    public TemporaryQueue getTemporaryQueue()
    {
        if(useTemporaryReplyDestination)
        {
            return temporaryQueue;
        }
        else
        {
            throw new IllegalStateException("You have used this destination in a wrong way. Have you " +
                                        "used correct constructor for temporary destinations?");
        }
    }

    /**
     * <b>See documentation in {@link Destination#waitAndGetReplyFromTemporaryDestination(long)}.</b><br>
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
                        "used correct constructor for temporary destinations?");
            }
            receiver = queueSession.createReceiver(getTemporaryQueue());
            return receiver.receive(waitForMs);
        } catch (JMSException ex)
        {
            logger.warn("Waiting for reply on temp queue failed",ex);
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
     * <b>See documentation in {@link Destination#sendReplyToTemporaryDestination(Message)}.</b><br>
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

}
