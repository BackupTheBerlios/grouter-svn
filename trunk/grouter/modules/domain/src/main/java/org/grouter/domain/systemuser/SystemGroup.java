package org.grouter.domain.systemuser;

import java.io.Serializable;


/**
 * A user representation in the domain model.
 */

public class SystemGroup implements Serializable
{
    private Long id ;
    private String groupName ;

    public SystemGroup()
    {
    }

    private void setId (Long id)
    {
        this.id = id ;
    }

    public Long getId()
    {
        return id ;
    }

    public void setGroupName (String groupName)
    {
        if (groupName == null)
        {
            throw new NullPointerException ("Group groupName must be non-null!") ;
        }
        this.groupName = groupName ;

    }

    public String getGroupName()
    {
        return groupName ;
    }
}

