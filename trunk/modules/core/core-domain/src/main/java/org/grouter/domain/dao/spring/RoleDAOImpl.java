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

import org.apache.log4j.Logger;
import org.grouter.domain.dao.RoleDAO;
import org.grouter.domain.entities.Role;
import org.hibernate.Session;

/**
 * Hibernate-specific implementation of the {@link org.grouter.domain.dao.RoleDAO} interface.
 *
 * @author Georges Polyzois
 */
public class RoleDAOImpl extends GenericHibernateDAO<Role, Long> implements RoleDAO
{
    Logger logger = Logger.getLogger(RoleDAOImpl.class);

    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public RoleDAOImpl()
    {
        super(Role.class);
    }

    public RoleDAOImpl(Session session)
    {
        super(Role.class, session);
    }

    public Role save(Role role)
    {
         throw new UnsupportedOperationException("New roles can not be saved.");
    }

}