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


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.acegisecurity.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Controller used for redirecting an authenticated user or non authenticated user to a resource.
 * Normally if a user tries to access a certain url he/she will be presented with a login form
 * as specified by the login attribute of this class. Upon a succesfull authentication the user
 * will be forwarded to the resource url.
 *
 * If an unsuccesfull attempt is made then the user is redirected to a login denied form as specified
 * by the loginDenied attribute.
 *
 * When user logs out the session data are removed and they are redirected to the loggedOut url.
 *
 * @author Georges Polyzois
 */
public class SecurityController extends MultiActionController
{
    private String login;
    private String loggedOut;
    private String loginDenied;


    public void setLogin( String login )
    {
        this.login = login;
    }


    public void setLoggedOut(String loggedOut)
    {
        this.loggedOut = loggedOut;
    }

    public void setLoginDenied( String loginDenied )
    {
        this.loginDenied = loginDenied;
    }

    public ModelAndView login( HttpServletRequest request, HttpServletResponse response )
            throws Exception
    {
        return new ModelAndView( this.login );
    }

    public ModelAndView logedout( HttpServletRequest request, HttpServletResponse response )
            throws Exception
    {
        SecurityContextHolder.clearContext();

        return new ModelAndView( this.loggedOut);
    }

    /**
     * Correspond to a call to /context/security/logindenied.do
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView logindenied( HttpServletRequest request, HttpServletResponse response )
            throws Exception
    {
        //SecurityContextHolder.clearContext();

        return new ModelAndView( this.loginDenied);
    }
}
