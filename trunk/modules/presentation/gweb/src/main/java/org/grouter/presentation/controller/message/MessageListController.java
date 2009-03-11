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

package org.grouter.presentation.controller.message;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;
import org.grouter.domain.service.RouterService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A controller for Message listing.
 * 
 * @author Georges Polyzois
 */
public class MessageListController extends AbstractController
{
    private static Logger logger = Logger.getLogger(MessageListController.class);
    private static final String LIST_VIEW = "message/listmessages";

    private RouterService routerService;
                                           

    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();

        String nodeId = ServletRequestUtils.getStringParameter(request, "nodeid", null);
        String routerId = ServletRequestUtils.getStringParameter(request, "routerid", null);

        List<Router> routers = routerService.findAllDistinct();
        map.put("routers", routers);


        if (nodeId != null)
        {
            List<Message> messages = routerService.findAllMessages( nodeId);
            map.put("messages", messages);
            map.put("selectedNodeId", nodeId );

        }

        if ( routerId != null)
        {
            List<Node> nodes = routerService.findAllNodes( routerId );
            map.put("nodes", nodes);
            map.put("selectedRouterId", routerId );
        }

        // Needs to be there for view to render
        map.put( "command", new MessageSearchCommand() );


        return new ModelAndView(LIST_VIEW, map);
    }




}
