package org.grouter.domain.servicelayer.spring;

import org.grouter.domain.servicelayer.UserService;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.User;
import org.grouter.domain.daolayer.UserDAO;

import java.util.List;

/**
 * @author Georges Polyzois
 */
public class UserServiceImpl implements UserService
{
    UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public List<User> findAll()
    {
        return userDAO.findAll();
    }

    public void saveUser(User user)
    {
        userDAO.save( user );
    }

    public User findById(Long id)
    {
        return userDAO.findById( id );
    }
}
