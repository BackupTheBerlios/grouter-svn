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
package org.grouter.domain.search;

import org.apache.commons.io.FileUtils;
import org.grouter.common.hibernatesearch.FullIndexHandler;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.NodeDAO;
import org.grouter.domain.entities.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Georges Polyzois
 */
public abstract class AbstractHibernateSearchTest extends AbstractTransactionalDataSourceSpringContextTests
{
    // inserted test data and ids of some of the data
    final static String MESSAGE_ID = "msgid_1";
    final static String NODE_ID = "nid_1";
    final static String NODE_ID_FTP = "nid_2";
    final static String ROUTER_ID = "rid_1";
    final static String ENDPOINT_ID = "1";
    final static String SETTINGS_ID = "jndi";
    final static Long JOB_ID = 1L;
    Long USER_ID = 10002L;

    SessionFactory sessionFactory;
    MessageDAO messageDAO;
    NodeDAO nodeDAO;

    /**
     * Injected.
     *
     * @param sessionFactory injected
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Our context files for these tests.
     *
     * @return context files
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        "context-domain-datasource.xml",
                        "context-domain-sessionfactory.xml",
                        "context-domain-dao.xml",
                        "context-domain-service.xml"
                };
    }


    /**
     * Location of test data realtive to the resources location.
     *
     * @return relative path to test data location
     */
    protected String getTestDataLocation()
    {
        return "sql/test-data-search.sql";
    }

    protected void flushSession()
    {
        sessionFactory.getCurrentSession().flush();
    }

    protected Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }


    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        super.onSetUpInTransaction();

        List<String> lines = FileUtils.readLines(new ClassPathResource(getTestDataLocation()).getFile(),
                "ISO-8859-1");

        for (String line : lines)
        {
            if (StringUtils.hasText(line))
            {
                jdbcTemplate.execute(line);
            }
        }


        FullIndexHandler fullIndexHandler = new FullIndexHandler();
        fullIndexHandler.doFullIndex(1000, Message.class, getSession());

    }


    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public void setNodeDAO(NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }
}

