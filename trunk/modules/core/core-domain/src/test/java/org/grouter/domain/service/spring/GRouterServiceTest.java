package org.grouter.domain.service.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.entities.*;
import org.grouter.domain.service.RouterService;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Tests for the GRouter service interface.
 *
 * @author Georges Polyzois
 */
public class GRouterServiceTest extends AbstractServiceTests
{
    private static Log logger = LogFactory.getLog(GRouterServiceTest.class);
    RouterService service;

    public void setGrouterService(RouterService service)
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
        assertTrue(true);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Sender sender = new Sender("A test sender ");
        Message message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        sender.addToMessages(message);

        Set<Message> messages = new HashSet<Message>();
        messages.add(message);

        Node node = new Node(-1234L, "name");
        node.setMessages(messages);
        message.setNode(node);

        Set<Node> nodes = new HashSet<Node>();
        nodes.add(node);

        Router router = new Router("grouter", "aroutername");
        router.setNodes(nodes);
        router.setStartedOn(timestamp);
        router.setUpTime(1000);

        node.setRouter(router);

        service.saveMessage(message);

        logger.debug("## Saved instance with id : " + message.getId() );
        assertNotNull(message.getId());


    }


}
