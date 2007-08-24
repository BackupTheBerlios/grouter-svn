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

package org.grouter.presentation.controller.user;
import org.apache.commons.lang.Validate;

import org.apache.log4j.Logger;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.validation.BindException;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.grouter.domain.servicelayer.UserService;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.Role;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Handles the edit form for a Node.
 *
 * @author Georges Polyzois
 */
public class UserEditController extends SimpleFormController
{
    private static Logger logger = Logger.getLogger( UserEditController.class );
    private final static String ID = "id";
    private static final String FORMVIEW = "user/edituser";
    private static final String SUCCESSVIEW = "redirect:list.do";
    private static final String USER = "usercommand";

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    private UserService userService;


    /**
     * Prefered way of init these settings since they are static.
     */
    public UserEditController(  )
    {
        setSessionForm( true );
        setCommandClass( UserCommand.class );
        setFormView( FORMVIEW );
        setSuccessView( SUCCESSVIEW );
        setCommandName( USER );
        //setValidator();
    }


    /**
     * Called on submit - stores a User.
     */
    @Override
    protected ModelAndView onSubmit( HttpServletRequest req, HttpServletResponse res, Object object, BindException bex )
            throws Exception
    {
        String message;
        UserCommand cmd = ( UserCommand ) object;


        User user = cmd.getUser(  );

        try
        {
            userService.saveUser( user );
            message = "Saved";
        }
        catch ( Exception e )
        {
            logger.error( e, e );
            message = "Could not save";
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( "message", message );

        //return showForm( req, res, bex, model );
        return new ModelAndView( SUCCESSVIEW, model );
    }





    /**
     * Callback method for intializing command/form bean.
     */
    @Override
    protected Object formBackingObject( HttpServletRequest request )
            throws Exception
    {
        UserCommand cmd;
        Long id = getId( request, ID);
        logger.debug("Get for id : " + id);

        if ( id != null )
        {
            User user = userService.findById( id );
            cmd = new UserCommand( user );
        }
        else
        {
            cmd = new UserCommand(  );
        }

        return cmd;
    }



    /**
     * Helper.
     * @param request  a HttpServletRequest
     * @param id   an id
     * @return an id
     */
    private Long getId( HttpServletRequest request, String id )
    {
        if ( ( request != null ) && ( request.getParameter( id ) != null ) )
        {
            try
            {
                return ServletRequestUtils.getLongParameter( request, id );
            }
            catch ( ServletRequestBindingException e )
            {
                logger.error( "Could not get id from request - probably not a valid Long", e );
            }
        }

        return null;
    }



    /**
     * Data needed by view.
     * <p/>
     * {@inheritDoc}
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    protected Map referenceData( HttpServletRequest request )
            throws Exception
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        List<Role> allRoles = Role.values();

        List<User> users = userService.findAll();
        model.put( "users", users );
        model.put( "roles", allRoles );

        return model;
    }

}