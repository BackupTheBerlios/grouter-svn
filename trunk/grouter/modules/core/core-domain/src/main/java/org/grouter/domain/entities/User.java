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
    private Long id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private User createdBy;
    private Address address;
    private Set<UserRole> roles = new HashSet<UserRole>();


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
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


    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
