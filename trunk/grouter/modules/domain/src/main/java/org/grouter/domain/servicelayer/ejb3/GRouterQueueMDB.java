package org.grouter.domain.servicelayer.ejb3;

import org.apache.log4j.Logger;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.SessionContext;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.annotation.Resource;

/**
 * A Message Driven Bean registering for callbacks on a in queue, receiving events.
 */

@SuppressWarnings({"EjbErrors"})
@MessageDriven(activationConfig =
{
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/GrouterQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto.acknowledge")
})
public class GRouterQueueMDB implements MessageListener
{
    //@JndiInject(jndiName = ExternalSystemsLocal.LOOKUP_NAME)
    //private ExternalSystemsHandler externalSystemsHandler;

    private static Logger logger = Logger.getLogger(GRouterQueueMDB.class);

    

    // This does not work in jboss...
    //@EJB
    //private GRouterLocal gRouterLocal;

    @SuppressWarnings({"EjbErrorInspection"})
    @Resource
    private SessionContext sc;


    /**
     * Method called when new message arrives on destination. Callback from JMS container.
     *
     * @param receivedMessage
     */
    public void onMessage(Message receivedMessage)
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

    /**
     * Call session ejb for processing of event.
     * 
     * @param objectMessage
     * @throws JMSException
     */
    private void persist(ObjectMessage objectMessage)
            throws JMSException
    {
        org.grouter.domain.entities.Message message = (org.grouter.domain.entities.Message) objectMessage.getObject();
        logger.debug("Got GRouterPublishEventDTO message");
        GRouterLocal gRouterLocal = (GRouterLocal)sc.lookup( GRouterLocal.DOMAIN_GROUTER_BEAN_LOCAL );
        gRouterLocal.createMessage(message);
    }


    /**
     * Util method.
     * 
     * @param messages
     * @return
     */
    private String printMessages(org.grouter.domain.entities.Message[] messages)
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (org.grouter.domain.entities.Message message : messages)
        {
            stringBuffer.append(message.reflectionToString()).append("\n");
        }
        return stringBuffer.toString();

    }
}
