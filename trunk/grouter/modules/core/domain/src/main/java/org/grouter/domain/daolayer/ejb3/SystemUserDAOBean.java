package org.grouter.domain.daolayer.ejb3;

import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.entities.SystemUser;


public class SystemUserDAOBean extends GenericEjb3DAO<SystemUser, Long> implements SystemUserDAO
{
    public SystemUserDAOBean()
    {
        super(SystemUser.class);
    }

    public SystemUserDAOBean(Class persistentClass)
    {
        super(persistentClass);
    }

    public SystemUser save(SystemUser entity)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void delete(String id)
    {
        delete( new Long(id) );
    }

}
