package org.grouter.domain;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;


/**
 * Domain class - root entity.
 * @Author Georges Polyzois
 */
@Entity
@Table(name="GROUTER")
public class GRouter
{
    private static Logger logger = Logger.getLogger(GRouter.class);
    @Id
    private String id;
    @Column(name="NAME",nullable = false,length = 255)
    private String name;
    @OneToMany
    private Set<Node> nodes = new HashSet();
    @Column
    private Timestamp startedOn;
    @Column
    private long upTime;


    /**
     * Constructor.
     */
    public GRouter()
    {
    }

    /**
     * Full constructor.
     * @param id
     * @param name
     * @param nodes
     * @param startedOn
     * @param upTime
     */
    public GRouter(String id, String name, Set<Node> nodes, Timestamp startedOn, long upTime)
    {
        this.id = id;
        this.name = name;
        this.nodes = nodes;
        this.startedOn = startedOn;
        this.upTime = upTime;
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

    public String getId()
    {
        return id;
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
