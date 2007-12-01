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
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Domain entity representing the state of a job.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "job_state")
public class JobState   //extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-long")
    @GenericGenerator(name = "system-long", strategy = "assigned")
    @NotNull
    Long id;

    @Column(name = "name")
    String name;


    // waiting to start
    public static final JobState PENDING = new JobState(1L, "BACKUP");
    public static final JobState RUNNING = new JobState(2L, "MAIL");
    public static final JobState FINISHED = new JobState(3L, "FINISHED");
    public static final JobState STOPPED = new JobState(4L, "STOPPED");
    public static final JobState ERROR = new JobState(5L, "ERROR");


    public JobState()
    {
    }

    public JobState(final Long id, final String name)
    {
        this.id = id;
        this.name = name;
    }

    public static Map<Long, JobState> valueOfMap = new HashMap<Long, JobState>();

    static
    {
        valueOfMap.put(PENDING.getId(), PENDING);
        valueOfMap.put(RUNNING.getId(), RUNNING);
        valueOfMap.put(FINISHED.getId(), FINISHED);
        valueOfMap.put(STOPPED.getId(), STOPPED);
        valueOfMap.put(ERROR.getId(), ERROR);
    }


    public static List<JobState> values()
    {
        return new ArrayList<JobState>(valueOfMap.values());
    }

    public static JobState valueOf(Long id)
    {
        return valueOfMap.get(id);
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }
}
