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
import org.grouter.domain.dao.UserDAO;
import org.grouter.domain.entities.User;
import org.grouter.domain.service.UserService;
import org.grouter.presentation.controller.AbstractRouterController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * A controller for User listing.
 *
 * @author Georges Polyzois
 */
public class UserListController extends AbstractRouterController
{
    private static Logger logger = Logger.getLogger(UserListController.class);
    private static final String LIST_VIEW = "user/listusers";   // jsp page
    private UserService userService;

    /**
     * Injected.
     * @param userService the service
     */
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        List<User> users = userService.findAll(UserDAO.FIND_ALL );
        logger.info( "Found users :" + users);
        map.put("users", users);
        map.put("usersSize", users.size());
        return new ModelAndView(LIST_VIEW, map);
    }
}
