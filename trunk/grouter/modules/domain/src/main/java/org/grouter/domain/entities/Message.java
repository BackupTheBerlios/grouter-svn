package org.grouter.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;
import java.io.Serializable;


/**
 * Domain class.
 * <p/>
 * The Collection type may contain duplicates while the Set type does not contain duplicates.
 * <p/>
 * A Message can have one and only one Sender. We have two tables:
 * Tables:
 * MESSAGE
 * --------------------------------------------------------------
 * ID  |   NAME  |   CREATIONTIME   | message |    SENDERID (FK)
 * <p/>
 * SENDER
 * --------------------------------------------------------------
 * ID |Êname
 * <p/>
 * <p/>
 * A Sender has a Set of messages and a Message has one Sender ->
 * The Sender has a OneToMany relationship with the Message and the Message
 * has a ManyToOne relationship with the Sender.
 * <p/>
 * 0..*               1
 * <-------------------
 * Message                      Sender
 * ------------------->
 * 1                   1
 *
 * @Author Georges Polyzois
 */

@Entity
@Table(name = "MESSAGE")
public class Message implements Serializable
{
    private String id;
    private Set<Receiver> receivers = new HashSet();
    private Sender sender;
    private String message;
    private Timestamp creationTimestamp;
    private Node node;

    public Message()
    {
    }

    /**
     * Constructor for a Message. Must add senders and receivers using addXX methods
     * for domain object to be complete.
     *
     * @param amessage
     */
    public Message(String amessage)
    {
        if (amessage == null)
        {
            throw new IllegalArgumentException("Non null parameters not allowed for this domain object.");
        }
        this.message = amessage;
    }


    /**
     * Fully constructs a Message object.
     *
     * @param message
     * @param receivers
     * @param sender
     */
    public Message(String message, Set<Receiver> receivers, Sender sender, Node node)
    {
        if (message == null || receivers == null || sender == null || node == null)
        {
            throw new IllegalArgumentException("Non null parameters not allowed for this domain object.");
        }
        this.message = message;
        this.receivers = receivers;
        this.sender = sender;
        this.node = node;
    }

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId()
    {
        return id;
    }

    @Column(name = "MESSAGE", nullable = false)
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * No duplicate elements and the ordering is not relevant for us -> Set
     *
     * @return Set<Receiver>
     */
    @Column(name = "RECEIVERS")
    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinTable(name = "RECEIVER_MESSAGE",
            joinColumns = {@JoinColumn(name = "MESSAGE_FK")},
            inverseJoinColumns = {@JoinColumn(name = "RECEIVER_FK")})
    public Set<Receiver> getReceivers()
    {
        return receivers;
    }

    /**
     * Prevent direct access to this Set.
     *
     * @param receivers
     */
    public void setReceivers(Set<Receiver> receivers)
    {
        this.receivers = receivers;
    }

    //@Column(name = "SENDER_ID")
    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "SENDER_FK", nullable = true)
    public Sender getSender()
    {
        return sender;
    }

    public void setSender(Sender sender)
    {
        this.sender = sender;
    }

    /**
     * Enforce bi-directionality in Java - needs to be done manually (in cotrast to ejb cmp).
     *
     * @param receiver
     */
    public void addToReceivers(Receiver receiver)
    {
        this.getReceivers().add(receiver);
        receiver.addToMessages(this);
    }

    /**
     * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
     *
     * @param receiver
     */
    public void removeFromReceivers(Receiver receiver)
    {
        this.getReceivers().remove(receiver);
        receiver.removeFromMessages(this);
    }


    /**
     * A convenience method for outputing all attributes in the instances' state.
     *
     * @return
     */
    public String reflectionToString()
    {
        return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this);
    }


    @Column(name = "CREATIONTIMESTAMP")
    public Timestamp getCreationTimestamp()
    {
        return creationTimestamp;
    }

    //    @ManyToOne()
    @ManyToOne( cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "NODE_FK", nullable = true)
    public Node getNode()
    {
        return node;
    }

    public void setNode(Node node)
    {
        this.node = node;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp)
    {
        this.creationTimestamp = creationTimestamp;
    }


    public void setId(String id)
    {
        this.id = id;
    }

}
