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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Returns Hibernate-specific instances of DAOs. SPring injects a sessionfactory as per defined in SPring context files.
 *
 * @author Georges Polyzois
 */
public class HibernateSpringDAOFactory extends DAOFactory
{
    private static Log logger = LogFactory.getLog(HibernateSpringDAOFactory.class);

    /**
     * Injected into this instance from Spring.
     */
    SessionFactory sessionFactory;

    public HibernateSpringDAOFactory()
    {
    }

    /**
     * Injected.
     *
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    protected Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    // Add your DAO interfaces below here
    public MessageDAO getMessageDAO()
    {
        return new MessageDAOImpl(getCurrentSession());
    }

    public UserDAO getSystemUserDAO()
    {
        return new UserDAOImpl(getCurrentSession());
    }
}
