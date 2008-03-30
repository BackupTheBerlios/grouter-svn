package org.grouter.domain.service;

import org.grouter.domain.entities.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Oct 10, 2006
 * Time: 2:41:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class RouterMessageFactory
{
    /**
     * Util method.
     *
     * @param sequenceNumber
     * @return
     */
    private static Message createRouter(String sequenceNumber)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Sender sender = new Sender("A test sender " + sequenceNumber);
        Message message = new Message("A test message" + sequenceNumber);
        Receiver receiver = new Receiver("A test receiver" + sequenceNumber);
        message.addToReceivers(receiver);
        message.setSender(sender);
        message.setCreationTimestamp(timestamp);
        sender.addToMessages(message);

        Set<Message> messages = new HashSet<Message>();
        messages.add(message);

        Node node = new Node(-1234L, "name");
        //messages, new Date(), null,new Date());
        node.setMessages(messages);
        message.setNode(node);

        Set<Node> nodes = new HashSet<Node>();
        nodes.add(node);

        Router router = new Router("grouter", "aname");
        router.setNodes(nodes);
        router.setStartedOn(timestamp);
        router.setUpTime(1000);

        node.setRouter(router);

        return message;
    }


    public static Message createRouter()
    {
        return createRouter(null);

    }

    public static Message createRouter(int i)
    {
        String sequencenr = Integer.toString(i);
        return createRouter(sequencenr);
    }


}
