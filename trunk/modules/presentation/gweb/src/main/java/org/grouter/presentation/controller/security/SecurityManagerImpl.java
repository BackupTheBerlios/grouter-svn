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

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;


/**
 *
 * @author Georges Polyzois
 */
public class SecurityManagerImpl implements SecurityManager
{
    private static Logger logger = Logger.getLogger( SecurityManagerImpl.class );
    private Authentication authentication;

    public SecurityManagerImpl(  )
    {
    }

    /**
     * See {@link SecurityManager#setAuthentication(org.acegisecurity.Authentication)}
     */
    public void setAuthentication( Authentication authentication )
    {
        this.authentication = authentication;
    }

    /**
     * See {@link SecurityManager#hasAlias()}
     * @return if principal has alias or not
     */
    public boolean hasAlias(  )
    {
        Object obj = getAuthentication(  ).getPrincipal(  );

        if ( obj instanceof ExtendedUserDetailsImpl )
        {
            ExtendedUserDetailsImpl userDetailsImpl = ( ExtendedUserDetailsImpl ) obj;

            if ( userDetailsImpl.getAliaUserId(  ) != null )
            {
                return true;
            }
        }

        return false;
    }



    /**
     * See {@link SecurityManager#getAliasId()}
     * @return alias id
     */
    public Long getAliasId(  )
    {
        //guard
        if ( ! hasAlias(  ) )
        {
            return null;
        }

        Object obj = getAuthentication(  ).getPrincipal(  );

        if ( obj instanceof ExtendedUserDetailsImpl )
        {
            ExtendedUserDetailsImpl userDetailsImpl = ( ExtendedUserDetailsImpl ) obj;

            return userDetailsImpl.getAliaUserId(  );
        }

        return null;
    }



    /**
     * See {@link SecurityManager#getAliasUserName()}
     * @return alias user name
     */
    public String getAliasUserName(  )
    {
        //guard
        if ( ! hasAlias(  ) )
        {
            return null;
        }

        Object obj = getAuthentication(  ).getPrincipal(  );

        if ( obj instanceof ExtendedUserDetailsImpl )
        {
            ExtendedUserDetailsImpl userDetailsImpl = ( ExtendedUserDetailsImpl ) obj;

            return userDetailsImpl.getAliasUserName(  );
        }

        return null;
    }





    /**
     */
    public boolean isUserInRole( String role )
    {
        Authentication authentication = getAuthentication(  );

        if ( authentication == null )
        {
            return false;
        }

        for ( GrantedAuthority authority : authentication.getAuthorities(  ) )
        {
            if ( authority.equals( role ) )
            {
                return true;
            }
        }

        return false;
    }



    public boolean isUserInRole( String[] authorizedRoles )
    {
        for ( String authorizedRole : authorizedRoles )
        {
            if ( isUserInRole( authorizedRole ) )
            {
                return true;
            }
        }

        return false;
    }



    public boolean isUserAnonymous(  )
    {
        Authentication authentication = getAuthentication(  );

        if ( authentication == null )
        {
            return false;
        }

        Object obj = getAuthentication(  ).getPrincipal(  );

        return obj instanceof String;
    }



    public String getUserId(  )
    {
        String username = null;
        Object obj = getAuthentication(  ).getPrincipal(  );

        if ( obj instanceof String )
        {
            username = ( String ) obj;
        }
        else if ( obj instanceof ExtendedUserDetailsImpl )
        {
            ExtendedUserDetailsImpl userDetailsImpl = ( ExtendedUserDetailsImpl ) obj;
            username = userDetailsImpl.getUsername(  );
        }
        return username;
    }

    /*
    public String getUserName(  )
    {
        String username = null;
        Object obj = getAuthentication(  ).getPrincipal(  );

        if ( obj instanceof String )
        {
            username = ( String ) obj;
        }
        else if ( obj instanceof UserDetails)
        {
            UserDetails userDetails = ( UserDetails ) obj;
            username = userDetails.getUsername(  );
        }
        return username;
    } */



    public String getModelFullName(  )
    {
        Object obj = getAuthentication(  ).getPrincipal(  );

        if ( obj instanceof ExtendedUserDetailsImpl )
        {
            ExtendedUserDetailsImpl userDetailsImpl = ( ExtendedUserDetailsImpl ) obj;

            return userDetailsImpl.getModelFullName(  );
        }

        return null;
    }



    public Long getUserIdAsLong(  )
    {
        Long userid = null;
        try
        {
            userid = Long.parseLong( getUserId(  ) );
        }
        catch ( NumberFormatException e )
        {
            logger.error( "User name was not of number type", e );
        }
        return userid;
    }



    /**
     * Helper.
     * @return Authentication
     */
    public Authentication getAuthentication(  )
    {
        if ( authentication == null )
        {
            SecurityContext securiyContext = SecurityContextHolder.getContext(  );
            return securiyContext.getAuthentication(  );
        }
        else
        {
            return authentication;
        }
    }
}
