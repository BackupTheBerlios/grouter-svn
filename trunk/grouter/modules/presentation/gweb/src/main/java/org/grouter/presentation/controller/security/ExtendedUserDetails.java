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

package org.grouter.presentation.controller.security;


import org.acegisecurity.userdetails.UserDetails;


/**
 * Extension interface for UserDetails to provide methods for retrieving information from the inmemory
 * security context after authentication. Methods represent operations on domain model.
 *
 * The interface also exposes methods for handling a user alias. Typically an administrator role may opt to use an
 * alias to be able to impersonate another user and act as if he was the user.
 *
 * @author Georges Polyzois
 */
public interface ExtendedUserDetails extends UserDetails
{
    /**
     * Set full name in domain model.
     * @return
     */
    void setModelFullName( String modelFullName );



    /**
     * Get fullname of user in domain model.
     * @return
     */
    String getModelFullName(  );



    /**
     * Set user name as defined in domain model.
     * @return
     */
    void setModelUserName( String modelUserName );



    /**
     * Get user name as defined in doain model.
     * @return
     */
    String getModelUserName(  );



    /**
     * Get alias id. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     * @return
     */
    Long getAliaUserId(  );



    /**
     * Set alias. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     * @param aliasId
     */
    void setAliasUserId( Long aliasId );



    /**
     * Get the alias username. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     * @return
     */
    public String getAliasUserName(  );



    /**
     * Set the alias user name. Typically an administrator role may opt to use an alias to be able to impersonate
     * another user and act as if he was the user.
     *
     * @param aliasUserName
     */
    public void setAliasUserName( String aliasUserName );
}
