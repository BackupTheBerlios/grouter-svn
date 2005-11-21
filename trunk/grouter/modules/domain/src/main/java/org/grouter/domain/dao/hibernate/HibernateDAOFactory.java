package org.grouter.domain.dao.hibernate;

import org.hibernate.Session;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.common.hibernate.HibernateUtilContextAware;

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
    protected Session getCurrentSession()
    {
        return HibernateUtilContextAware.getSessionFactory().getCurrentSession();
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
