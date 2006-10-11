package org.grouter.domain.entities.systemuser;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import java.io.Serializable;


/**
 * A user representation in the domain model.
 */

@Entity
public class SystemGroup implements Serializable
{
    @Id
    private Long id ;
    @Column
    private String groupName ;

    public SystemGroup()
    {
    }


    public Long getId()
    {
        return id ;
    }
}

