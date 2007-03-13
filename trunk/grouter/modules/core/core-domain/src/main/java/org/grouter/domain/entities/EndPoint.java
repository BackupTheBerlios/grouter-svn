package org.grouter.domain.entities;

/**
 * An EndPoint represents the interface for inbound and outbound messaging. A Node can have one
 * inbound Endpoint and one outbound EndPoint.
 *
 * @author Georges Polyzois
 */
public class EndPoint
{
    Long id;
    String uri;
    String clazzName;
    String scheduleCron;
    EndPointType endPointType;
    Filter filter;

    public Filter getFilter()
    {
        return filter;
    }

    public void setFilter(Filter filter)
    {
        this.filter = filter;
    }



    public String getScheduleCron()
    {
        return scheduleCron;
    }


    public void setScheduleCron(String scheduleCron)
    {
        this.scheduleCron = scheduleCron;
    }

    public String getClazzName()
    {
        return clazzName;
    }

    public void setClazzName(String clazzName)
    {
        this.clazzName = clazzName;
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public EndPointType getEndPointType()
    {
        return endPointType;
    }

    public void setEndPointType(EndPointType endPointType)
    {
        this.endPointType = endPointType;
    }
}
