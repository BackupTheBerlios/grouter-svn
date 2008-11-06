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
package org.grouter.presentation.gwt.server;

import org.grouter.domain.entities.Message;
import org.grouter.domain.service.RouterService;
import org.grouter.presentation.gwt.client.GWTRemoteService;
import org.gwtwidgets.server.spring.GWTSpringController;

import java.util.ArrayList;
import java.util.List;


/**
 * Server side implementation.
 *
 * @author Georges Polyzois
 */
//public class GWTRemoteServiceImpl     extends RemoteServiceServlet implements GWTRemoteService
public class GWTRemoteServiceImpl extends GWTSpringController implements GWTRemoteService
{
    private RouterService routerService;
    public String getMessages(Integer nodeId)
    {
        List<Message> messages = new ArrayList<Message>();

        Message entity = new Message("A messae");
        Message entity2 = new Message("A messae");

        messages.add( entity );
        messages.add( entity2 );

        return "messages";
    }

    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }
}