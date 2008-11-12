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

import org.apache.log4j.Logger;
import org.grouter.domain.entities.*;
import org.grouter.domain.service.UserService;
import org.grouter.presentation.controller.security.SecurityManagerImpl;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Handles the edit form for a Node.
 *
 *
 *
 * @author Georges Polyzois
 */
public class UserEditController extends SimpleFormController
{
    private static Logger logger = Logger.getLogger(UserEditController.class);
    private final static String ID = "id";
    private static final String FORMVIEW = "user/edituser";
    private static final String SUCCESSVIEW = "redirect:list.do";
    // This is used in the view for spring:bind
    private static final String USEREDITCOMMAND = "usereditcommand";
    private org.grouter.presentation.controller.security.SecurityManager securityManager = new SecurityManagerImpl();
    private UserService userService;
    private UserEditCommandValidator userEditCommandValidator = new UserEditCommandValidator();


    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }


    /**
     * Prefered way of init these settings since they are static.
     */
    public UserEditController()
    {
        setSessionForm(true);
        setCommandClass(UserEditCommand.class);
        setFormView(FORMVIEW);
        setSuccessView(FORMVIEW);
        setCommandName(USEREDITCOMMAND);
        setValidator(userEditCommandValidator);
    }


    /**
     * Called on submit - stores a User.
     */
    @Override
    protected ModelAndView onSubmit(HttpServletRequest req, HttpServletResponse res, Object object, BindException bex)
            throws Exception
    {
        // Get the user id of logged on user
        Long logedOnUserId = securityManager.getUserIdAsLong();
        User loggedOnUser = userService.findById( logedOnUserId );

        String message;
        UserEditCommand cmd = (UserEditCommand) object;
        User user = cmd.getUser();
        user.setUserState(UserState.NEW);
        AuditInfo auditInfo = user.getAuditInfo();
        auditInfo.setCreatedBy( loggedOnUser.getId() );
        auditInfo.setModifiedBy( loggedOnUser.getId() );
        auditInfo.setModifiedOn( new Date() );
        //user.setAuditInfo( auditInfo  );

        Set<UserRole> userRoles = user.getUserRoles();
        for (Iterator<UserRole> userRoleIterator = userRoles.iterator(); userRoleIterator.hasNext();)
        {
            UserRole userRole = userRoleIterator.next();
            userRole.setUser( user );
        }          

        try
        {
            // delete                             
            //userService.deleteUserRoles( user.getId() );
            userService.save(user);
            message = "Saved";
        }
        catch (Exception e)
        {
            logger.error(e, e);
            message = "Could not save " + e.getMessage();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("message", message);

        // to same view return showForm( req, res, bex, model );
        return new ModelAndView(SUCCESSVIEW, model);
    }

    /**
     * Callback method for intializing command/form bean.
     */
    @Override
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception
    {
        final UserEditCommand cmd;
        final Long id = getId(request, ID);
        logger.debug("Get for id : " + id);
                                                    
        if (id != null)
        {
            // Edit existing one
            User user = userService.findById(id);
            cmd = new UserEditCommand(user);
        } else
        {
            // New User
            cmd = new UserEditCommand();
        }
        return cmd;
    }


    /**
     * Helper.
     *
     * @param request a HttpServletRequest
     * @param id      an id
     * @return an id
     */
    private Long getId(HttpServletRequest request, String id)           
    {
        if ((request != null) && (request.getParameter(id) != null))
        {
            try
            {
                return ServletRequestUtils.getLongParameter(request, id);
            }
            catch (ServletRequestBindingException e)
            {
                logger.error("Could not get id from request - probably not a valid Long", e);
            }
        }

        return null;
    }


    /**
     * Data needed by view. Her we need the list of available roles.
     *
     * @param request
     * @return a map with reference data
     * @throws Exception
     */
    @Override
    protected Map referenceData(HttpServletRequest request)
            throws Exception
    {
        Map<String, Object> model = new HashMap<String, Object>();
        List<Role> allRoles = Role.values();
        model.put("allroles", allRoles);
        return model;
    }


    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
    {

        final Long userId;
        try
        {
            userId = ServletRequestUtils.getLongParameter( request, "id");
        } catch (ServletRequestBindingException e)
        {
            // ignore
            return;
        }

        binder.registerCustomEditor(Set.class, new CustomCollectionEditor(Set.class)
        {
            protected Object convertElement(Object element)
            {
                if (element != null )
                {
                    Long roleId = new Long((String) element);
                    UserRole userRole = userService.findUserRoleByUserIdAndRoleId(userId, roleId);
                    if(userRole != null)
                    {
                       return userRole;
                    }
                    else
                    {
                        User user = new User();
                        user.setId( userId );
                        return new UserRole( user, Role.valueOf( roleId ) );
                    }
                }
                return null;
            }
        });
    }

}