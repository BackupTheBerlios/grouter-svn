package org.grouter.domain.daolayer.spring;

import org.apache.log4j.Logger;
import org.grouter.domain.daolayer.UserDAO;
import org.grouter.domain.entities.User;
import org.hibernate.Session;

import java.util.List;

/**
 * Hibernate-specific implementation of the {@link UserDAO} interface.
 *
 * @author Georges Polyzois
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


    public void markAsDeleted(Long id)
    {
        User user = findById( id );
        user.setDeleted( true );
    }
}
