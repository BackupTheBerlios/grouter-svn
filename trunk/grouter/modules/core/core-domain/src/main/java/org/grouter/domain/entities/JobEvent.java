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

import java.util.Date;

/**
 * @author Georges Polyzois
 */
public class JobEvent
{
    Long id;

    JobState jobState;

    Date created;

    String description;

    Job job;

    Long runTimeInMs;

    Long position;

    Boolean wasTiggeredManually = Boolean.FALSE;



    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }


    public Long getPosition()
    {
        return position;
    }

    public void setPosition(Long position)
    {
        this.position = position;
    }


    public Long getRunTimeInMs()
    {
        return runTimeInMs;
    }

    public void setRunTimeInMs(Long runTimeInMs)
    {
        this.runTimeInMs = runTimeInMs;
    }


    public Boolean getWasTiggeredManually()
    {
        return wasTiggeredManually;
    }

    public void setWasTiggeredManually(Boolean wasTiggeredManually)
    {
        this.wasTiggeredManually = wasTiggeredManually;
    }

    public Long getRunTimeInMinutes()
    {

        if( runTimeInMs != null && runTimeInMs >0 )
        {
            return runTimeInMs / 60000 ;
        }
        else
        {
            return 0L;
        }
    }

    public Long getRunTimeInSeconds()
    {
        if( runTimeInMs != null && runTimeInMs >0 )
        {
            return runTimeInMs / 1000 ;
        }
        else
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

    public JobState getJobState()
    {
        return jobState;
    }

    public void setJobState(JobState jobState)
    {
        this.jobState = jobState;
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
                ", jobState=" + jobState +
                ", created=" + created +
                ", description='" + description + '\'' +
                ", job=" + job +
                ", runTimeInMs=" + runTimeInMs +
                ", position=" + position +
                ", wasTiggeredManually=" + wasTiggeredManually +
                '}';
    }
}
