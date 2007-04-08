package org.grouter.domain.daolayer;

import org.grouter.domain.entities.User;


/**
 * Interface for {@link User} DAO operations.
 *
 * @author Georges Polyzois
 */
public interface UserDAO extends GenericDAO<User, Long>
{

    /**
     * Mark user as deleted - does not remove the user physically from pesistent storage.
     * @param id
     */
    void markAsDeleted(Long id);
}

