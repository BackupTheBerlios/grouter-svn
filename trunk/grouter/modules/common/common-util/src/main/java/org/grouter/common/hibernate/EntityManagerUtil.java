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

package org.grouter.common.hibernate;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * HibernateUtil but for
 *
 * @author Georges Polyzois
 */
public class EntityManagerUtil
{
    private static EntityManagerFactory emf;
    public static final ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();
    private static final String GROUTER_DOMAIN = "grouterDomain";

    public static EntityManagerFactory getEntityManagerFactory()
    {
        if (emf == null)
        {
            // Create the EntityManagerFactory
            emf = Persistence.createEntityManagerFactory(GROUTER_DOMAIN);
        }

        return emf;
    }


    /**
     * Corresponds to a Hibernate Session.
     *
     * @return
     */
    public static EntityManager getEntityManager()
    {
        EntityManager em = entitymanager.get();

        // Create a new EntityManager
        if (em == null)
        {
            em = emf.createEntityManager();
            entitymanager.set(em);
        }
        return em;
    }


    /**
     * Close our "session".
     */
    public static void closeEntityManager()
    {
        EntityManager em = entitymanager.get();
        entitymanager.set(null);
        if (em != null) em.close();
    }

}

