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
