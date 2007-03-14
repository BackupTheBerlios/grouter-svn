package org.grouter.presentation.controller.security;


import org.acegisecurity.Authentication;


/**
 * Interface for operations on the Acegi Secutiry Context inmemory.
 *
 * @author Georges Polyzois
 */
public interface SecurityManager
{

    /**
     * Check if user has a certain role.
     * @param role  check to see if this role is among the roles of the current user
     * @return true if user has role
     */
    boolean isUserInRole( String role );



    boolean isUserInRole( String[] authorizedRoles );



    /**
     * Check to see if current user autheticated is of type anonymous.
     * @return true if user not autheticated yet
     */
    boolean isUserAnonymous(  );



    /**
     * Get username as defined by Acegi.
     * @return
     */
    String getUserName(  );



    /**
     * Get fullname if any, else a null i sreturne.
     * @return fullName as definede by domain
     */
    String getModelFullName(  );



    /**
     * Helper method that returns the id of the logged in user as a Long - if failure to retrieve the user or
     * a user with a non number representation found a null is returned.
     * @return
     */
    Long getModelUserIdAsLong(  );



    /**
     * The user id corresponds to the domain models {@link  org.grouter.domain.entities.User}.
     * @return
     */
    String getModelUserId(  );



    /**
     * For injection and unit testing.
     * @param authentication a {@link org.acegisecurity.Authentication} instance
     */
    void setAuthentication( Authentication authentication );



    /**
     * Get authentication instance.
     * @return  an {@link org.acegisecurity.Authentication} instance
     */
    Authentication getAuthentication(  );



    /**
     * Does current authenitcated user also an alias, i.e. he/she has selected and additional user to impersonate
     * and act as if the same.
     * @return
     */
    boolean hasAlias(  );



    /**
     * Get the alias id.
     * @return
     */
    Long getAliasId(  );



    /**
     * Get the alias for authenitcated user impersonating an other user.
     * @return
     */
    String getAliasUserName(  );
}
