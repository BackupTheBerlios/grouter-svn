package org.grouter.domain.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.common.hibernate.HibernateUtilContextAware;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Returns Hibernate-specific instances of DAOs.
 * <p/>
 * One of the responsiblities of the factory is to inject a Hibernate Session
 * into the DAOs. You can customize the getCurrentSession() method if you
 * are not using the default strategy, which simply delegates to
 * Hibernates built-in "current Session" mechanism.
 * <p/>
 * If for a particular DAO there is no additional non-CRUD functionality, we use
 * an inner class to implement the interface in a generic way. This allows clean
 * refactoring later on, should the interface implement business data access
 * methods at some later time. Then, we would externalize the implementation into
 * its own first-level class. We can't use anonymous inner classes for this trick
 * because they can't extend or implement an interface and they can't include
 * constructors.
 *
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss )
 *
 * @author Georges Polyzois
 */
public class HibernateDAOFactory extends DAOFactory
{
    private static Log log = LogFactory.getLog(HibernateDAOFactory.class);
    SessionFactory sessionFactory;

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
        if(sessionFactory == null)
        {//we have not been injected with a sessionfactory... so we get one from a util class
            log.debug("############ Using util to get session");
            return HibernateUtilContextAware.getSessionFactory().getCurrentSession();
        }
        else
        {
            log.debug("############### Using injected sessionfactory");
            return sessionFactory.getCurrentSession();
        }

    }

    // Add your DAO interfaces below here
    public MessageDAO getMessageDAO()
    {
        return new MessageDAOHibernate(getCurrentSession());
    }

    public SystemUserDAO getSystemUserDAO() {
        return new SystemUserDAOHibernate(getCurrentSession());
    }
}
