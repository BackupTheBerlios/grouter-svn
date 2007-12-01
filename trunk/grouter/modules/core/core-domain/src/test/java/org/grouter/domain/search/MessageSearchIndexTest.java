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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.common.hibernatesearch.FullIndexHandler;
import org.grouter.domain.entities.Message;

import java.util.List;


/**
 * @author Georges Polyzois
 */
public class MessageSearchIndexTest extends AbstractHibernateSearchTest
{
    private static Log log = LogFactory.getLog(MessageSearchIndexTest.class);


       public void testFind()
       {

           //flushSession();

           List<Message> messageList = messageDAO.findFromIndex( "java", "content","kalle" );


           assertNotNull( messageList );
           assertTrue( messageList.size() > 0 );


           for (Message message : messageList)
           {
               log.debug( "##" + message );
           }
           
       }


}
