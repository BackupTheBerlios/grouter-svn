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

package org.grouter.domain.servicelayer.spring.logging;

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.servicelayer.RouterService;

/**
 * @author Georges Polyzois
 */
public class JDBCLogStrategyImpl implements LogStrategy
{
    RouterService routerService;


    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }

    public void log(Message message)
    {
        routerService.saveMessage(message);

    }


    public void log(Node node)
    {
        routerService.saveNode(node);
    }

}
