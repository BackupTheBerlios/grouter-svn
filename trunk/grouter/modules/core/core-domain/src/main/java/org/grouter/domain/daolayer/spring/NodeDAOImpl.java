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

package org.grouter.domain.daolayer.spring;

import org.grouter.domain.entities.Node;
import org.grouter.domain.daolayer.NodeDAO;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.Criteria;
import org.hibernate.type.Type;
import org.hibernate.criterion.Projections;

import java.util.List;


/**
 *
 *
 *
$Rev::                    $:  Revision of last commit
$Author::                 $:  Author of last commit
$Date::                   $:  Date of last commit
 *
 * $Revision$
 * $HeadURL$
 * $Date$
 * 
 * @author $Author$
 */
public class NodeDAOImpl extends GenericHibernateDAO<Node, String> implements NodeDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public NodeDAOImpl()
    {
        super(Node.class);
    }

    public NodeDAOImpl(Session session)
    {
        super(Node.class, session);
    }

    public List<Node> findNodesWithNumberOfMessages(String routerId)
    {
        String hsql = "select obj from Node obj where obj.router.id = :routerId";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        List<Node> nodes = qr.setParameter("routerId", routerId).list();
        for (Node node : nodes)
        {
            node.setNumberOfMessagesHandled(getNumberOfMessages(node.getId()));
        }
        return nodes;
    }

    public Long getNumberOfMessages(String nodeId)
    {
        String hsql = "select count(obj) from Message obj where obj.node.id = :nodeid";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        return (Long) qr.setParameter("nodeid", nodeId).uniqueResult();
    }


    public List<Node> findAllNodes(String routerId, boolean initEndPoint)
    {
        String hsql = "select obj from Node obj where obj.router.id = :routerId";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        List<Node> nodes = qr.setParameter("routerId", routerId).list();
        
        for (Node node : nodes)
        {
            node.setNumberOfMessagesHandled(getNumberOfMessages( node.getId()));
            if( initEndPoint )
            {
                Hibernate.initialize( node.getInBound() );
                Hibernate.initialize( node.getOutBound() );
            }
        }
        return nodes;

    }

    public List<Node> findNodes()
    {
        // Todo fix this to return count(*) for a particular node
        String sqlFragment = "(select count(*) from Message i where i.node_fk = node_fk) as numOfItems";
        Criteria criteria = getSession().createCriteria(getEntityClass())
                .setProjection(Projections.projectionList()
                        .add(Projections.property("id"))
                        .add(Projections.property("name"))
                        .add(Projections.sqlProjection(
                        sqlFragment, new String[]{"numOfItems"}, new Type[]{Hibernate.LONG}))
                );


        return criteria.list();
    }
}
