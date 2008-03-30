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


import org.acegisecurity.GrantedAuthority;

import org.acegisecurity.userdetails.User;


/**
 * See interface for doc - {@link ExtendedUserDetails}
 *
 * @author Georges Polyzois
 */
public class ExtendedUserDetailsImpl extends User implements ExtendedUserDetails
{
    private String modelFullName;
    private String modelUserName;
    private String aliasUserName;
    private Long aliasUserId;

    /**
     * Uses super constructor in User.
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     * @throws IllegalArgumentException
     */
    public ExtendedUserDetailsImpl( String username, String password, boolean enabled, boolean accountNonExpired,
        boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[] authorities )
            throws IllegalArgumentException
    {
        super( username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities );
    }



    /**
     * Uses super constructor in User.
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     * @throws IllegalArgumentException
     */
    public ExtendedUserDetailsImpl( String username, String password, boolean enabled, boolean accountNonExpired,
        boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[] authorities, Long aliasId )
            throws IllegalArgumentException
    {
        super( username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities );
        this.aliasUserId = aliasId;
    }



    /**
     * {@link ExtendedUserDetails#getAliasUserName()}
     * @return
     */
    public String getAliasUserName(  )
    {
        return aliasUserName;
    }



    /**
     * {@link ExtendedUserDetails#setAliasUserName(String)}
     * @param impersonatedUserName
     */
    public void setAliasUserName( String impersonatedUserName )
    {
        this.aliasUserName = impersonatedUserName;
    }



    /**
     * {@link ExtendedUserDetails#getModelUserName()}
     * @return
     */
    public String getModelUserName(  )
    {
        return modelUserName;
    }



    /**
     * {@link ExtendedUserDetails#getAliaUserId()}
     * @return
     */
    public Long getAliaUserId(  )
    {
        return aliasUserId;
    }



    /**
     * {@link ExtendedUserDetails#setAliasUserId(Long)}
     * @param dealerId
     */
    public void setAliasUserId( Long dealerId )
    {
        this.aliasUserId = dealerId;
    }



    /**
     * {@link ExtendedUserDetails#setModelUserName(String)}
     * @param modelUserName
     */
    public void setModelUserName( String modelUserName )
    {
        this.modelUserName = modelUserName;
    }



    /**
     * {@link ExtendedUserDetails#getModelFullName()}
     * @return
     */
    public String getModelFullName(  )
    {
        return modelFullName;
    }



    /**
     * {@link ExtendedUserDetails#setModelFullName(String)}
     * @param modelFullName
     */
    public void setModelFullName( String modelFullName )
    {
        this.modelFullName = modelFullName;
    }
}

