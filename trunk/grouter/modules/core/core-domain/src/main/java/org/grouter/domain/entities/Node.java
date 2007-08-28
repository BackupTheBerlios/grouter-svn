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
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Length;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@Entity
@Table(name = "node")
public class Node extends BaseEntity<String>
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    @NotNull
    private String id;

    @NotNull
    @Column(name = "displayName", nullable = false)
    private String displayName;

    @Column(name = "nodestatus_fk", nullable = false)
    private NodeStatus nodeStatus;

    @Column(name = "statusmessage", nullable = false)
    private String statusMessage;



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

    @Column(name = "backupuri")
    @Length(max = 2048)
    private String backupUri;

    @Column(name = "description")
    @Length(max = 2048)
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
     * @param id id of the node
     * @param dislayName a name to display
     */
    public Node(String id, String dislayName)
    {
        this.id = id;
        this.displayName = dislayName;
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

    public String toString()
    {
        String toString;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=").append(id).append(",");
        stringBuilder.append("name=").append(id).append(",");
        stringBuilder.append("senderStatic=").append(senderStatic).append(",");
        stringBuilder.append("receiverStatic=").append(receiverStatic);
        stringBuilder.append("nodeStatus=").append(nodeStatus);
        toString = stringBuilder.toString();
        return toString;
    }



}
