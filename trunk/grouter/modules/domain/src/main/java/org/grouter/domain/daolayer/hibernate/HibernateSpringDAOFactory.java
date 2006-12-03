package org.grouter.domain.daolayer.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Returns Hibernate-specific instances of DAOs.
 * 
 *
 * @author Georges Polyzois
 */
public class HibernateSpringDAOFactory extends DAOFactory
{
    private static Log logger = LogFactory.getLog(HibernateSpringDAOFactory.class);
    /** Injected into this instance from Spring. */
    SessionFactory sessionFactory;

    public HibernateSpringDAOFactory()
    {
    }

    /**
     * Used to spring in the session factory for Hibernate.
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    protected Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    // Add your DAO interfaces below here
    public org.grouter.domain.daolayer.MessageDAO getMessageDAO()
    {
        return new MessageDAOImpl(getCurrentSession());
    }

    public SystemUserDAO getSystemUserDAO()
    {
        return new SystemUserDAOHibernate(getCurrentSession());
    }
}
