package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.Message;
import org.grouter.domain.servicelayer.AbstractRouterServiceInit;
import org.grouter.domain.servicelayer.jms.GRouterPublishEventDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RouterServiceTest extends AbstractRouterServiceInit
{
    private static Log log = LogFactory.getLog(RouterServiceTest.class);

    static
    {
        isSpringTest = false;
    }

    public RouterServiceTest()
    {
        super();
    }

    /**
     * Create a message.
     *
     * @throws Exception
     */
    public void testCreateMessage() throws Exception
    {


        Message messagePersisted = null;

        Message[] messages = new Message[1];
        messages[0] = message;
        remoteRouter.persistMessageAndBroadcastEvent(new GRouterPublishEventDTO("routerid","nodeid",messages));

        //messagePersisted = remoteRouter.createMessage(message);
        
     //   log.debug("## Saved instance with id : " + messagePersisted.getId() + " timestamp " + messagePersisted.getCreationTimestamp());
     //   assertNotNull(messagePersisted.getId());



        /*    Object obj = factory.getBean(beanName);
        GRouterService gRouterService = (GRouterService) obj;
        messagePersisted = gRouterService.createMessage(message);
        */
    }



}