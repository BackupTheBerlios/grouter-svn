package org.grouter.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.Column;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;


/**
 * Domain class.
 * @Author Georges Polyzois
 */
@Entity
public class Sender implements Serializable
{
    @ManyToOne
    private Address address;
    @Id
    private String id;
    @Column
    private String name;
    // No duplicate elements and the ordering is not relevant for us -> Set
    private Set<Message> messages = new HashSet();

    public Sender()
    {
    }

    public Sender(String name, String id)
    {
        this.name = name;
        this.id = id;
    }

    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public Sender(String name)
    {
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    /**
     * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
     *
     * @param message
     */
    public void addToMessages(Message message)
    {
        this.getMessages().add(message);
        message.setSender(this);
    }

    /**
     * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
     *
     * @param message
     */
    public void removeFromMessages(Message message)
    {
        this.getMessages().remove(message);
        message.setSender(null);
    }

}
