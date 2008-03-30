/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@Entity
@Table(name = "node")
@Indexed(index = "indexes/node")
// Entity will be indexed for querying using Hibernate SystemServiceImpl
public class Node extends BaseEntity
{
    @Id
    @Column(name = "id")
    //@GeneratedValue(generator = "system-uuid")
    //@GenericGenerator(name = "system-uuid", strategy = "assigned")
     @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @DocumentId
    // Hibernate search
    private Long id;

    @Column(name = "idno", nullable = false)
    private String idNo;

    @NotNull
    @Column(name = "displayName", nullable = false)
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private String displayName;

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "nodestatus_fk", nullable = true)
    private NodeStatus nodeStatus;

    @Column(name = "statusmessage", nullable = false)
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private String statusMessage;


    // A nodes sender - if one is provided by the message itself that sender is used to override this
    @Column(name = "source", nullable = false)
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private String source;

    // A nodes receiver - if one is provided by the message itself that receiver is used to override this
    @Column(name = "receiver", nullable = false)
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private String receiver;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "node")
    private Set<Message> messages = new HashSet<Message>();

    @Column(name = "modifiedon")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private Date modifiedOn;

    @Column(name = "createdon")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private Date createdOn;

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "router_fk", nullable = true)
    private Router router;

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "inbound_endpoint_fk", nullable = true)
    private EndPoint inBound;

    @Column(name = "createdirectories")
    private Boolean createDirectories;

    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "outbound_endpoint_fk", nullable = true)
    private EndPoint outBound;


    @Column(name = "backupuri")
    @Length(max = 2048)
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private String backupUri;

    @Column(name = "description")
    @Length(max = 2048)
    @Field(index = Index.TOKENIZED, store = Store.YES)
    // Hibernate SystemServiceImpl  - fields to be indexed
    private String description;

    // a message read from a inbound endPoint is stored in this folder with a unique GUID - the command reader
    // thread will pull messages from this folder and process them  
    @Column(name = "internalqueueurl")
    @Length(max = 2048)
    private String internalQueueUri;

    @Transient
    transient private Long numberOfMessagesHandled;

    //@Transient
    //private User modifiedByUser;


    public Node()
    {
    }

    /**
     * Constructor for creating a valid entity.
     *
     * @param id         id of the node
     * @param dislayName a message to display
     */
    public Node(Long id, String dislayName)
    {
        this.id = id;
        this.displayName = dislayName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
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


    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public NodeStatus getNodeStatus()
    {
        return nodeStatus;
    }

    public void setNodeStatus(final NodeStatus nodeStatus)
    {
        this.nodeStatus = nodeStatus;
    }

    public String getStatusMessage()
    {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage)
    {
        this.statusMessage = statusMessage;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(final String source)
    {
        this.source = source;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(final String receiver)
    {
        this.receiver = receiver;
    }

    public Boolean getCreateDirectories()
    {
        return createDirectories;
    }

    public void setCreateDirectories(final Boolean createDirectories)
    {
        this.createDirectories = createDirectories;
    }

    /*
        @Override
        public boolean equals(Object object)
        {
            if (object == null)
            {
                // Never equal to a null object
                return false;
            }

            if (this == object)
            {
                // Always equal to self
                return true;
            }

            if (!this.getClass().equals(object.getClass()))
            {
                // Must be same class
                return false;
            }

            Node other = (Node) object;

            if (this.id == null)
            {
                // If both ids are zero, delegate to super equals,
                // otherwise not equal (this id is zero, but the other isn't)
                return (other.getId() == null) && super.equals(object);
            }

            // Equal if the ids are equal
            return (this.id.equals(other.getId()));
        }


        @Override
        public int hashCode()
        {
            if (id == null)
            {
                return super.hashCode();
            } else
            {
                return id.hashCode();
            }
        }
    */


    public String getIdNo()
    {
        return idNo;
    }

    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }

    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Node node = (Node) o;

        if (id != null ? !id.equals(node.id) : node.id != null) return false;

        return true;
    }

    public int hashCode()
    {
        return (id != null ? id.hashCode() : 0);
    }


}
