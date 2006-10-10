package org.grouter.domain.servicelayer.jms;

import org.grouter.domain.Message;

import java.io.Serializable;


public class GRouterPublishEventDTO implements Serializable
{
    String grouterId;
    String node;
    Message[] messages;


    public GRouterPublishEventDTO(String grouterId, String node, Message[] messages)
    {
        this.grouterId = grouterId;
        this.node = node;
        this.messages = messages;
    }

    public String getGrouterId()
    {
        return grouterId;
    }

    public String getNode()
    {
        return node;
    }

    public Message[] getMessages()
    {
        return messages;
    }
}
