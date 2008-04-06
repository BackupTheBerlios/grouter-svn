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
import org.grouter.domain.dao.ReceiverDAO;
import org.grouter.domain.dao.SenderDAO;
import org.grouter.domain.entities.Receiver;
import org.grouter.domain.entities.Sender;
import org.hibernate.Session;

/**
 * Hibernate-specific implementation of the {@link org.grouter.domain.dao.RoleDAO} interface.
 *
 * @author Georges Polyzois
 */
public class SenderDAOImpl extends GenericHibernateDAO<Sender, Long> implements SenderDAO
{
    Logger logger = Logger.getLogger(SenderDAOImpl.class);

    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public SenderDAOImpl()
    {
        super(Sender.class);
    }

    public SenderDAOImpl(Session session)
    {
        super(Sender.class, session);
    }
}