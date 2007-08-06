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

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.spring.GenericHibernateDAO;
import org.grouter.domain.entities.Message;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.MatchMode;

import java.util.List;

/**
 * Hibernate-specific implementation of the <tt>MessageDAOImpl</tt>
 * non-CRUD data access object.
 */
public class MessageDAOImpl extends GenericHibernateDAO<Message, String> implements MessageDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
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
    public List<Message> findByParams(Long messageId, String freetext, QueryColumn freeTextColumnName)
    {
        boolean freeTextColumnSet = (freeTextColumnName != null);
        boolean freeTextSet = (freetext != null && !freetext.equalsIgnoreCase(""));

        Criteria criteria = getSession().createCriteria(Message.class);


        if (messageId != null)
        {
            criteria.add(Expression.eq("id", messageId)).setFetchMode("dealerType", FetchMode.JOIN);
        }

        if (!freeTextSet)
        {
            return criteria.addOrder(Order.asc("id")).list();
        }

        // no freeTextColumnName and freetext
        if (!freeTextColumnSet && freeTextSet)
        {
            Long id = LongValidator.getInstance().validate(freetext);
            if (id != null)
            {
                criteria.add(Restrictions.like("id", id)).setFetchMode("id", FetchMode.JOIN);
                return criteria.addOrder(Order.asc("id")).list();
            } else
            {

                criteria.createCriteria("address").add(Restrictions.like("companyName", freetext, MatchMode.START)).setFetchMode("companyName", FetchMode.JOIN);
                return criteria.addOrder(Order.asc("id")).list();
            }
        }

        // freeTextColumnName and freetext set
        if (freeTextSet && freeTextColumnSet)
        {
            if (freeTextColumnName == QueryColumn.ZIP)
            {
                criteria.createCriteria("address").add(Restrictions.like("zip", freetext, MatchMode.START).ignoreCase()).setFetchMode("zip", FetchMode.JOIN) ;
                return criteria.addOrder(Order.asc("id")).list();
            }
            if (freeTextColumnName == QueryColumn.CONTACTPERSON)
            {
                criteria.add(Restrictions.like("contactPerson", freetext, MatchMode.START).ignoreCase()).setFetchMode("contactPerson", FetchMode.JOIN);
                return criteria.addOrder(Order.asc("id")).list();
            }
             if (freeTextColumnName == QueryColumn.PHONE)
            {
                criteria.createCriteria("address").add(Restrictions.like("phone1", freetext, MatchMode.START )).setFetchMode("phone1", FetchMode.JOIN);
                return criteria.addOrder(Order.asc("id")).list();
            }
        }
        // column but no text -> do nothing


        return criteria.addOrder(Order.asc("id")).list();
    }
    */
}
