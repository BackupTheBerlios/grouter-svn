package org.grouter.domain;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.annotation.Generated;
import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;
import java.io.Serializable;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@Entity
public class Message implements Serializable
{
    @Id
    @Generated("AUTO")
    private String id;
    @Column( nullable = false)
    private String message;
    // No duplicate elements and the ordering is not relevant for us -> Set
    private Set<Receiver> receivers = new HashSet();
    @ManyToOne
    private Sender sender;
    @Column
    private Timestamp creationTimestamp;
    @ManyToOne()
    private Node node;

    public Message()
    {}

    /**
     * Constructor for a Message. Must add senders and receivers using addXX methods
     * for domain object to be complete.
     * @param amessage
     */
    public Message(String amessage)
    {
        if (amessage == null )
        {
            throw new IllegalArgumentException("Non null parameters not allowed for this domain object.");
        }
        this.message = amessage;
    }


    /**
     * Fully constructs a Message object.
     * @param message
     * @param receivers
     * @param sender
     */
    public Message(String message, Set<Receiver> receivers, Sender sender)
    {
        if (message == null || receivers == null || sender == null)
        {
            throw new IllegalArgumentException("Non null parameters not allowed for this domain object.");
        }
        this.message = message;
        this.receivers = receivers;
        this.sender = sender;
    }

    public String getId()
    {
        return id;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Prevent direct access to this Set.
     *
     * @return Set<Receiver>
     */
    protected Set<Receiver> getReceivers()
    {
        return receivers;
    }

    /**
     * Prevent direct access to this Set.
     *
     * @param receivers
     */
    protected void setReceivers(Set<Receiver> receivers)
    {
        this.receivers = receivers;
    }

    public Sender getSender()
    {
        return sender;
    }

    public void setSender(Sender sender)
    {
        this.sender = sender;
    }

    private void setId(String id)
    {
        this.id = id;
    }

    /**
     * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
     *
     * @param receiver
     */
    public void addToReceivers(Receiver receiver)
    {
        this.getReceivers().add(receiver);
        //receiver.addToMessages(this);
    }

    /**
     * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
     *
     * @param receiver
     */
    public void removeFromReceivers(Receiver receiver)
    {
        this.getReceivers().remove(receiver);
        //receiver.removeFromMessages(this);
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





    public Timestamp getCreationTimestamp()
    {
        return creationTimestamp;
    }

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

}
