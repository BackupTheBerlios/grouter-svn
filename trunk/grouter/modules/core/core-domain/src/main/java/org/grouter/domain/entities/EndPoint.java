package org.grouter.domain.entities;

/**
 * An EndPoint represents the interface for inbound and outbound messaging. A Node can have one
 * inbound Endpoint and one outbound EndPoint.
 *
 * @author Georges Polyzois
 */
public class EndPoint
{
    String uri;
    Long id;
    Filter filter;
    Connector connector;
    EndPointContext endPointContext;
    String clazzName;


    public String getClazzName()
    {
        return clazzName;
    }

    public void setClazzName(String clazzName)
    {
        this.clazzName = clazzName;
    }

    public EndPointContext getEndPointContext()
    {
        return endPointContext;
    }

    public void setEndPointContext(EndPointContext endPointContext)
    {
        this.endPointContext = endPointContext;
    }

    public Filter getFilter()
    {
        return filter;
    }

    public void setFilter(Filter filter)
    {
        this.filter = filter;
    }

    public Connector getConnector()
    {
        return connector;
    }

    public void setConnector(Connector connector)
    {
        this.connector = connector;
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
}
