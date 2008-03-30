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

import org.grouter.domain.dao.SystemDAO;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.User;
import org.hibernate.*;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Hibernate-specific implementation of the {@link org.grouter.domain.dao.SystemDAO} interface.
 * <p/>
 * Handles search index operations.
 * <p/>
 * To avoid OutOfMemoryExceptions we need to provide hibernate.search.worker.batch_size
 *
 * @author Georges Polyzois
 */
public class SystemDAOImpl extends HibernateDaoSupport implements SystemDAO
{

    public void initSearchIndex()
    {
        doFullIndex(1000, Message.class, getSession());
        doFullIndex(1000, User.class, getSession());
        //doFullIndex(1000, Job.class, getSession());
        doFullIndex(1000, Node.class, getSession());
    }


    public void doFullIndex(int batchSize, Class theIndexClass, Session session)// Class... clazzes)
    {
        FullTextSession fullTextSession = Search.createFullTextSession(session);
        fullTextSession.setFlushMode(FlushMode.MANUAL);
        fullTextSession.setCacheMode(CacheMode.IGNORE);
        //Transaction transaction = fullTextSession.beginTransaction();
//Scrollable results will avoid loading too many objects in memory
        ScrollableResults results = fullTextSession.createCriteria(theIndexClass).scroll(ScrollMode.FORWARD_ONLY);
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
        //transaction.commit();
    }
}