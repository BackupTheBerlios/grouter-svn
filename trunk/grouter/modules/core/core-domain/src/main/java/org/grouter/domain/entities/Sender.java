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

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@SuppressWarnings({"PersistenceModelORMInspection"})
@Entity
@Table(name = "sender")
public class Sender extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;


    @ManyToOne(targetEntity = Address.class )
    private Address address;

    @Column(name = "name")
    private String name;

    @OneToMany
    private Set<Message> messages = new HashSet();

    public Sender()
    {
    }

    public Sender(String name, String id)
    {
        this.name = name;
        this.id = id;
    }

    /**
     * No duplicate elements and the ordering is not relevant for us so we use a Set<Message>
     * <p/>
     * To map this relationship without an extra join table use this:
     *
     * @return
     * @OneToMany
     * @JoinColumn(name="SENDER_ID") else if you want ot use a join table do as we have done below. An extra join table is good
     * if any future requirements may need a many to many relationship, but of course lessens
     * the performance for queries with a lot of joins.
     */
    /*
     @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
@JoinTable(name = "USER_CONTACT_XREF",
joinColumns = { @JoinColumn(name = "user_fk", referencedColumnName = "user_id") },
inverseJoinColumns = { @JoinColumn(name = "contact_info_fk", referencedColumnName = "contact_info_id") })
     */
    public Set<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public Sender(String name)
    {
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    /**
     * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
     *
     * @param message
     */
    public void addToMessages(Message message)
    {
        this.getMessages().add(message);
        message.setSender(this);
    }

    /**
     * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
     *
     * @param message
     */
    public void removeFromMessages(Message message)
    {
        this.getMessages().remove(message);
        message.setSender(null);
    }

}
