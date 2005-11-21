package org.grouter.domain.systemuser;

import java.io.Serializable;
import java.util.Set;

import java.util.HashSet;
import java.util.Calendar;


/**
 * A user representation in the domain model.
 */
public class SystemUser implements Serializable
{
    private Long id ;
    private String name;
    private Set groups ;
    private Set passwords ;
    private Password currentPassword ;
    private String fullName ;
    private String description ;
    private boolean active ;
    private int loginRetries ;
    private Calendar validFrom ;
    private Calendar validTo ;

    public SystemUser()
    {
    }

    public SystemUser (String name, String fullName, String description, boolean active, int loginRetries, Calendar validFrom, Calendar validTo)
    {
        setName (name) ;
        setFullName (fullName) ;
        setDescription (description) ;
        setActive (active) ;
        setLoginRetries(loginRetries) ;
        passwords = new HashSet() ;
        groups = new HashSet() ;
        setValidFrom(validFrom);
        setValidTo(validTo) ;
    }

    private void setId(Long id)
    {
        this.id = id ;
    }
    public Long getId()
    {
        return id ;
    }
    public void setCurrentPassword (Password currentPassword)
    {
        this.currentPassword = currentPassword ;
    }

    public Password getCurrentPassword()
    {
        return currentPassword ;
    }


    public void setPasswords (Set passwords)
    {
        this.passwords = passwords ;
    }

    public Set getPasswords()
    {
        return passwords ;
    }

    public void addPassword (Password password)
    {
        passwords.add(password) ;
    }
    public void setName (String name)
    {
        this.name = name ;
    }
    public String getName()
    {
        return name ;
    }

    public void setGroups (Set groups)
    {
        this.groups = groups ;
    }

    public Set getGroups ()
    {
        return groups ;
    }

    public void addGroup (SystemGroup group)
    {
        groups.add(group) ;
    }

    public void setFullName (String fullName)
    {
        this.fullName = fullName ;
    }
    public String getFullName()
    {
        return fullName ;
    }
    public void setDescription (String description)
    {
        this.description = description ;
    }
    public String getDescription()
    {
        return description ;
    }
    public void setActive (boolean active)
    {
        this.active = active ;
    }
    public boolean getActive()
    {
        return active ;
    }
    public void setLoginRetries(int loginRetries)
    {
        this.loginRetries = loginRetries ;
    }
    public int getLoginRetries()
    {
        return loginRetries ;
    }
    public void setValidFrom (Calendar validFrom)
    {
        this.validFrom = validFrom ;
    }
    public Calendar getValidFrom()
    {
        return validFrom ;
    }
    public void setValidTo (Calendar validTo)
    {
        this.validTo = validTo ;
    }
    public Calendar getValidTo()
    {
        return validTo ;
    }
}
