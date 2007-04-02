package org.grouter.domain.entities;

import java.io.Serializable;

/**
 * @author Georges Polyzois
 */
public class UserRole implements Serializable, Comparable
{
    private Long id;
    private User user;
    private Role role;

    public UserRole()
    {
    }

    public UserRole(User user, Role role)
    {
        this.user = user;
        this.role = role;
    }

    public User getUser()
    {
        return user;
    }


    public void setUser(User user)
    {
        this.user = user;
    }


    public Role getRole()
    {
        return role;
    }


    public void setRole(Role role)
    {
        this.role = role;
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


    /**
     * Sorting inmemory using Collections.sort will do sort by name.
     *
     * @param anotherObject is a non-null Role.
     * @throws NullPointerException if anotherObject is null.
     * @throws ClassCastException   if anotherObject is not an Role object.
     */
    public int compareTo(Object anotherObject) throws ClassCastException
    {
        //optimizing
        if (this == anotherObject)
        {
            return 0;
        }

        UserRole compareTo = (UserRole) anotherObject;
        return getId().compareTo(compareTo.getId());
    }

}
