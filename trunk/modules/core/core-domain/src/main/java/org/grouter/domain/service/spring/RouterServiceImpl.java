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

package org.grouter.domain.service.spring;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.EndPointTypeDAO;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.NodeDAO;
import org.grouter.domain.dao.RouterDAO;
import org.grouter.domain.entities.*;
import org.grouter.domain.service.RouterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The implementation of the interface {@link org.grouter.domain.service.RouterService} uses underlying
 * generic DAOs providing transaction demarcation for the service layer.
 * <p/>
 * Client such as - gswing and gweb - uses this service layer.
 * <p/>
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext xml file/s
 * or if you are using Ejb3 in the annotations of the Ejb3 session beans.
 * <p/>
 * DAOs are injected using Spring.
 *
 * @author Georges Polyzois
 */
public class RouterServiceImpl implements RouterService
{
    private static Log logger = LogFactory.getLog(RouterServiceImpl.class);
    private MessageDAO messageDAO;
    private NodeDAO nodeDAO;
    private EndPointTypeDAO endPointTypeDAO;
    private RouterDAO routerDAO;

    /**
     * Constructor.
     */
    public RouterServiceImpl()
    {

    }

    /**
     * Injected.
     *
     * @param endPointTypeDAO injected dao
     */
    public void setEndPointTypeDAO(final EndPointTypeDAO endPointTypeDAO)
    {
        this.endPointTypeDAO = endPointTypeDAO;
    }

    /**
     * Injected.
     *
     * @param routerDAO injecteed DAO
     */
    public void setRouterDAO(final RouterDAO routerDAO)
    {
        this.routerDAO = routerDAO;
    }

    /**
     * Injected.
     *
     * @param nodeDAO injected DAO
     */
    public void setNodeDAO(final NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }


    /**
     * Injected.
     *
     * @param messageDAO injected DAO
     */
    public void setMessageDAO(final MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }


    public List<Router> findAll()
    {
        return routerDAO.findAll();
    }

    public List<Router> findAllDistinct()
    {
        return routerDAO.findAllDistinct();
    }

    public void saveMessage(final Message message)
    {
        Validate.notNull(message, "In parameter can not be null");
        messageDAO.save(message);
    }

    public Message findMessageById(final Long id)
    {
        Validate.notNull(id, "In parameter can not be null");
        Message foundMessage = messageDAO.findById(id);
        return foundMessage;
    }


    public List<Message> findAllMessages(final String nodeId)
    {
        return messageDAO.findMessagesForNode(nodeId);
    }

    public List<Message> searchMessages(String searchText)
    {
        return messageDAO.findFromIndex(searchText, "content", "id");
    }


    public List<Node> findAllNodes(final String routerId)
    {
        return nodeDAO.findAllNodes(routerId, true);
    }


    public void saveRouter(final Router router)
    {
        Validate.notNull(router, "In parameter can not be null");
        routerDAO.save(router);
    }

    public void saveNode(Node node)
    {
        Validate.notNull(node, "In parameter can not be null");
        nodeDAO.save(node);
    }

    public List<Node> findNodesWithNumberOfMessages(final String routerId)
    {
        return nodeDAO.findNodesWithNumberOfMessages(routerId);
    }

    public List<Node> searchNodes(String searchText)
    {
        return nodeDAO.findFromIndex(searchText, "displayName", "id", "statusMessage", "source", "receiver",
                "modifiedOn", "createdOn");
    }

    public List<Router> searchRouter(String searchText)
    {
        return routerDAO.findFromIndex(searchText, "displayName", "id", "description", "homePath");

    }

    public Map<Long, EndPointType> findAllEndPointTypes()
    {
        return EndPointType.values;
    }


    public Router findRouterById(String routerId)
    {
        return routerDAO.findById(routerId);
    }

    public Node findNodeById(String nodeId)
    {
        return nodeDAO.findById(nodeId);
    }

    public void updateStateForNotConfiguredNodes(final String routerId,
                                                 final Set<Node> allConfiguredNodes)
    {
        // including orphan nodes
        List<Node> allNodesIncludingOrphans = nodeDAO.findAllNodes(routerId, true);

        List<Node> changeStatenodes = new ArrayList<Node>();
        for (Node aPotentialOrphan : allNodesIncludingOrphans)
        {
            if (!allConfiguredNodes.contains(aPotentialOrphan))
            {
                changeStatenodes.add(aPotentialOrphan);
            }
        }
        for (Node node : changeStatenodes)
        {
            // changing the node state
            node.setNodeStatus(NodeStatus.NOT_CONFIGURED_TO_START);
            nodeDAO.save(node);
        }
    }


}
