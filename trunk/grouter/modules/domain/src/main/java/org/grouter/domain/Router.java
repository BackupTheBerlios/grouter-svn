package org.grouter.domain;

import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;
import java.io.Serializable;


/**
 * Domain class - root entity.
 *
 * @Author Georges Polyzois
 */
@Entity
@Table(name = "ROUTER")
public class Router implements Serializable
{
    private static Logger logger = Logger.getLogger(Router.class);
    private String id;
    private String name;
    private Set<Node> nodes = new HashSet();
    private Timestamp startedOn;
    private long upTime;


    /**
     * Constructor.
     */
    public Router()
    {
    }



    public Router(String name, Set<Node> nodes, Timestamp startedOn, long upTime)
    {
        this.name = name;
        this.nodes = nodes;
        this.startedOn = startedOn;
        this.upTime = upTime;
    }



    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
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




    //
 /*    @OneToMany
    @JoinTable(name = "ROUTER_NODE",
            joinColumns = {@JoinColumn(name = "ROUTER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "NODE_ID")})*/
    @OneToMany
    @JoinColumn(name = "ROUTER_ID", nullable = true)
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
