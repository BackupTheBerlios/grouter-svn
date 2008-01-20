package org.grouter.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.*;
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

/**
 * Represents the state of the Node. A Node might not be running, manually stopped
 * or worse not working properly.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "nodestatus")
public class NodeStatus  extends BaseEntity
{

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-long")
    @GenericGenerator(name = "system-long", strategy = "assigned")
    @NotNull
    Long id;

    @Column(name = "name")
    String name;

    // before beeing scheduled
    public static NodeStatus NOTSTARTED = new NodeStatus(1L, "NOTSTARTED");
    // scheduler has picked it up
    public static NodeStatus SCHEDULED_TO_START = new NodeStatus(2L, "SCHEDULED_TO_START");
    public static NodeStatus RUNNING = new NodeStatus(3L, "MAIL");
    public static NodeStatus RESCHEDULED_TO_START = new NodeStatus(4L, "RESCHEDULED_TO_START");
    // stopped manually
    public static NodeStatus STOPPED = new NodeStatus(5L, "STOPPED");
    public static NodeStatus ERROR = new NodeStatus(6L, "ERROR");
    // upon start of router old nodes that are not within the config file are set to this state
    public static NodeStatus NOT_CONFIGURED_TO_START = new NodeStatus(7L, "NOT_CONFIGURED_TO_START");

    public static Map<Long, NodeStatus> values = new HashMap<Long, NodeStatus>();

    static
    {
        values.put(NOTSTARTED.getId(), NOTSTARTED);
        values.put(SCHEDULED_TO_START.getId(), SCHEDULED_TO_START);
        values.put(RUNNING.getId(), RUNNING);
        values.put(RESCHEDULED_TO_START.getId(), RESCHEDULED_TO_START);
        values.put(STOPPED.getId(), STOPPED);
        values.put(ERROR.getId(), ERROR);
        values.put(NOT_CONFIGURED_TO_START.getId(), NOT_CONFIGURED_TO_START);
    }

    public static NodeStatus valueOf(Long id)
    {
        return values.get(id);
    }


    public NodeStatus(Long id, String name)
    {
        this.name = name;
        this.id = id;
    }


    public NodeStatus()
    {
    }

    /**
     * Util method.
     *
     * @return lsit of NodeStatus
     */
    public static List<NodeStatus> values()
    {
        return Collections.unmodifiableList(new ArrayList<NodeStatus>(values.values()));
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final NodeStatus that = (NodeStatus) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    /**
     * Uses the id property.
     */
    @Override
    public int hashCode()
    {
        int result;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


}
