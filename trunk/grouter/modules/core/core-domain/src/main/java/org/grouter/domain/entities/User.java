package org.grouter.domain.entities;


import java.util.HashSet;
import java.util.Set;


/**
 * User entity.
 *
 * @author Georges Polyzois
 */
public class User
{
    private String extUserId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private User createdBy;
    private Address address;
    private Set<UserRole> roles = new HashSet<UserRole>();

    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public String getEmail()
    {
        return email;
    }


    public boolean isSuperReviewer()
    {
        return roles.contains(Role.SUPER_REVIEWER);
    }


    public boolean isAdmin()
    {
        return roles.contains(Role.ADMIN);
    }


    public boolean isReviewer()
    {
        return roles.contains(Role.REVIEWER);
    }


    public User getCreatedBy()
    {
        return createdBy;
    }


    public void setCreatedBy(User createdBy)
    {
        this.createdBy = createdBy;
    }


    public Set<UserRole> getRoles()
    {
        return roles;
    }


    public void setRoles(Set<UserRole> roles)
    {
        this.roles = roles;
    }



    public String getExtUserId()
    {
        return extUserId;
    }


    public void setExtUserId(String extUserId)
    {
        this.extUserId = extUserId;
    }


    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
