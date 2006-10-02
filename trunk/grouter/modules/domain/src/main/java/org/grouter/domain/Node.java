package org.grouter.domain;

import org.apache.log4j.Logger;
import org.grouter.domain.systemuser.SystemUser;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;
import java.sql.Timestamp;


/**
 * Domain class.
 * @Author Georges Polyzois
 */
@javax.persistence.Entity
public class Node
{
    private static Logger logger = Logger.getLogger(Node.class);
    @Id
    private String id;
    @Column
    private String name;
    // No duplicate elements and the ordering is not relevant for us -> Set
    private Set<Message> messages;
    @ManyToOne
    private Message message;
    @Column
    private Timestamp modifiedOn;
    @ManyToOne
    private SystemUser modifiedBySystemUser;

    public Node()
    {
    }


    public Node(String id, String name, Set<Message> messages, Timestamp modifiedOn, SystemUser modifiedBySystemUser)
    {
        this.id = id;
        this.name = name;
        this.messages = messages;
        this.modifiedOn = modifiedOn;
        this.modifiedBySystemUser = modifiedBySystemUser;
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


    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
