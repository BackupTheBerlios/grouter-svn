package org.grouter.domain.daolayer.hibernate;

import org.hibernate.*;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.entitylayer.Message;
import org.grouter.domain.entitylayer.SystemUser;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Hibernate-specific implementation of the <tt>SystemUserDAO</tt>
 * non-CRUD data access object.
 *
 */
public class SystemUserDAOHibernate extends GenericHibernateDAO<SystemUser, Long> implements SystemUserDAO
{
    Logger logger = Logger.getLogger(SystemUserDAOHibernate.class);

    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public SystemUserDAOHibernate()
    {
        super(SystemUser.class);
    }

    public SystemUserDAOHibernate(Session session)
    {
        super(SystemUser.class, session);
    }

    @SuppressWarnings("unchecked")
    public List<Message> findConcrete(Class concreteClass)
    {
        return getSession().createCriteria(concreteClass).list();
    }

    public SystemUser createSystemUser(SystemUser systemUser)
    {
        return save(systemUser);
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

    protected void setSession(Session s)
    {
        session = s;
    }
}
