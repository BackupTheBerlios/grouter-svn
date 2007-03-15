package org.grouter.domain.servicelayer.spring;

import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Message;

import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


/**
 * @author Georges Polyzois
 */
public class RouterQueueListenerImpl  implements MessageListener // no need since we are using an adapter implements MessageListener
{
    private static Logger logger = Logger.getLogger(RouterQueueListenerImpl.class);
    RouterService service;

    public void setGrouterService(RouterService service)
    {
        this.service = service;
    }

    public void onMessage(javax.jms.Message receivedMessage)
    {
        try
        {
            ObjectMessage objectMessage = (ObjectMessage) receivedMessage;
            logger.debug("Got message : " + objectMessage.getObject());
            if (objectMessage.getObject() instanceof org.grouter.domain.entities.Message)
            {
                process( (Message) objectMessage);

            } else
            {
                logger.debug("Got unknown type of message!!!");
            }

        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }


    /**
     * Call session ejb for processing of event.
     *
     */
    public void process(Message message)
    {
        //org.grouter.domain.entities.Message message = (org.grouter.domain.entities.Message) objectMessage.getObject();
        logger.debug("##### Got a message and persisting it : " + message.getId() );
        service.saveMessage(message);
    }

}
