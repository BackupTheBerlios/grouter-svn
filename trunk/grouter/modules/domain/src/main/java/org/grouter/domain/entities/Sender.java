package org.grouter.domain.entitylayer;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@SuppressWarnings({"PersistenceModelORMInspection"})
@Entity
@Table(name = "SENDER")
public class Sender implements Serializable
{
    private String id;
    private Address address;
    private String name;
    private Set<Message> messages = new HashSet();

    public Sender()
    {
    }

    public Sender(String name, String id)
    {
        this.name = name;
        this.id = id;
    }

    /**
     * No duplicate elements and the ordering is not relevant for us so we use a Set<Message>
     *
     * To map this relationship without an extra join table use this:
     * @OneToMany
     * @JoinColumn(name="SENDER_ID")
     * else if you want ot use a join table do as we have done below. An extra join table is good
     * if any future requirements may need a many to many relationship, but of course lessens
     * the performance for queries with a lot of joins.
     *
     * @return
     */
    /*
     @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
@JoinTable(name = "USER_CONTACT_XREF",
joinColumns = { @JoinColumn(name = "user_fk", referencedColumnName = "user_id") },
inverseJoinColumns = { @JoinColumn(name = "contact_info_fk", referencedColumnName = "contact_info_id") })
     */
    
    @OneToMany
    @JoinTable(name = "SENDER_MESSAGE",
            joinColumns = {@JoinColumn(name = "SENDER_FK")},
            inverseJoinColumns = {@JoinColumn(name = "MESSAGE_FK")})
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

    @Column(name = "NAME")
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

    @Id
    @Column(name = "SENDER_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId()
    {
        return id;
    }


    //@ManyToOne
    @Transient
    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
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
