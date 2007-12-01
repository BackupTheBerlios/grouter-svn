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

package org.grouter.domain.daolayer.ejb3;

import org.apache.log4j.Logger;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.entities.Message;
import org.hibernate.FetchMode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


public class MessageDAOBean extends GenericEjb3DAO<Message, String> implements MessageDAO
{
    private static Logger logger = Logger.getLogger(MessageDAOBean.class);

    public MessageDAOBean()
    {
        super(Message.class);
    }

    public MessageDAOBean(Class persistentClass)
    {
        super(persistentClass);
    }

    public List<Message> findConcrete(Class concreteClass)
    {
        throw new NotImplementedException();
    }

    public List<Message> findMessagesForNode(String nodeId)
    {
        throw new NotImplementedException();
    }

    public List<Message> findMessagesFromIndex(final String queryForMe)
    {
        throw new NotImplementedException();
    }

    public List<Message> findAllLazy(final Class clazz, final String... disJoinProps)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Message> findAllUsingFetchMode(final Class clazz, final FetchMode fetchMode, final
    String... disJoinProps
    )
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

