package org.grouter.domain.entities;

import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;
import java.io.Serializable;


/**
 * Domain class - root entity.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "router")
public class Router extends BaseEntity
{
    private static Logger logger = Logger.getLogger(Router.class);


    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    private String id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany
    //@JoinColumn(name = "router_fk", nullable = true)
    private Set<Node> nodes = new HashSet();

    @Column(name = "startedon")
    private Timestamp startedOn;

    @Column(name = "uptime")
    private long upTime;

    @Column(name = "rmiregistryport")
    private Integer rmiRegistryPort;

    @Column(name = "rmiserviceport")
    private Integer rmiServicePort;

    @Column(name = "homepath")
    private String homePath;

    public Router(String id, String name, String homePath)
    {
        this.id = id;
        this.name = name;
        this.homePath = homePath;
    }


    public Integer getRmiServicePort()
    {
        return rmiServicePort;
    }

    public void setRmiServicePort(Integer rmiServicePort)
    {
        this.rmiServicePort = rmiServicePort;
    }

    public Integer getRmiRegistryPort()
    {
        return rmiRegistryPort;
    }

    public void setRmiRegistryPort(Integer rmiRegistryPort)
    {
        this.rmiRegistryPort = rmiRegistryPort;
    }

    public Router()
    {
    }


    public Router(String id, String name)
    {
        this.id = id;
        this.name = name;
    }


    public String getId()
    {
        return id;
    }


    public Timestamp getStartedOn()
    {
        return startedOn;
    }

    public void setStartedOn(Timestamp startedOn)
    {
        this.startedOn = startedOn;
    }

    public long getUpTime()
    {
        return upTime;
    }

    public void setUpTime(long upTime)
    {
        this.upTime = upTime;
    }


    public Set<Node> getNodes()
    {
        return nodes;
    }

    public void setNodes(Set<Node> nodes)
    {
        this.nodes = nodes;
    }


    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }


    public String getHomePath()
    {
        return homePath;
    }

    public void setHomePath(String homePath)
    {
        this.homePath = homePath;
    }

    public String toString()
    {
        return "Router{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nodes=" + nodes +
                ", startedOn=" + startedOn +
                ", rmiRegistryPort=" + rmiRegistryPort +
                ", rmiServicePort=" + rmiServicePort +
                ", upTime=" + upTime +
                '}';
    }


}
