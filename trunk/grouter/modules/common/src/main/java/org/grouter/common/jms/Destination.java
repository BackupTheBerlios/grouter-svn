/**
 * Destination.java
 */
package org.grouter.common.jms;

import java.io.*;
import java.util.*;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.*;

import org.apache.log4j.*;
import org.grouter.common.jndi.GenericServiceLocator;
import org.grouter.common.exception.RemoteSiriException;


/**
 * A Destination is defined in JMS to be either a Topic or Queue. Destinations subclassing
 * this abstract Destination class are able to unbind and rebind to a JMS provider using a
 * RebindBehaviour.
 *
 * Purpose of Destination is to hide some JMS plumbing code using this Fascade and adding a
 * rebindbehavious based on a strategy pattern.
 * You can set a rebind behavior dynamically or statically in constructors. Be sure to use the
 * correct one depending on your needs. Following descission tree might help:
 * Is messaging transactional?
 *               -> yes
 *               -> no -> acknowledge mode auto (messages are automatically acked
 *                        on a session and delivered only once)
 *                     -> duplicates ok (messages are automatically acked
 *                        on a session like in acknowledge mode auto and delivered
 *                        <b>at least</b> once)
 *                     -> client mode (messages are not automatically acknowledged
 *                        and need to be acknowledged in in code -> more complex code)
 *
 *
 *
 * @author Georges Polyzois
 * @version
 */
