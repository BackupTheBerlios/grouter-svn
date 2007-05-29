package org.grouter.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

@Entity
@Table(name = "receiver")
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
     * The "mappedBy" makes Hibernate ignore changes made to this class - Receiver -
     * and that the other end of the association is the master. Corresponda to inverse=true
     * in Hibernate and has to name the inverse property of the target entity.
     * 
     * @return
     */
    @ManyToMany( mappedBy = "receivers" )
    @JoinTable(
        name = "receiver_message",
        joinColumns = {@JoinColumn(name = "receiver_fk")},
        inverseJoinColumns = {@JoinColumn(name = "message_fk")}
    )
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

    @Column(name = "name" )
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @NotNull
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
