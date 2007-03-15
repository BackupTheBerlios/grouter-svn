package org.grouter.presentation.controller.user;

import org.grouter.domain.entities.User;

/**
 * @author Georges Polyzois
 */
public class UserCommand
{
    User user;

    public UserCommand(User user)
    {
        this.user = user;
    }

    public UserCommand()
    {
        
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
