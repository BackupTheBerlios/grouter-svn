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

package org.grouter.core.util;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.grouter.core.RemoteRouterService;
import org.grouter.core.RouterServerImpl;

/**
 * A {@link RmiServiceExporter} configuration is static. This class delegates to
 * {@link RmiServiceExporter} and implements a factory interface in spring to be able
 * to init ports before calling prepare() on RmiServiceExporter - which registers
 * the our RemoteRouterService in jndi.
 *
 * Another option would be to extend RmiServiceExporter and override afterPropertiesSet so
 * that prepare is not called until we explicitly say so.
 *
 * @author Georges Polyzois
 */
public class RmiServiceExporterFactoryBean implements FactoryBean
{
    RouterServerImpl routerServer;
    RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
    private static final String REMOTE_ROUTER_SERVICE = "RemoteRouterService";


    public Object getObject() throws Exception
    {
        rmiServiceExporter.setServiceName(REMOTE_ROUTER_SERVICE);
        rmiServiceExporter.setServiceInterface(RemoteRouterService.class);
        rmiServiceExporter.setService(routerServer);
        return rmiServiceExporter;
    }

    public Class getObjectType()
    {
        return RmiServiceExporter.class;
    }

    public boolean isSingleton()
    {
        return true;
    }

    /**
     * Injected.
     * @param routerServer
     */
    public void setRouterServer(final RouterServerImpl routerServer)
    {
        this.routerServer = routerServer;
    }
}
