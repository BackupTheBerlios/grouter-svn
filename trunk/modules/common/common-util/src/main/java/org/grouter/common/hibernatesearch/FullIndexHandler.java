package org.grouter.common.hibernatesearch;

import org.hibernate.*;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import java.io.Serializable;
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


/**
 * To avoid OutOfMemoryExceptions we need to provide hibernate.search.worker.batch_size
 *
 * @author Georges Polyzois
 * @depracated
 */
public class FullIndexHandler
{

    public void purgeEntity(Session session, Class entity, Serializable id)
    {
        FullTextSession fullTextSession = Search.createFullTextSession(session);
        Transaction tx = fullTextSession.beginTransaction();
        //for (Customer customer : customers)
        //{
        fullTextSession.purge(entity, id);
        //}
        tx.commit(); //index are written at commit time
    }


    /**
     * Purges the entity class from the index.
     *
     * @param session
     * @param entity
     */
    public void purgeAll(Session session, Class entity)
    {
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        //Transaction tx = session.beginTransaction();
        fullTextSession.purgeAll(entity);
        //optionally optimize the index
        fullTextSession.flushToIndexes();
        fullTextSession.getSearchFactory().optimize( entity );
        //tx.commit(); //index are written at commit time
    }

    /**
     * Creates index
     *
     * @param batchSize batch index parameter
     * @param session the Hibernate session
     * @param theIndexClass classes to index
     */
    public void doFullIndex(int batchSize, Session session, Class... theIndexClass)
    {
        FullTextSession fullTextSession = Search.createFullTextSession(session);
        fullTextSession.setFlushMode(FlushMode.MANUAL);
        fullTextSession.setCacheMode(CacheMode.IGNORE);
        //Scrollable results will avoid loading too many objects in memory
        for (Class theClass : theIndexClass)
        {
            ScrollableResults results = fullTextSession.createCriteria(theClass).scroll(ScrollMode.FORWARD_ONLY);
            int index = 0;
            while (results.next())
            {
                index++;
                fullTextSession.index(results.get(0)); //index each element
                if (index % batchSize == 0)
                {
                    session.clear(); //clear every batchSize since the queue is processed
                }
            }
        }
    }

}
