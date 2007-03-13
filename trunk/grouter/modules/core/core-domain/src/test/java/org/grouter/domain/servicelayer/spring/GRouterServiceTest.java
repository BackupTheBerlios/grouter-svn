package org.grouter.domain.servicelayer.spring;

import org.grouter.domain.servicelayer.GRouterService;
import org.grouter.domain.entities.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;

/**
 * Tests for the GRouter service interface.
 *
 * @author Georges Polyzois
 */
public class GRouterServiceTest extends AbstractServiceTests
{
    private static Log logger = LogFactory.getLog(GRouterServiceTest.class);
    GRouterService service;

    public void setGrouterService(GRouterService service)
    {
        this.service = service;
    }


    //private final static String beanName = "messageServiceManager";


    public GRouterServiceTest()
    {
       setAutowireMode(AUTOWIRE_BY_NAME);
    }

    public void testCreateMessage() throws Exception
    {
        setAutowireMode(AUTOWIRE_BY_NAME);
        assertTrue(true);


        Message messagePersisted = null;
//        GRouterService gRouterServiceService = (GRouterService) factory.getBean(beanName);
//        messagePersisted = gRouterServiceService.saveMessage(message);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Sender sender = new Sender("A test sender " );
        Message message = new Message("A test message" );
        Receiver receiver = new Receiver("A test receiver" );
        message.addToReceivers(receiver);
        message.setSender(sender);
        message.setCreationTimestamp(timestamp);
        sender.addToMessages(message);

        Set<Message> messages = new HashSet<Message>();
        messages.add(message);

        Node node = new Node("file node", messages, new Date(), null, new Date());
        message.setNode(node);

        Set<Node> nodes = new HashSet<Node>();
        nodes.add(node);

        Router router = new Router("grouter", nodes, timestamp, 1000);
        node.setRouter(router);

        messagePersisted = service.saveMessage( message );

        logger.debug("## Saved instance with id : " + messagePersisted.getId() + " timestamp " + messagePersisted.getCreationTimestamp());
        assertNotNull(messagePersisted.getId());


    }


}
