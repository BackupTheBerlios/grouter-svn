package org.grouter.domain.servicelayer;

import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.User;

import javax.ejb.Remote;
import javax.ejb.Local;
import java.util.List;

/**
 * Main interface for operations with the grouter internal domain.
 *
 * There are a spring based implementation and
 *
 * @author Georges Polyzois
 */
@Remote
@Local
public interface UserService
{
    /**
     * Retrieve a list with all grouters available.
     * @return
     */
    List<User> findAll();


    /**
     * Stores a message - all relationships need to be inplace for persitence operation is to succeed.
     * @param user a message to persist
     * @return
     */
    void saveUser(User user);

    /**
     * Get user.
     *
     * @param id of user
     * @return a User
     */
    User findById(Long id);
}
