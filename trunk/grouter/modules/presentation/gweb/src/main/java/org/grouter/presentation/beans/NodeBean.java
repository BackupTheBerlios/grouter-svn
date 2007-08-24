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

package org.grouter.presentation.beans;

import org.grouter.domain.entities.Node;
import org.grouter.domain.servicelayer.RouterService;
import org.apache.log4j.Logger;

/**
 * @author Georges Polyzois
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class NodeBean
{
    private static Logger logger = Logger.getLogger(NodeBean.class);
    private Node node;
    private RouterService grouterService;
    private String id;
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    public void setGrouterService(RouterService grouterService)
    {
        this.grouterService = grouterService;
    }

    public String submitSave()
    {
        logger.info("###############  Submitting form");
        return "sucess";

    }


    public Node getNode()
    {
        return node;
    }

    public void setNode(Node node)
    {
        this.node = node;
    }
}
