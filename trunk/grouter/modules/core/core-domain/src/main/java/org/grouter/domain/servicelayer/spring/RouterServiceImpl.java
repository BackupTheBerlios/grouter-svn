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

package org.grouter.domain.servicelayer.spring;

import org.grouter.domain.daolayer.*;
import org.grouter.domain.entities.*;
import org.grouter.domain.servicelayer.RouterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Map;


/**
 * The implementation of the interface {@link org.grouter.domain.servicelayer.RouterService} uses underlying
 * generic DAOs providing transaction demarcation for the service layer.
 *
 * Client such as - gswing and gweb - uses this service layer.
 *
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext xml file/s
 * or if you are using Ejb3 in the annotations of the Ejb3 session beans.
 *
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
     * @param endPointTypeDAO injected dao
     */
    public void setEndPointTypeDAO(EndPointTypeDAO endPointTypeDAO)
    {
        this.endPointTypeDAO = endPointTypeDAO;
    }

    /**
     * Injected.
     * @param routerDAO injecteed DAO
     */
    public void setRouterDAO(RouterDAO routerDAO)
    {
        this.routerDAO = routerDAO;
    }

    /**
     * Injected.
     * @param nodeDAO injected DAO
     */
    public void setNodeDAO(NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }


    /**
     * Injected.
     * @param messageDAO injected DAO
     */
    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }


    /**
     * {@inheritDoc}
     */
    public List<Router> findAll()
    {
        return routerDAO.findAll();
    }


    /**
     * {@inheritDoc}
     */
    public List<Router> findAllDistinct()
    {
        return routerDAO.findAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    public void saveMessage(Message message)
    {
        Validate.notNull(message, "In parameter can not be null");
        messageDAO.save(message);
    }

    /**
     * {@inheritDoc}
     */
    public Message findMessageById(String id)
    {
        Validate.notNull(id, "In parameter can not be null");
        Message foundMessage = messageDAO.findById(id);
        return foundMessage;
    }


    /**
     * {@inheritDoc}
     */
    public List<Message> findAllMessages(String nodeId)
    {
        return messageDAO.findMessagesForNode( nodeId );
    }


    /**
     * {@inheritDoc}
     */
    public List<Node> findAllNodes(String routerId)
    {
        return nodeDAO.findAllNodes( routerId );
    }


    /**
     * {@inheritDoc}
     */
    public void saveRouter(Router router)
    {
        Validate.notNull(router, "In parameter can not be null");
        routerDAO.save(router);
    }

    /**
     * {@inheritDoc}
     */
    public void saveNode(Node node)
    {
        Validate.notNull(node, "In parameter can not be null");
        nodeDAO.save(node);
    }

    /**
     * {@inheritDoc }
     */
    public List<Node> findNodesWithNumberOfMessages(String routerId)
    {
        return nodeDAO.findNodesWithNumberOfMessages( routerId );  
    }

    public Map<Long,EndPointType> findAllEndPointTypes()
    {
        return EndPointType.values;
    }


    /**
     * {@inheritDoc}
     */
    public Node findById(String nodeId)
    {
        return nodeDAO.findById( nodeId );
    }


}
