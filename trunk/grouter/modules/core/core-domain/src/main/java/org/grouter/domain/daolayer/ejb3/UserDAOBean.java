package org.grouter.domain.daolayer.ejb3;

import org.grouter.domain.daolayer.UserDAO;
import org.grouter.domain.entities.User;


public class UserDAOBean extends GenericEjb3DAO<User, Long> implements UserDAO
{
    public UserDAOBean()
    {
        super(User.class);
    }

    public UserDAOBean(Class persistentClass)
    {
        super(persistentClass);
    }


    public void markAsDeleted(Long id)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
