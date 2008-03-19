package org.grouter.domain.dao;

import org.grouter.domain.entities.UserRole;

/**
 * Maps User to Roles and vice versa.
 * 
 * @author  Georges Polyzois
 */
public interface UserRoleDAO extends GenericDAO<UserRole, Long>
{
    UserRole findUserRoleByUserIdAndRoleId( Long userid, Long roleId );

    void deleteUserRole( Long userId );
}
