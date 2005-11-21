package org.grouter.domain.systemuser;



/**
 * The user account password.
 */

public class Password
{
    /** The ID of this password. */
    private Long id ;
    /** The password itself. */
    private String password ;
    /** A reference to the SystemUser that has this password. */
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
        setPassword(password) ;
        setSystemUser(systemUser) ;
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
     * Setter.
     * @param systemUser The SystemUser that has this password. Must be non-null.
     */
    public void setSystemUser (SystemUser systemUser)
    {
        this.systemUser = systemUser ;
    }

    /**
     * Setter.
     * @param id The ID of this password.
     */
    public void setId (Long id)
    {
        this.id = id ;
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
     * Setter.
     * @param password The users password. Must be non-null.
     */
    public void setPassword (String password)
    {
        this.password = password ;
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

