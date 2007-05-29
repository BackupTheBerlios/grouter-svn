package org.grouter.domain.entities;

import org.apache.log4j.Logger;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.LazyInitializationException;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Length;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.io.Serializable;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@Entity
@Table(name = "node")
public class Node extends BaseEntity
{
    @Transient
    private static Logger logger = Logger.getLogger(Node.class);

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    @NotNull
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    // A nodes sender - if one is provided by the message itself that sender is used to override this
    @Column(name = "senderstatic")

    private String senderStatic;
    // A nodes receiver - if one is provided by the message itself that receiver is used to override this
    @Column(name = "receiverstatic")
    private String receiverStatic;


    @OneToMany(cascade = {CascadeType.REMOVE},
            mappedBy = "node_fk")
//    @JoinTable(name = "NODE_MESSAGE",
//            joinColumns = {@JoinColumn(name = "NODE_FK")},
//            inverseJoinColumns = {@JoinColumn(name = "MESSAGE_FK")})
    private Set<Message> messages = new HashSet<Message>();

    @Column(name = "modifiedon")
    private Date modifiedOn;

    @Column(name = "createdon")
    private Date createdOn;

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "router_fk", nullable = true)
    private Router router;

    // to store messages persistently
    @Column(name = "inbound_endpoint_fk")
    private EndPoint inBound;

    @Column(name = "outbound_endpoint_fk")
    private EndPoint outBound;

    @Column(name = "backupurl")
    @Length(max = 1000)
    private String backupUri;

    // a message read from a inbound endPoint is stored in this folder with a unique GUID - the command reader
    // thread will pull messages from this folder and process them  
    @Column(name = "internalqueueurl")
    @Length(max = 1000)
    private String internalQueueUri;

    @Transient
    transient private Long numberOfMessagesHandled;

    @Transient
    private User modifiedByUser;


    public Node()
    {
    }

    /**
     * Constructor for creating a valid entity.
     *
     * @param id
     * @param name
     */
    public Node(String id, String name)
    {
        this.id = id;
        this.name = name;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    public Date getModifiedOn()
    {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn)
    {
        this.modifiedOn = modifiedOn;
    }


    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Router getRouter()
    {
        return router;
    }

    public void setRouter(Router router)
    {
        this.router = router;
    }


    public Date getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn)
    {
        this.createdOn = createdOn;
    }



    public EndPoint getInBound()
    {
        return inBound;
    }

    public void setInBound(EndPoint inBound)
    {
        this.inBound = inBound;
    }

    public EndPoint getOutBound()
    {
        return outBound;
    }

    public void setOutBound(EndPoint outBound)
    {
        this.outBound = outBound;
    }


    public String getBackupUri()
    {
        return backupUri;
    }

    public void setBackupUri(String backupUri)
    {
        this.backupUri = backupUri;
    }


    public String getSenderStatic()
    {
        return senderStatic;
    }

    public void setSenderStatic(String senderStatic)
    {
        this.senderStatic = senderStatic;
    }


    public String getReceiverStatic()
    {
        return receiverStatic;
    }

    public void setReceiverStatic(String receiverStatic)
    {
        this.receiverStatic = receiverStatic;
    }


    public Long getNumberOfMessagesHandled()
    {
        return numberOfMessagesHandled;
    }

    public void setNumberOfMessagesHandled(Long numberOfMessagesHandled)
    {
        this.numberOfMessagesHandled = numberOfMessagesHandled;
    }


    public String getInternalQueueUri()
    {
        return internalQueueUri;
    }

    public void setInternalQueueUri(String internalQueueUri)
    {
        this.internalQueueUri = internalQueueUri;
    }

    public String toString()
    {
        String toString;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=" + id + ",");
        stringBuilder.append("name=" + id + ",");
        stringBuilder.append("senderStatic=" + senderStatic + ",");
        stringBuilder.append("receiverStatic=" + receiverStatic);
        toString = stringBuilder.toString();
        return toString;
    }
}
