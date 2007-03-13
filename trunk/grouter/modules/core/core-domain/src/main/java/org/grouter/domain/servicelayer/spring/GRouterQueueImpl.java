package org.grouter.domain.servicelayer.spring;

import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.GRouterService;
import org.grouter.domain.entities.Message;


/**
 * @author Georges Polyzois
 */
public class GRouterQueueImpl // no need since we are using an adapter implements MessageListener
{
    private static Logger logger = Logger.getLogger(GRouterQueueImpl.class);
    GRouterService service;

    public void setGrouterService(GRouterService service)
    {
        this.service = service;
    }

    /*public void onMessage(Message receivedMessage)
    {
        try
        {
            ObjectMessage objectMessage = (ObjectMessage) receivedMessage;
            logger.debug("Got message : " + objectMessage.getObject());
            if (objectMessage.getObject() instanceof org.grouter.domain.entities.Message)
            {
                persist(objectMessage);

            } else
            {
                logger.debug("Got unknown type of message!!!");
            }

        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }
      */

    /**
     * Call session ejb for processing of event.
     *
     */
    private void process(Message message)
    {
        //org.grouter.domain.entities.Message message = (org.grouter.domain.entities.Message) objectMessage.getObject();
        service.saveMessage(message);
    }
}
