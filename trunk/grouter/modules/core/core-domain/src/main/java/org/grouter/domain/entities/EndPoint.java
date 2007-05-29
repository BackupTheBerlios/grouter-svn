package org.grouter.domain.entities;

import org.hibernate.validator.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.io.Serializable;

/**
 * An EndPoint represents the interface for inbound and outbound messaging. A Node can have one
 * inbound Endpoint and one outbound EndPoint.
 * Tied to an EndPoint is a collection of EndPointContext items, keyname/value pairs with both optional
 * keyname/value pairs and keyname/value pairs to override existing default keyname/value pairs in a worker thread -
 * a reader or writer.
 * <p/>
 * Quartz is used to schedule reader and writer jobs (threads) tied to each EndPoint.
 * <p/>
 * An EndPoint can also have a filter - filtering out messages which should not be used.
 *
 * @author Georges Polyzois
 */

@Entity
@Table(name = "endpoint")
public class EndPoint extends BaseEntity
{
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    private String id;

    @Column(name = "clazzname")
    private String clazzName;

    @Column(name = "uri")
    private String uri;

    @Column(name = "cron")
    private String scheduleCron;

    // Unidirectional ManyToOne
    @ManyToOne
    @JoinColumn( name = "endpoint_type_fk")
    EndPointType endPointType;

    @Transient
    transient Filter filter;

    @OneToMany( targetEntity = org.grouter.domain.entities.EndPointContext.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER )
//    @JoinTable( name="endpoint_context",
//                joinColumns = @JoinColumn(name="endpoint_fk"))
//    @OnDelete( action = OnDeleteAction.CASCADE )
    Map endPointContext = new HashMap();

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
