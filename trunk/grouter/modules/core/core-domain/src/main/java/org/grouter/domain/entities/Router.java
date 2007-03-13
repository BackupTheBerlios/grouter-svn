package org.grouter.domain.entities;

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


    public Router(String id)
    {
        this.id = id;
    }

    public Router(String name, Set<Node> nodes, Timestamp startedOn, long upTime)
    {
        this.name = name;
        this.nodes = nodes;
        this.startedOn = startedOn;
        this.upTime = upTime;
    }



    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId()
    {
        return id;
    }


    @Column(name = "STARTEDON")
    public Timestamp getStartedOn()
    {
        return startedOn;
    }

    public void setStartedOn(Timestamp startedOn)
    {
        this.startedOn = startedOn;
    }

    @Column(name = "UPTIME")
    public long getUpTime()
    {
        return upTime;
    }

    public void setUpTime(long upTime)
    {
        this.upTime = upTime;
    }





//     @OneToMany
//    @JoinTable(name = "ROUTER_NODE",
//            joinColumns = {@JoinColumn(name = "ROUTER_FK")},
//            inverseJoinColumns = {@JoinColumn(name = "NODE_FK")})
    @OneToMany
    @JoinColumn(name = "ROUTER_FK", nullable = true)
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

    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        buf.append( "name : " + this.name + "\n");
        for (Node node : nodes)
        {
            buf.append( "node : " + node.toString() + "\n");

        }


        return buf.toString();

    }



}
