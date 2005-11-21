package org.grouter.domain.servicelayer.dto;

/**
 * Class description.
 */
public class SystemUserDTO
{
    String name;
    String lastName;

    public SystemUserDTO(String name, String lastName, String password)
    {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    String password;
}
