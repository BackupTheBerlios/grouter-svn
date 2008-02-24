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

package org.grouter.domain.service;

import org.grouter.domain.entities.User;
import org.grouter.domain.entities.UserState;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;

/**
 * Main interface for operations with the grouter internal domain.
 * <p/>
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
     *
     * @return
     */
    List<User> findAll();

    /**
     * Retrieve a list with all grouters available.
     *
     * @return
     */
    List<User> findAll(String hql);


    /**
     * Stores a user - all relationships need to be inplace for persitence operation is to succeed.
     *
     * @param user a user to persist
     * @return
     */
    void save(User user);

    /**
     * Deletes a user.
     *
     * @param id the id of the user to delete
     */
    void delete(Long id);

    /**
     * Get user with id.
     *
     * @param id of user
     * @return a User
     */
    User findById(Long id);

    /**
     * Change the state of this user - {@link UserState}
     *
     * @param id        id of user
     * @param userState the new state for this user
     */
    void changeState(Long id, UserState userState);


    /**
     * Find messages for this node.
     *
     * @param searchText text to use for index search
     * @return a list of {@link User}s
     */
    List<User> searchUsers(String searchText);


}
