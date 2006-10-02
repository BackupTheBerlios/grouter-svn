package org.grouter.domain.servicelayer.ejb3;

import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.jms.GRouterPublishEventDTO;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.JMSException;
import javax.annotation.Resource;
import javax.naming.InitialContext;


@SuppressWarnings({"EjbErrors"})
@MessageDriven(activationConfig =
{
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/GrouterQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto.acknowledge")
})
public class GRouterQueueMDB implements MessageListener
{
    //(name="ear/GRouterBean/local")

    //@JndiInject(jndiName = ExternalSystemsLocal.LOOKUP_NAME)
    //private ExternalSystemsHandler externalSystemsHandler;

    private static Logger logger = Logger.getLogger(GRouterQueueMDB.class);

    //@EJB
    //private GRouterLocal gRouterLocal;

    @Resource
    private SessionContext sc;
    private static final String DOMAIN_GROUTER_BEAN_LOCAL = "domain/GRouterBean/local";

    /**
     * Method called when new message arrives on destination.
     *
     * @param receivedMessage
     */
    public void onMessage(Message receivedMessage)
    {
        try
        {
            ObjectMessage objectMessage = (ObjectMessage) receivedMessage;
            logger.debug("Got message : " + objectMessage.getObject());
            if (objectMessage.getObject() instanceof GRouterPublishEventDTO)
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

    private void persist(ObjectMessage objectMessage)
            throws JMSException
    {
        GRouterPublishEventDTO eventDTO = (GRouterPublishEventDTO) objectMessage.getObject();
        logger.debug("Got GRouterPublishEventDTO message");
        // logger.debug("Messages : " + printMessages(eventDTO.getMessages()));
        logger.debug("Calling bean");


        GRouterLocal gRouterLocal = (GRouterLocal)sc.lookup(DOMAIN_GROUTER_BEAN_LOCAL);
        gRouterLocal.persistMessageAndBroadcastEvent(eventDTO);
        //      gRouterLocal.persistMessageAndBroadcastEvent(null);
    }


    private String printMessages(org.grouter.domain.Message[] messages)
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (org.grouter.domain.Message message : messages)
        {
            stringBuffer.append(message.reflectionToString()).append("\n");
        }
        return stringBuffer.toString();

    }
}
