package org.grouter.presentation.controller.security;


import org.acegisecurity.userdetails.UserDetails;


/**
 * Extension interface for UserDetails to provide methods for retrieving information from the inmemory
 * security context after authentication. Methods represent operations on domain model.
 *
 * The interface also exposes methods for handling a user alias. Typically an administrator role may opt to use an
 * alias to be able to impersonate another user and act as if he was the user.
 *
 * @author Georges Polyzois
 */
public interface ExtendedUserDetails extends UserDetails
{
    /**
     * Set full name in domain model.
     * @return
     */
    void setModelFullName( String modelFullName );



    /**
     * Get fullname of user in domain model.
     * @return
     */
    String getModelFullName(  );



    /**
     * Set user name as defined in domain model.
     * @return
     */
    void setModelUserName( String modelUserName );



    /**
     * Get user name as defined in doain model.
     * @return
     */
    String getModelUserName(  );



    /**
     * Get alias id. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     * @return
     */
    Long getAliaUserId(  );



    /**
     * Set alias. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     * @param aliasId
     */
    void setAliasUserId( Long aliasId );



    /**
     * Get the alias username. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     * @return
     */
    public String getAliasUserName(  );



    /**
     * Set the alias user name. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     *
     * @param aliasUserName
     */
    public void setAliasUserName( String aliasUserName );
}
