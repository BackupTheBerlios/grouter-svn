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

import org.apache.log4j.Logger;
import org.grouter.domain.daolayer.UserDAO;
import org.grouter.domain.entities.User;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Hibernate-specific implementation of the {@link UserDAO} interface.
 *
 * @author Georges Polyzois
 */
public class UserDAOImpl extends GenericHibernateDAO<User, Long> implements UserDAO
{
    Logger logger = Logger.getLogger(UserDAOImpl.class);

    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public UserDAOImpl()
    {
        super(User.class);
    }

    public UserDAOImpl(Session session)
    {
        super(User.class, session);
    }


    protected void setSession(Session s)
    {
        session = s;
    }

    public List<User> findAllDistinct(String hql)
    {
        String hsql = "select obj from User obj";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        return qr.list();
    }


}
