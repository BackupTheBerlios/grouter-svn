package org.grouter.domain;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

@Entity
public class Receiver implements Serializable
{
    @ManyToOne
    private Address address;
    @Id
    private String id;
    @Column
    private String name;
    @OneToMany
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
