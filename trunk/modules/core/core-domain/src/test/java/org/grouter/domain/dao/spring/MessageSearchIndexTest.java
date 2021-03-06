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


/**
 * @author Georges Polyzois
 */
public class MessageSearchIndexTest extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(MessageSearchIndexTest.class);

    public void testFind()
    {
        setUpIndex();
        setComplete();
       // List<Message> messageList = messageDAO.findFromIndex("java", "content");
       // assertNotNull(messageList);
       // assertTrue(messageList.size() > 0);
    }

    public void testLazyCollections()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void testFindById()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void testDelete()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void testSave()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
