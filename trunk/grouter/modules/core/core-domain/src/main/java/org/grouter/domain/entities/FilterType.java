package org.grouter.domain.entities;

/**
 * Types would be : file, jms.
 *
 * @author Georges Polyzois
 */
public class FilterType
{
    Long id;
    String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
