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
    private SystemUser modifiedBySystemUser;

    public Node()
    {
    }


    public Node(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Node(String name, Set<Message> messages, Date modifiedOn, SystemUser modifiedBySystemUser, Date creetedOn)
    {
        this.name = name;
        this.messages = messages;
        this.modifiedOn = modifiedOn;
        this.modifiedBySystemUser = modifiedBySystemUser;
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

    @Column(name = "MODIFIEDBYSYSTEMUSER")
    public SystemUser getModifiedBySystemUser()
    {
        return modifiedBySystemUser;
    }


    public void setModifiedBySystemUser(SystemUser modifiedBySystemUser)
    {
        this.modifiedBySystemUser = modifiedBySystemUser;
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
}
