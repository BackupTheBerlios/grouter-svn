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

package org.grouter.domain.dao.spring;

import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.entities.Message;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hibernate-specific implementation of the <tt>MessageDAOImpl</tt>
 * non-CRUD data access object.
 */
public class MessageDAOImpl extends GenericHibernateDAO<Message, Long> implements MessageDAO
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


    public List<Message> findMessagesForNode(final String nodeId)
    {
        Query qr = getSession().getNamedQuery("message.findMessageByNodeId");
        return (List<Message>) qr.setParameter("nodeid", nodeId).list();
    }

    public List<Message> findMessagesBy(final Long messageId, final Date fromDate, final Date toDate, final String nodeId)
    {
        Criteria crit = getSession().createCriteria( getEntityClass() );

        crit.setFetchMode("receivers", FetchMode.DEFAULT);
        crit.setFetchMode("node", FetchMode.DEFAULT);

        if( messageId == null && fromDate == null && toDate == null && nodeId == null )
        {
            return new ArrayList<Message>();
        }

        if (messageId != null)
        {
            crit.add( Restrictions.idEq(messageId));
            crit.addOrder( Order.asc("id") );

        }
        if (nodeId != null)
        {
            crit.add( Restrictions.eq("node.id",nodeId));
            crit.addOrder( Order.asc("id") );

        }
        if (fromDate != null)
        {
            crit.add( Restrictions.ge("auditInfo.createdOn", fromDate));
            crit.addOrder( Order.asc("auditInfo.createdOn") );
        }
        if (toDate != null)
        {
            crit.add( Restrictions.le("auditInfo.createdOn", toDate));
            crit.addOrder( Order.asc("auditInfo.createdOn") );
        }
        List<Message> messages = crit.list(); 
        return messages;
    }


    public List<Message> findAllMessages()
    {
        String hsql = "from Message as m";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        qr.setCacheable(true);
        return (List<Message>) qr.list();
    }
}
