package org.grouter.domain.entities;

import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;

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
@SuppressWarnings({"PersistenceModelORMInspection"})
@Entity
@Table(name = "NODE")
public class Node implements Serializable
{
    @Transient
    private static Logger logger = Logger.getLogger(Node.class);
    private String id;
    private String name;

    // A nodes sender - if one is provided by the message itself that sender is used to override this
    private String senderStatic;
    // A nodes receiver - if one is provided by the message itself that receiver is used to override this
    private String receiverStatic;
    private Set<Message> messages = new HashSet<Message>();
    private Date modifiedOn;
    private Date createdOn;
    private Router router;
    // to store messages persistently
    private EndPoint inBound;
    private EndPoint outBound;
    private String backupUri;

    //private NodeType nodeType;
    @Transient
    private User modifiedByUser;

    @Column(name = "MODIFIEDBYUSER")
    public User getModifiedByUser()
    {
        return modifiedByUser;
    }

    public void setModifiedByUser(User modifiedByUser)
    {
        this.modifiedByUser = modifiedByUser;
    }

    public Node()
    {
    }


    public Node(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Node(String name, Set<Message> messages, Date modifiedOn, User modifiedByUser, Date creetedOn)
    {
        this.name = name;
        this.messages = messages;
        this.modifiedOn = modifiedOn;
        this.modifiedByUser = modifiedByUser;
        this.createdOn = creetedOn;
    }


    @Id
    @Column(name = "NODE_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Column(name = "MODIFIEDON")
    public Date getModifiedOn()
    {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn)
    {
        this.modifiedOn = modifiedOn;
    }



    @OneToMany
    @JoinColumn(name = "NODE_FK", nullable = true)
//    @JoinTable(name = "NODE_MESSAGE",
//            joinColumns = {@JoinColumn(name = "NODE_FK")},
//            inverseJoinColumns = {@JoinColumn(name = "MESSAGE_FK")})
    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "ROUTER_FK", nullable = true)
    public Router getRouter()
    {
        return router;
    }

    public void setRouter(Router router)
    {
        this.router = router;
    }


    @Column(name = "CREATEDON")
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


    public String toString()
    {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", senderStatic='" + senderStatic + '\'' +
                ", receiverStatic='" + receiverStatic + '\'' +
                ", messages=" + messages +
                ", modifiedOn=" + modifiedOn +
                ", createdOn=" + createdOn +
                ", router=" + router.getId() +
                ", inBound=" + inBound +
                ", outBound=" + outBound +
                ", backupUri='" + backupUri + '\'' +
                ", modifiedByUser=" + modifiedByUser +
                '}';
    }
}
