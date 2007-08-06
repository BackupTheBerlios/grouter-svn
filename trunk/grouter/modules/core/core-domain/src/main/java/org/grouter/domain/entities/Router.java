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

import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;


/**
 * Domain class - root entity.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "router")
public class Router extends BaseEntity
{
    private static Logger logger = Logger.getLogger(Router.class);


    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    private String id;

    @NotNull
    @Column(name = "displayname")
    private String displayName;

    @Column(name = "description")
    private String description;

    @OneToMany
    //@JoinColumn(displayName = "router_fk", nullable = true)
    private Set<Node> nodes = new HashSet();

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

    public String toString()
    {
        return "Router{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", nodes=" + nodes +
                ", startedOn=" + startedOn +
                ", rmiRegistryPort=" + rmiRegistryPort +
                ", rmiServicePort=" + rmiServicePort +
                ", upTime=" + upTime +
                '}';
    }


}
