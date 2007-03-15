package org.grouter.domain.daolayer.spring;

import org.apache.log4j.Logger;
import org.grouter.domain.daolayer.UserDAO;
import org.grouter.domain.daolayer.spring.GenericHibernateDAO;
import org.grouter.domain.entities.User;
import org.hibernate.Session;

/**
 * Hibernate-specific implementation of the <tt>SystemUserDAO</tt>
 * non-CRUD data access object.
 */
public class UserDAOImpl extends GenericHibernateDAO<User, Long> implements UserDAO
{
    Logger logger = Logger.getLogger(UserDAOImpl.class);

    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public UserDAOImpl()
    {
        super(User.class);
    }

    public UserDAOImpl(Session session)
    {
        super(User.class, session);
    }


    protected void setSession(Session s)
    {
        session = s;
    }

}
