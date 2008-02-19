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

import org.grouter.domain.daolayer.RouterDAO;
import org.grouter.domain.entities.Router;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Georges Polyzois
 */
public class RouterDAOImpl extends GenericHibernateDAO<Router, String> implements RouterDAO
{


    protected RouterDAOImpl()
    {
        super(Router.class);
    }

    protected RouterDAOImpl(Class<Router> persistentClass)
    {
        super(persistentClass);
    }

    public RouterDAOImpl(Class<Router> persistentClass, Session session)
    {
        super(persistentClass, session);
    }

    public List<Router> findAllDistinct()
    {
        String hsql = "from Router";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        return qr.list();
    }

    /*   public List<Router> findByProjection()
    {
        Criteria qriteria = getSession().createCriteria(getEntityClass())
                .setProjection(Projections.projectionList()
                        .add(Projections.id())
                        .add(Projections.property("name")));
        return qriteria.list();


    }

    */
}
