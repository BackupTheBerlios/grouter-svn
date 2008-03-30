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
import org.grouter.domain.dao.UserDAO;
import org.grouter.domain.dao.UserRoleDAO;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.UserRole;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Hibernate-specific implementation of the {@link org.grouter.domain.dao.UserDAO} interface.
 *
 * @author Georges Polyzois
 */
public class UserRoleDAOImpl extends GenericHibernateDAO<UserRole, Long> implements UserRoleDAO
{
    Logger logger = Logger.getLogger(UserRoleDAOImpl.class);

    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public UserRoleDAOImpl()
    {
        super(UserRole.class);
    }

    public UserRoleDAOImpl(Session session)
    {
        super(UserRole.class, session);
    }

    protected void setSession(Session s)
    {
        session = s;
    }

    public UserRole findUserRoleByUserIdAndRoleId(Long userid, Long roleId)
    {
        String hsql = "from UserRole obj where obj.user.id = :userid and obj.role.id = :roleide ";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        qr.setParameter("userid", userid);
        qr.setParameter("roleide", roleId); 
        return (UserRole) qr.uniqueResult();
    }

    public void deleteUserRole(Long userId)
    {
        String hsql = "delete from UserRole obj where obj.user.id = :userid";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        qr.setParameter("userid", userId);
        qr.executeUpdate();
    }
}