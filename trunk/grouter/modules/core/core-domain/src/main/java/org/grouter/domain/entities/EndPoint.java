package org.grouter.domain.entities;

import java.util.Map;
import java.util.HashMap;

/**
 * An EndPoint represents the interface for inbound and outbound messaging. A Node can have one
 * inbound Endpoint and one outbound EndPoint.
 * Tied to an EndPoint is a collection of EndPointContext items, keyname/value pairs with both optional
 * keyname/value pairs and keyname/value pairs to override existing default keyname/value pairs in a worker thread -
 * a reader or writer.
 *
 * Quartz is used to schedule reader and writer jobs (threads) tied to each EndPoint.
 *
 * An EndPoint can also have a filter - filtering out messages which should not be used.
 *
 * @author Georges Polyzois
 */
public class EndPoint
{
    String id;
    String uri;
    String clazzName;
    String scheduleCron;
    EndPointType endPointType;
    Filter filter;
    Map endPointContext =  new HashMap();

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


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    public Map getEndPointContext()
    {
        return endPointContext;
    }

    public void setEndPointContext(Map endPointContext)
    {
        this.endPointContext = endPointContext;
    }

    public String toString()
    {
        return "EndPoint{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", clazzName='" + clazzName + '\'' +
                ", scheduleCron='" + scheduleCron + '\'' +
                ", endPointType=" + endPointType +
                ", filter=" + filter +
                '}';
    }
}
