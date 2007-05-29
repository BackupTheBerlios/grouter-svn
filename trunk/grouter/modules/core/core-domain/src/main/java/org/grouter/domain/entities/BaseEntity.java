package org.grouter.domain.entities;

import org.hibernate.LazyInitializationException;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Base class for Domain entities. 
 *
 * @author Georges Polyzois
 */
public class BaseEntity implements Serializable
{

    /**
     * A convenience method for outputting all attributes in the instances' state.
     *
     * @return a string with name, value pairs, {@link org.apache.commons.lang.builder.ToStringBuilder#reflectionToString(Object)}
     * @throws LazyInitializationException if the entity was not fully initialized, eg. if you try
     * to print out state of instance while decoupled and instance was not loaded completely
     */
    public String reflectionToString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
