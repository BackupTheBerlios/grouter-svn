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

package org.grouter.domain.servicelayer.spring;

import org.grouter.domain.servicelayer.UserService;
import org.grouter.domain.entities.User;
import org.grouter.domain.daolayer.UserDAO;

import java.util.List;

/**
 * Handles operations on Users and user roles.
 *
 * @author Georges Polyzois
 */
public class UserServiceImpl implements UserService
{
    UserDAO userDAO;

    /**
     * Injected.
     * @param userDAO injected DAO
     */
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

    public void deleteUser(Long id)
    {
        userDAO.markAsDeleted( id );
    }
}
