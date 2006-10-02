package org.grouter.domain.dao.ejb3;

import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.dao.hibernate.MessageDAOHibernate;
import org.grouter.domain.dao.hibernate.SystemUserDAOHibernate;
import org.grouter.common.hibernate.HibernateUtilContextAware;
import org.hibernate.Session;

public class Ejb3DAOFactory extends DAOFactory
{

    protected Session getCurrentSession()
    {
        return HibernateUtilContextAware.getSessionFactory().getCurrentSession();
    }

    // Add your DAO interfaces below here
    public MessageDAO getMessageDAO()
    {
        return new MessageDAOHibernate(getCurrentSession());
    }

    public SystemUserDAO getSystemUserDAO()
    {
        return new SystemUserDAOHibernate(getCurrentSession());
    }
}
