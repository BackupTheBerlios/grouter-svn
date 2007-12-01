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
@Table(name = "job_type")
public class JobType // extends BaseEntity
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
    public static final JobType BACKUP = new JobType(1L, "BACKUP");
    public static final JobType MAIL = new JobType(2L, "MAIL");


    public static Map<Long, JobType> valueOfMap = new HashMap<Long, JobType>();

    public JobType()
    {
    }

    public JobType(final Long id, final String name)
    {
        this.id = id;
        this.name = name;
    }

    static
    {
        valueOfMap.put(BACKUP.getId(), BACKUP);
        valueOfMap.put(MAIL.getId(), MAIL);
    }

    public static JobType valueOf(Long id)
    {
        return valueOfMap.get(id);
    }


    public static List<JobType> values()
    {
        return new ArrayList<JobType>(valueOfMap.values());
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