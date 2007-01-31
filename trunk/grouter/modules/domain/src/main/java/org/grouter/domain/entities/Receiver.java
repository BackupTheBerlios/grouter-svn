package org.grouter.domain.entitylayer;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

@SuppressWarnings({"PersistenceModelORMInspection"})
@Entity
@Table(name = "RECEIVER")
public class Receiver implements Serializable
{
    private String id;
    private Address address;
    private String name;

    private Set<Message> messages = new HashSet();

    public Receiver()
    {
    }


    public Receiver(String name)
    {
        this.name = name;
    }

    /**
     * "mappedBy" makes Hibernate ignore changes made to this class - Receivers -
     * and that the other end of the association, the rec reivers collection in the Message
     * class, is the representation that should be synchronized with the database if you link
     * instances in Java code.
     *
     * @return
     */
    @ManyToMany(mappedBy = "receivers")
    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    @Transient
    public Address getAddress()
    {
        return address;
    }

    @Column(name = "NAME" )
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Id
    @Column(name = "RECEIVER_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    //Hibernate specific...
    public String getId()
    {
        return id;
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


    public void setId(String id)
    {
        this.id = id;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

}
