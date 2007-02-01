package org.grouter.domain.daolayer;

import org.grouter.domain.entities.SystemUser;


public interface SystemUserDAO extends GenericDAO<SystemUser, Long>
{
    SystemUser createSystemUser(SystemUser systemUser);

    void removeUser(Long id);

    void removeUser(String userName);

    boolean userExists(String userName);

    boolean userExists(Long id);

/*
public void createSystemUser(String userName, String password, String fullName, String description,
                       Calendar validFrom, Calendar validTo, boolean active, boolean temporaryPassword,
                       int loginRetries, Calendar passwordLastChanged) ;

public void createUserAccount(String userName, String password, String fullName, String description,
                            Calendar validFrom, Calendar validTo, boolean active, boolean temporaryPassword,
                            int loginRetries, Calendar passwordLastChanged, Role[] roles) ;


public UserInfo[] getUserAccountInfo() ;
public UserInfo getUserAccountInfo(String userName) throws Exception;
public void setUserAccountInfo (UserAccountInfo info) throws Exception ;
public UserInfo getUserAccountInfo(Long userId) throws Exception ;
public void removeUser (Long id) throws Exception ;
public void removeUser (String userName) throws Exception ;
public boolean userExists (String userName) throws Exception ;
public boolean userExists (Long id) throws Exception ;
*/

}

