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

import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.Date;

/**
 * A Job can fire of an event which will be stored for lateer viewing.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "job_event")
public class JobEvent extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    Long id;

    @Column(name = "message")
    @NotNull
    String message;

    @ManyToOne
    @JoinColumn(name = "job_fk")
    Job job;

    @Column(name = "processtime_ms")
    Long processTimeInMs;


    @Column(name = "position")
    Long position;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Long getPosition()
    {
        return position;
    }

    public void setPosition(Long position)
    {
        this.position = position;
    }


    public Long getProcessTimeInMs()
    {
        return processTimeInMs;
    }

    public void setProcessTimeInMs(Long processTimeInMs)
    {
        this.processTimeInMs = processTimeInMs;
    }

    public Long getRunTimeInMinutes()
    {

        if (processTimeInMs != null && processTimeInMs > 0)
        {
            return processTimeInMs / 60000;
        } else
        {
            return 0L;
        }
    }

    public Long getRunTimeInSeconds()
    {
        if (processTimeInMs != null && processTimeInMs > 0)
        {
            return processTimeInMs / 1000;
        } else
        {
            return 0L;
        }
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Job getJob()
    {
        return job;
    }

    public void setJob(Job job)
    {
        this.job = job;
    }


    public String toString()
    {
        return "JobEvent{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", job=" + job +
                ", processTimeInMs=" + processTimeInMs +
                ", position=" + position +
                '}';
    }
}
