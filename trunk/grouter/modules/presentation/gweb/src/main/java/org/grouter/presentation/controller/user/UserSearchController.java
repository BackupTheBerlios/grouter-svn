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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.grouter.domain.entities.User;
import org.grouter.domain.service.UserService;
import org.grouter.presentation.controller.message.MessageListController;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Jan 19, 2008
 * Time: 5:30:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserSearchController extends AbstractController
{
    private static Logger logger = Logger.getLogger(MessageListController.class);
    private static final String LIST_VIEW = "user/searchusers";

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        String searchText = ServletRequestUtils.getStringParameter(request, "searchText", null);

        if (StringUtils.isNotEmpty(searchText)) {
            List<User> users = userService.searchUsers(searchText);
            map.put("users", users);
        }

        return new ModelAndView(LIST_VIEW, map);
    }

}