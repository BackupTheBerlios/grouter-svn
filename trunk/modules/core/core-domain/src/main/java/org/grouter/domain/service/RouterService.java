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

package org.grouter.domain.service;

import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;

import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Main interface for operations with the grouter service layer.
 *
 * @author Georges Polyzois
 */
@Remote
@Local
public interface RouterService
{
    final static String BEANNAME = "routerService";

    /**
     * Retrieve a list with all grouters available - loads all collections if specified in mapping.
     *
     * @return a "fully" loaded router as per mapping definition
     */
    List<Router> findAll();


    List<Router> findAllDistinct();


    /**
     * Stores a message - all relationships need to be inplace for persitence operation is to succeed.
     *
     * @param message a message to persist
     * @return
     */
    void saveMessage(Message message);

    /**
     * Finder.
     *
     * @param id of a Message enitty
     * @return
     */
    Message findMessageById(String id);

    /**
     * Find messages for this node.
     *
     * @param nodeId a node for which we want all messages
     * @return a list of {@link Message}s
     */
    List<Message> findAllMessages(String nodeId);


    /**
     * Find messages for this node.
     *
     * @param searchText text to use for index search
     * @return a list of {@link Message}s
     */
    List<Message> searchMessages(String searchText);


    /**
     * @param routerId
     * @return
     */
    List<Node> findAllNodes(String routerId);


    /**
     * Stores a router - all relationships need to be inplace for persitence operation is to succeed.
     * Typically used when a router instance starts up and reads in a configuration file.
     *
     * @param router a router to persist
     * @return
     */
    void saveRouter(Router router);


    /**
     * Stores a node - all relationships need to be inplace for persitence operation is to succeed.
     * Altering a node does not persiste between restarts of a Router, since the master config file
     * is read on every restart of the router.
     *
     * @param node
     */
    void saveNode(Node node);


    /**
     * Retrieve number of messages for every node on this router instance - using select count(*).
     *
     * @param routerId id of the node we want to query
     * @return list {@link Node}s with number of messages set
     */
    List<Node> findNodesWithNumberOfMessages(String routerId);


    /**
     * Find messages for this node.
     *
     * @param searchText text to use for index search
     * @return a list of {@link Node}s
     */
    List<Node> searchNodes(String searchText);


    /**
     * Retrieves all endpointtypes - this operation is cachable since it will always for the lifteime
     * of the grouter be the same types. We can not support e.g. a corba endpointtype without some
     * modifications and new release of the grouter.
     *
     * @return map with endpointtypes, key is id and value is the EndPointType insance
     */
    Map<Long, EndPointType> findAllEndPointTypes();


    Node findNodeById(String nodeId);


    /**
     * A router config may change the id of a node - that node will still remain in the database
     * This method changes the state of nodes with a valid router parent but that are not configured
     * in router config startup xml file to {@link org.grouter.domain.entities.NodeStatus#NOT_CONFIGURED_TO_START}
     *
     * @param routerId
     */
    void updateStateForNotConfiguredNodes(final String routerId, final Set<Node> configuredNodes);

    //Settings findSettings(  );

}
