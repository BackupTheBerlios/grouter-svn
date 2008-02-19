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

package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.servicelayer.RouterService;

import javax.ejb.Local;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 25, 2006
 * Time: 5:37:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface RouterLocalService extends RouterService
{
    public static final String DOMAIN_GROUTER_BEAN_LOCAL = "domain/GRouterBeanService/local";

    /**
     * Persist into domain and broadcast to clients on a topic to inform of new event.
     * @param message
     */
}
