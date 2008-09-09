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

import org.grouter.domain.entities.Job;

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
public interface JobService
{
    /**
     * Retrieve a list with all grouters available.
     *
     * @return
     */
    List<Job> findAll();

    /**
     * Find messages for this node.
     *
     * @param searchText text to use for index search
     * @return a list of {@link Job}s
     */
    List<Job> searchJobs(String searchText);

    /**
     * Stores a message - all relationships need to be inplace for persitence operation is to succeed.
     *
     * @param job a message to persist
     * @return
     */
    void save(Job job);

    /**
     * Get user.
     *
     * @param id of user
     * @return a User
     */
    Job findById(String id);


    /**
     * Delete the job permanently - and all events associated to it.
     *
     * @param id
     */
    void delete(String id);


}