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

import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Settings;
import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.daolayer.SettingsDAO;
import org.grouter.domain.daolayer.spring.GenericHibernateDAO;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.Criteria;
import org.hibernate.type.Type;
import org.hibernate.criterion.Projections;

import java.util.List;


public class SettingsDAOImpl extends GenericHibernateDAO<Settings, String> implements SettingsDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public SettingsDAOImpl()
    {
        super(Settings.class);
    }

    public SettingsDAOImpl(Session session)
    {
        super(Settings.class, session);
    }


    public void delete(String id)
    {
        logger.info( "Not deleting any settings" );

    }


    public void delete(Settings entity)
    {
        logger.info( "Not deleting any settings" );

    }


}