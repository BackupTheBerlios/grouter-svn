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
import org.grouter.common.hibernate.HibernateUtilContextAware;
import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.UserDAO;
import org.hibernate.Session;

/**
 * Returns Hibernate-specific instances of DAOs.
 * <p/>
 * One of the responsiblities of the factory is to inject a Hibernate Session
 * into the DAOs. You can customize the getCurrentSession() method if you
 * are not using the default strategy, which simply delegates to
 * Hibernates built-in "current Session" mechanism.
 * <p/>
 * If for a particular DAO there is no additional non-CRUD functionality, we use
 * an inner class to implement the interface in a generic way. This allows clean
 * refactoring later on, should the interface implement business data access
 * methods at some later time. Then, we would externalize the implementation into
 * its own first-level class. We can't use anonymous inner classes for this trick
 * because they can't extend or implement an interface and they can't include
 * constructors.
 * <p/>
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss )
 *
 * @author Georges Polyzois
 */
public class HibernateDAOFactory extends DAOFactory
{
    private static Log log = LogFactory.getLog(HibernateDAOFactory.class);

    protected Session getCurrentSession()
    {
        return HibernateUtilContextAware.getSessionFactory().getCurrentSession();
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
