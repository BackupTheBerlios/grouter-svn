package org.grouter.domain.daolayer.ejb3;

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.systemuser.SystemUser;
import org.grouter.domain.daolayer.SystemUserDAO;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Oct 9, 2006
 * Time: 9:42:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class SystemUserDAOBean extends GenericEjb3DAO<SystemUser, Long> implements SystemUserDAO
{
    public SystemUserDAOBean()
    {
        super(Message.class);
    }

    public SystemUserDAOBean(Class persistentClass)
    {
        super(persistentClass);
    }

    public SystemUser save(SystemUser entity)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void delete(SystemUser entity)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public SystemUser createSystemUser(SystemUser systemUser)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeUser(Long id)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeUser(String userName)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean userExists(String userName)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean userExists(Long id)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