public abstract class Destination
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(Destination.class);
    /** Is sender? */
    protected boolean isSender;
    /** Receiver for the Queue. */
    protected MessageConsumer messageConsumer;
    /** Context to use when looking up things. */
    protected Context context;
    /** Service locator for caching and looking up jndi resources. */
    protected GenericServiceLocator serviceLocator;
    /** Transactional. */
    protected boolean isTransactional;
    /** Ack mode. */
    protected int acknowledgeMode;
    /** Name of Queue or Topic. */
    protected String destinationName;
    /** Exceptionlistener. */
    protected ExceptionListener exceptionListener;
    /** Connection factory JNDI name. */
    protected String connectionFactory = "UIL2ConnectionFactory";
    public final static String ACTIVEMQCONNECTIONFACTORY = "ConnectionFactory";
    public final static String JBOSSCONNECTIONFACTORY = "UIL2ConnectionFactory";
    /** Registered MessgeListener. */
    protected MessageListener listener;
    /** Default time to live from constructor. Can be overridden in send method.*/
    protected long timeToLive = 0;
    /** Default message priority if none is given */
    protected int messagePriority = 0;
    /** Used for response on requests for synchronous messaging in a session. */
    protected boolean useTemporaryReplyDestination = false;



    /**
     * A strategy to use for rebinding. Setting default rebind behavior -
     * can be changed dynamically using setter or through constructor
     *
     * @label has a behavior
     * @directed
     */
    protected RebindBehavior rebindBehavior = new EternalRebind();


    /**
     * Method to retrieve the consumer for this session. A consumer can not
     * exist if state of this object was created with isSender true - see
     * constructor. An <b>IllegalStateException</b> is raised if this is done.
     *
     * @return MessageConsumer
     */
    public MessageConsumer getMessageConsumer()
    {
        if (isSender)
        {
            throw new IllegalStateException(
                    "There is no consumer created for this JMS session." +
                    " Boolean isSender was true when constructing this instance");
        }
        return messageConsumer;
    }

    /**
     * Method for sending a string message.
     *
     * @param message String
     */
    public abstract void sendMessage(String message);

    /**
     * This send method will override settings for deliverymode, time to live
     * and message priority. It will also take a Hashmap<String,String> and decorate
     * JMS header properties accordingly.
     *
     * @param message Serializable your serializable object
     * @param deliveryMode int jms documentation
     * @param messagePriority int jms documentation 0-4 are gradations of normal priority
     * and 5-9 are indicates expeditious handling om messages in the jms provider (mom)
     * service. Beware though that there is no guarantee that moms will use this informtion.
     * @param timeToLive long see jms documentation
     * @param headerProperties HashMap a hashmap with string key value pairs used for the jms
     */
    public abstract void sendMessage(Serializable message, int deliveryMode,
                                     int messagePriority, long timeToLive,
                                     HashMap<String, String> headerProperties);


    /**
     * This send method will override settings for deliverymode, time to live
     * and message priority. It will also take a Hashmap<String,String> and decorate
     * JMS header properties accordingly.
     *
     * @param message Serializable your serializable object
     */
    public abstract void sendMessage(Message message);

    /**
     * This send method will take a Hahmap<String,String> and decorate
     * JMS header properties accordingly.
     *
     * @param message Serializable your serializable object.
     * @param headerProperties HashMap a hashmap with string key value pairs used for the jms header.
     */
    public abstract void sendMessage(Serializable message, HashMap<String,
                                     String> headerProperties);


    /**
     * Implementation will use default delivery mode, priority and time to live
     * as set by constructor.
     *
     * @param message Serializable
     */
    public abstract void sendMessage(Serializable message);

    /**
     * Message for sending an already created javax.jms.Message
     *
     * @param message The JMS message to send
     * @param deliveryMode int
     * @param messagePriority int jms documentation 0-4 are gradations of normal priority
     * and 5-9 are indicates expeditious handling om messages in the jms provider (mom)
     * service. Beware though that there is no guarantee that moms will use this informtion.
     * @param timeToLive long
     */
    public abstract void sendMessage(Message message, int deliveryMode,
                                     int messagePriority, long timeToLive);

    /**
     * Disconnect from queue. This method should be called from the ejbRemove method
     * if you are using a stateless session bean.
     *
     * @throws RemoteSiriException Unchecked exception.
     */
    public abstract void unbind() throws RemoteSiriException;

    /**
     * Connect to destination and open session.
     *
     * @throws RemoteSiriException Unchecked exception.
     */
    public abstract void bind() throws RemoteSiriException;

    /**
     * Handle over rebinding to behavior interface implementation (strategy pattern).
     *
     * @param dest Destination
     * @throws RemoteSiriException Unchecked exception.
     */
    abstract public void rebind(Destination dest) throws RemoteSiriException;

    /**
     * Send the reply on a temporary destination and set the correlation
     * id on the JMS header.
     * @param request Message
     */
    abstract public void sendReplyToTemporaryDestination(Message request);

    /**
     * Sender / publisher can choose to wait and receive an acknowledge or reply
     * on a request from a temporary destination.
     *
     * @param waitForMs long quality of service
     * @return Message
     */
    abstract public Message waitAndGetReplyFromTemporaryDestination(long waitForMs);



    /**
     * Get destination name.
     * @return java.lang.String
     */
    public String getDestinationName()
    {
        return destinationName;
    }

    /**
     * Sets rebind behaviour dynamically.
     * @param rebindBehavior RebindBehavior
     */
    public void setRebindBehavior(RebindBehavior rebindBehavior)
    {
        this.rebindBehavior = rebindBehavior;
    }


    /**
     * Remove the Messagelistener on this messageconsumer. This will make your
     * messagelistener stop receiving messages from the session.
     */
    final public synchronized void closeConsumer()
    {
        if (messageConsumer != null)
        {
            try
            {
                logger.debug("Closing consumer.");
                messageConsumer.close();
                messageConsumer = null;
            } catch (JMSException ex)
            {
                logger.error(
                        "Failed closing messageconsumer on removeReceiver call",
                        ex);
            }
        }
    }

    /**
     * Create a new messageconsumer and add the listener {#link javax.jms.MessageListener}
     * to the consumer.
     * Can be used in a use cases where we want to consume a number of
     * events and remove oour message listener, the process the events and reconnect our
     * messagelistener.
     */
    abstract public void setMessageListenerOnConsumer();

    /**
     * Get Session acknowledge mode from AcknowledgeMode.
     * @return int represents Session acknowledge modes
     */
    final protected int getAcknowledgeMode(AcknowledgeMode ackmode)
    {
        switch (ackmode)
        {
        case AUTO_ACKNOWLEDGE:
            return Session.AUTO_ACKNOWLEDGE;
        case DUPS_OK_ACKNOWLEDGE:
            return Session.DUPS_OK_ACKNOWLEDGE;
        case CLIENT_ACKNOWLEDGE:
            return Session.CLIENT_ACKNOWLEDGE;
        case NONE:
            return -1; //indicates transactional mode is used
        default:
            return Session.AUTO_ACKNOWLEDGE;
        }
    }
}
