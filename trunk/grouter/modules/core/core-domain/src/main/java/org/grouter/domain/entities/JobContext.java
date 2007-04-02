package org.grouter.domain.entities;

/**
 * Holds data as name value pairs. JobContext data are stored persistently and are used for any given
 * job under its execution.
 *
 * @author Georges Polyzois
 */
public class JobContext
{
    Long id;
    Job job;
    String name;
    String value;


    public JobContext()
    {
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
