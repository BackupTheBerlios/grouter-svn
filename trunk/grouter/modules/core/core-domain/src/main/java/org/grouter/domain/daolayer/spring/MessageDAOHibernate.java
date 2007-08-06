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

import java.util.List;

/**
 * Hibernate-specific implementation of the <tt>MessageDAO</tt>
 * non-CRUD data access object.
 *
 * @deprecated
 */
public class MessageDAOHibernate extends GenericHibernateDAO<Message, String> implements MessageDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public MessageDAOHibernate()
    {
        super(Message.class);
    }

    public MessageDAOHibernate(Session session)
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
        String hsql = "from Message obj where obj.nodeId = : nodeId";
        Query qr = getSession().createQuery(hsql);
        return (List<Message>) qr.setParameter("nodeId", nodeId).list();
    }

    /**
     * Makes transient message persisted.
     *
     * @param message
     * @return the persisted message with an id
     */
    public Message createMessage(Message message)
    {
        return save(message);
    }

    public void setSession(Session s)
    {
        session = s;
    }

}
