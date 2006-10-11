package org.grouter.domain.entities;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.systemuser.SystemUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
@Table(name = "NODE")
public class Node implements Serializable
{
    @Transient
    private static Logger logger = Logger.getLogger(Node.class);
    private String id;
    private String name;
    private Set<Message> messages = new HashSet<Message>();
    private Timestamp modifiedOn;
    private Router router;


    @Transient
    private SystemUser modifiedBySystemUser;

    public Node()
    {
    }


    public Node(String name, Set<Message> messages, Timestamp modifiedOn, SystemUser modifiedBySystemUser)
    {
        this.name = name;
        this.messages = messages;
        this.modifiedOn = modifiedOn;
        this.modifiedBySystemUser = modifiedBySystemUser;
    }


    @Id
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

    public Timestamp getModifiedOn()
    {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn)
    {
        this.modifiedOn = modifiedOn;
    }

    public SystemUser getModifiedBySystemUser()
    {
        return modifiedBySystemUser;
    }


    public void setModifiedBySystemUser(SystemUser modifiedBySystemUser)
    {
        this.modifiedBySystemUser = modifiedBySystemUser;
    }

    @OneToMany
    @JoinTable(name = "NODE_MESSAGE",
            joinColumns = {@JoinColumn(name = "NODETTEST_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MESSAGETEST_ID")})
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


    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    public Router getRouter()
    {
        return router;
    }

    public void setRouter(Router router)
    {
        this.router = router;
    }

}
