/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.domain.service.spring;

import org.grouter.domain.dao.UserDAO;
import org.grouter.domain.dao.UserRoleDAO;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.UserRole;
import org.grouter.domain.entities.UserState;
import org.grouter.domain.service.UserService;

import java.util.List;

/**
 * Handles operations on Users and user roles.
 *
 * @author Georges Polyzois
 */
public class UserServiceImpl implements UserService
{
    UserDAO userDAO;
    UserRoleDAO userRoleDAO;

    /**
     * Injected.
     *
     * @param userDAO injected DAO
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void setUserRoleDAO(UserRoleDAO userRoleDAO)
    {
        this.userRoleDAO = userRoleDAO;
    }

    public List<User> findAll()
    {
        return userDAO.findAll();
    }

    public List<User> findAll(final String hql)
    {
        return userDAO.findAll(hql);
    }

    public void save(User user)
    {
        userDAO.save(user);
    }

    public void delete(final Long id)
    {
        userDAO.delete(id);
    }

    public User findById(Long id)
    {
        return userDAO.findById(id,"address");
    }

    public void changeState(final Long id, final UserState userState)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("User id was null");
        }
        if ( !UserState.values.containsKey(userState.getId()) )
        {
            throw new IllegalArgumentException("Provided state does not exist. User state was " + userState);
        }

        User user = findById(id);

        if(  user.getUserState().equals( UserState.BLOCKED ) && userState.equals( UserState.NEW ) )
        {
            throw new IllegalStateException( "A blocked user can not be set to state new" );

        }

        user.setUserState(userState);
        save(user);
    }

    public List<User> searchUsers(String searchText)
    {
        return userDAO.findFromIndex(searchText, "userName", "id", "lastName","firstName", "address.mobilephone",
                "address.phone","address.street", "address.zip", "address.city", "address.fax", "address.homepageurl",
                "address.companyname", "address.email");
    }

    public UserRole findUserRoleById(Long id)
    {
        return userRoleDAO.findById( id );
    }

    public UserRole findUserRoleByUserIdAndRoleId(Long userid, Long roleId)
    {
        return userRoleDAO.findUserRoleByUserIdAndRoleId( userid, roleId );
    }

    public void deleteUserRoles( Long userId )
    {
        userRoleDAO.deleteUserRole( userId );
    }
}
