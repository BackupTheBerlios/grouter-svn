package org.grouter.domain.entitylayer;

import javax.persistence.*;


/**
 * The user account password.
 */

//@Entity
public class Password
{
    private Long id ;
    @Column
    private String password ;
    @Transient
    private SystemUser systemUser ;

    /**
     * Constructor.
     */
    public Password()
    {
    }

    /**
     * Constructor.
     * @param systemUser The SystemUser that has this password. Must be non-null.
     * @param password The users password. Must be non-null.
     */
    public Password (SystemUser systemUser, String password)
    {
        this.systemUser = systemUser;
        this.password = password;
        }

    /**
     * Getter.
     * @return The SystemUser that has this password.
     */
    public SystemUser getSystemUser()
    {
        return systemUser ;
    }


    /**
     * Getter.
     * @return The ID of this password.
     */
    public Long getId()
    {
        return id ;
    }


    /**
     * Getter.
     * @return The users password.
     */
    public String getPassword()
    {
        return password ;
    }
}

