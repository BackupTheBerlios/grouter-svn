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
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Domain entity.
 * <p/>
 * The Collection type may contain duplicates while the Set type does not contain duplicates.
 * <p/>
 *
 * A Message can have one and only one Sender. We have two tables:
 * Tables:
 * MESSAGE
 * --------------------------------------------------------------
 * ID  |   NAME  |   CREATIONTIME   | content |    SENDERID (FK)
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


@Indexed ( index="indexes/message" )  // Entity will be indexed for querying using Hibernate Search
@Entity
@Table(name = "message")
public class Message implements Comparable, Serializable   //extends BaseEntity 
{
    //private static final long serialVersionUID = -6097635701783502292L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @DocumentId // Hibernate search
    private String id;

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.MERGE} , fetch = FetchType.EAGER )
    @JoinTable( name = "receiver_message",
                joinColumns = {@JoinColumn(name = "message_fk")},
                inverseJoinColumns = {@JoinColumn(name = "receiver_fk")})
    private Set<Receiver> receivers = new HashSet();

    @ManyToOne(cascade = {CascadeType.PERSIST} , fetch = FetchType.EAGER )
    @JoinColumn( name = "sender_fk", nullable = true)
    private Sender sender;

    @NotNull   // Hibernate Validator
    @Column(name = "content", nullable = false)
    @Field(index= Index.TOKENIZED, store= Store.YES)   // Hibernate Search  - fields to be indexed
    private String content;

    @Column(name = "counter")
    private BigInteger counter;


    @Column(name = "creationtimestamp")
    private Timestamp creationTimestamp;

    
    @NotNull
    @ManyToOne( cascade = {CascadeType.ALL, CascadeType.MERGE} )
    @JoinColumn(name = "node_fk", nullable = false)
    private Node node;

    public Message()
    {
    }

    /**
     * Constructor for a Message. Must add senders and receivers using addXX methods
     * for domain object to be complete.
     *
     * @param amessage a message
     */
    public Message(String amessage)
    {
        if (amessage == null)
        {
            throw new IllegalArgumentException("Non null parameters not allowed for this domain object.");
        }
        this.content = amessage;
    }

    

    public String getId()
    {
        return id;
    }

    public String getContent()
    {
        return content;
    }


    public void setContent(final String content)
    {
        this.content = content;
    }

    /**
     * No duplicate elements and the ordering is not relevant for us -> Set
     *
     * @return Set<Receiver>
     */
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




    public Timestamp getCreationTimestamp()
    {
        return creationTimestamp;
    }


    //    @ManyToOne()
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

    public BigInteger getCounter()
    {
        return counter;
    }

    public void setCounter(BigInteger counter)
    {
        this.counter = counter;
    }

    public int compareTo(final Object compareTo)
    {
        if( compareTo == null )
        {
            return -1;
        }

        if( !(compareTo instanceof Message) )
        {
            throw new ClassCastException( "Wrong class type. Was " + compareTo.getClass() );
        }



        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
