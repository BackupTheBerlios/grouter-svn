package org.grouter.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import java.util.HashSet;
import java.util.Calendar;



/**
 * Domain class.
 * @Author Georges Polyzois
 */
//@SuppressWarnings({"PersistenceModelORMInspection"})
//@Entity
public class SystemUser extends User implements Serializable
{                                    
    private Long id ;
    private String name;
    // No duplicate elements and the ordering is not relevant for us -> Set
    private Set<SystemGroup> groups = new HashSet();
    // No duplicate elements and the ordering is not relevant for us -> Set
    private Set<Password> passwords = new HashSet();
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
        setValidFrom(validFrom);
        setValidTo(validTo) ;
    }

    private void setId(Long id)
    {
        this.id = id ;
    }
    @Id
    @Column(name = "SYSTEMUSER_ID")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
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


    public void setPasswords (Set<Password> passwords)
    {
        this.passwords = passwords ;
    }

    @OneToOne
    public Set<Password> getPasswords()
    {
        return passwords ;
    }

    public void addPassword (Password password)
    {
        this.getPasswords().add(password);
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
