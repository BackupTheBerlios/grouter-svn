package org.grouter.domain.entities;

/**
 * Context store - used to override EndPoint default settings. Holds key value pairs used by
 * {@link EndPoint}:s.
 *
 * @author Georges Polyzois
 */
public class EndPointContext
{
    Long id;
    String keyname;
    String value;
    EndPoint endPoint;


    public EndPointContext()
    {
    }


    public String getKeyname()
    {
        return keyname;
    }

    public void setKeyname(String keyname)
    {
        this.keyname = keyname;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }


    public EndPoint getEndPoint()
    {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint)
    {
        this.endPoint = endPoint;
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
