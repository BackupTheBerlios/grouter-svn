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

package org.grouter.domain.daolayer;

import org.grouter.domain.entities.Node;

import java.util.List;

/**
 * Business DAO operations related to the <tt>Node</tt> entity.
 *
 * @author Georges Polyzois
 */
public interface NodeDAO extends GenericDAO<Node, String>
{
    /**
     * Use to get all all nodes for a given router with number of messages set on each node.
     *
     * @param routerId get statistics for this router
     * @return list with Nodes and number of messages for every node
     */
    List<Node> findNodesWithNumberOfMessages(String routerId);


    Long getNumberOfMessages(String nodeId);

    /**
     * Find all nodes for a given routerid and init endpoint if set to true.
     *
     * @param routerId     id of router
     * @param initEndPoint should we also load endpoints
     * @return list with nodes
     */
    List<Node> findAllNodes(String routerId, boolean initEndPoint);

    List<Node> findNodes();


}