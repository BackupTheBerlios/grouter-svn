package org.grouter.common.jms;

import org.apache.log4j.Logger;

import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author Georges
 */
public abstract class AbstractListenerDestination extends AbstractDestination
{
    //Logger.
    private static Logger logger = Logger.getLogger(AbstractListenerDestination.class);
    //Receiver for the Queue.
    protected MessageConsumer messageConsumer;
    // Registered MessgeListener.
    protected MessageListener listener;

    /**
     * Method to retrieve the consumer for this session. A consumer can not
     * exist if state of this object was created with isSender true - see
     * constructor. An <b>IllegalStateException</b> is raised if this is done.
     *
     * @return MessageConsumer
     */
    public MessageConsumer getMessageConsumer()
    {
        return messageConsumer;
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
                        "Failed closing messageconsumer.",
                        ex);
            }
        }
    }
    
    /**
     * Send the reply on a temporary destination and set the correlation
     * id on the JMS header before sending it. This is an ACK sent from the listener.
     *
     * @param request Message
     */
    abstract public void sendReplyToTemporaryDestination(Message request);
    


    
}
