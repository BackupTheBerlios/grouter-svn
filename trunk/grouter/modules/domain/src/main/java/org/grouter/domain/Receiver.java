package org.grouter.domain;

import java.util.Set;
import java.util.HashSet;

@javax.persistence.Entity
public class Receiver
{
    /**
     * @directed true
     */
    private Address address;
    private String id;
    private String name;
    private Set<Message> messages = new HashSet();

    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    public Receiver()
    {
    }

    public Receiver(String name)
    {
        this.name = name;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }


    private void setId(String id)
    {
        this.id = id;
    }

    public void addToMessages(Message message)
    {
        this.getMessages().add(message);
        //message.addToReceivers(this);
    }

    public void removeFromMessages(Message message)
    {
        this.getMessages().remove(message);
        //message.removeFromReceivers(this);
    }


}
