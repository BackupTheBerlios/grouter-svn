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

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;
import org.grouter.domain.daolayer.*;
import org.grouter.domain.daolayer.ejb3.PersistenceContextName;
import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.NotImplementedException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.SessionContext;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Stateless
public class RouterBeanService implements RouterLocalService, RouterRemoteService
{
    //Seam @Logger annotation is used to inject the component's Log instance
    // @Logger
    private static Logger logger = Logger.getLogger(RouterBeanService.class);

    @PersistenceContext(unitName = PersistenceContextName.PERSISTENCE)
    private EntityManager entityManager;

    private MessageDAO messageDAO;
    private NodeDAO nodeDAO;
    private UserDAO userDAO;
    private RouterDAO routerDAO;

    @Resource
    private SessionContext sc;

    public void setRouterDAO(RouterDAO routerDAO)
    {
        this.routerDAO = routerDAO;
    }


    public List<Router> findAll()
    {
        return routerDAO.findAll();
    }

    public List<Router> findAllDistinct()
    {
        throw new NotImplementedException("implemente this");

    }

    public List<Router> findByProjection()
    {
        throw new NotImplementedException("implemente this");
    }

    public void saveMessage(Message message)
    {
        Validate.notNull(message, "Can not handle a null message");
        messageDAO = DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        messageDAO.save(message);
    }

    public Message findMessageById(String id)
    {
        return messageDAO.findById(id);
    }

    public List<Message> findAllMessages(final String id)
    {
        return messageDAO.findMessagesForNode(id);
    }

    public List<Node> findAllNodes(final String routerId)
    {
        return nodeDAO.findAll();
    }

    public void saveRouter(final Router router)
    {
        routerDAO.save(router);
    }

    public void saveNode(final Node node)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Node> findNodesWithNumberOfMessages(String routerId)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map findAllEndPointTypes()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Node> getNodeWithNumberOfMessages(String routerId)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Long getNumberOfMessages(String nodeId)
    {
        throw new NotImplementedException("implemente this");
    }

    //todo
    public Node findNodeById(String nodeId)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateStateForNotConfiguredNodes(final String routerId,
                                                 final Set<Node> configuredNodes
    )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateStateForNotConfiguredNodes(final String routerId,
                                                 final List<Node> configuredNodes
    )
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    //TODO fix a dao that takes a collection of messaes insttead...
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createMessage(Message[] messages)
    {
        logger.debug("In saveMessage. Number of messages :" + messages.length);
        if (messages == null)
        {
            throw new IllegalArgumentException("Null inparameter");
        }
        messageDAO = DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        for (Message message : messages)
        {
            logger.debug("## Calling dao to create message");
            messageDAO.save(message);
        }
    }

}
