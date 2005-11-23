package org.grouter.domain.dao.hibernate;

import org.hibernate.*;
import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.Message;
import org.grouter.domain.servicelayer.dto.SystemUserDTO;
import org.grouter.domain.systemuser.SystemUser;
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

    public SystemUserDAOHibernate()
    {
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

    public void createSystemUser(SystemUserDTO systemUserDTO)
    {
  
    }
}
