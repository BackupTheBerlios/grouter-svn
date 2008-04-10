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

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Domain class - root entity.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "router")
public class Router extends BaseEntity
{
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    private String id;

    @NotNull
    @Column(name = "displayname")
    private String displayName;

    @Column(name = "description")
    private String description;

    /*
     * The inverse target property of the target entity. We do not need to enter the foreign
     * key kolumn as we do in the hbm mapping file -> less verbose.
     * mappedBy means inverse="true"  meaning that any changes made to this collection
     * is not persisted. If you want to persist it you need to call node.setRouter(...)
     * on the item in the Set
     *
     */
    @OneToMany(mappedBy = "router",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Node> nodes = new HashSet<Node>();

    @ManyToOne( cascade = {CascadeType.ALL} )
    @JoinColumn(name = "settings_fk")
    private Settings settings;

    @Column(name = "startedon")
    private Timestamp startedOn;

    @Column(name = "uptime")
    private long upTime;

    @Column(name = "rmiregistryport")
    private Integer rmiRegistryPort;

    @Column(name = "rmiserviceport")
    private Integer rmiServicePort;

    @Column(name = "homepath")
    private String homePath;


    public Router(String id, String displayName, String homePath)
    {
        this.id = id;
        this.displayName = displayName;
        this.homePath = homePath;
    }

    public Router()
    {
    }

    public Router(String id, String displayName)
    {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId()
    {
        return id;
    }


    public Timestamp getStartedOn()
    {
        return startedOn;
    }

    public void setStartedOn(Timestamp startedOn)
    {
        this.startedOn = startedOn;
    }

    public long getUpTime()
    {
        return upTime;
    }

    public void setUpTime(long upTime)
    {
        this.upTime = upTime;
    }


    public Set<Node> getNodes()
    {
        return nodes;
    }

    public void setNodes(Set<Node> nodes)
    {
        this.nodes = nodes;
    }


    public void setId(String id)
    {
        this.id = id;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public String getHomePath()
    {
        return homePath;
    }

    public void setHomePath(String homePath)
    {
        this.homePath = homePath;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Settings getSettings()
    {
        return settings;
    }

    public void setSettings(final Settings settings)
    {
        this.settings = settings;
    }

    public String printNodes()
    {
        StringBuffer buf = new StringBuffer();
        if (nodes != null)
        {
            buf.append("Nodes (total:" + nodes.size() + ")\n");
            for (Node node : nodes)
            {
                buf.append( "Node id:" + node.getId()).append( " " + node.getIdNo() ).append("\n");
            }
        } else
        {
            buf.append("No nodes");
        }
        return buf.toString();

    }


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

        Router other = (Router) object;

        if (this.id == null)
        {
            // If both ids are zero, delegate to super equals,
            // otherwise not equal (this id is zero, but the other isn't)
            return (other.getId() == null) && super.equals(object);
        }

        // Equal if the ids are equal
        return (this.id.equals(other.getId()));
    }


    /**
     * Uses the id property.
     */
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


    public Integer getRmiServicePort()
    {
        return rmiServicePort;
    }

    public void setRmiServicePort(Integer rmiServicePort)
    {
        this.rmiServicePort = rmiServicePort;
    }

    public Integer getRmiRegistryPort()
    {
        return rmiRegistryPort;
    }

    public void setRmiRegistryPort(Integer rmiRegistryPort)
    {
        this.rmiRegistryPort = rmiRegistryPort;
    }

}
