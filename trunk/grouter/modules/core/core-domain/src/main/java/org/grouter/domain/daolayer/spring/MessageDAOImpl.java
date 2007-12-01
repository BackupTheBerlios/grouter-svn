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

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.entities.Message;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import java.util.List;

/**
 * Hibernate-specific implementation of the <tt>MessageDAOImpl</tt>
 * non-CRUD data access object.
 */
public class MessageDAOImpl extends GenericHibernateDAO<Message, String> implements MessageDAO
{
    public MessageDAOImpl()
    {
        super(Message.class);
    }

    public MessageDAOImpl(Session session)
    {
        super(Message.class, session);
    }

    @SuppressWarnings("unchecked")
    public List<Message> findConcrete(Class concreteClass)
    {
        return getSession().createCriteria(concreteClass).list();
    }


    public List<Message> findMessagesForNode(String nodeId)
    {
       /* String hsql = "from Message obj where obj.node.id = :nodeid";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        return (List<Message>) qr.setParameter("nodeid", nodeId).list();*/


        Criteria criteria = getSession().createCriteria(Message.class);
        criteria.add(Expression.eq("node.id", nodeId));
        return criteria.addOrder(Order.asc("id")).list();

    }

    /*
    public List<Message> findMessagesFromIndex(final String queryForMe)
    {

        FullTextSession fullTextSession = Search.createFullTextSession(getSession());

        MultiFieldQueryParser parser = new MultiFieldQueryParser( new String[]{"content"}, new StandardAnalyzer());
        Query query = null;
        try
        {
            query = parser.parse( queryForMe );
            org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery( query, Message.class );
            return hibQuery.list();
        } catch (ParseException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;


    } */
}
