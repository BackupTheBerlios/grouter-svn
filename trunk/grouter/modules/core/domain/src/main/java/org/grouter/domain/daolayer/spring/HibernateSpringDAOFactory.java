package org.grouter.domain.daolayer.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Returns Hibernate-specific instances of DAOs. SPring injects a sessionfactory as per defined in SPring context files.
 *
 * @author Georges Polyzois
 */
public class HibernateSpringDAOFactory extends DAOFactory
{
    private static Log logger = LogFactory.getLog(HibernateSpringDAOFactory.class);

    /**
     * Injected into this instance from Spring.
     */
    SessionFactory sessionFactory;

    public HibernateSpringDAOFactory()
    {
    }

    /**
     * Injected.
     *
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
    public MessageDAO getMessageDAO()
    {
        return new MessageDAOImpl(getCurrentSession());
    }

    public SystemUserDAO getSystemUserDAO()
    {
        return new SystemUserDAOHibernate(getCurrentSession());
    }
}
