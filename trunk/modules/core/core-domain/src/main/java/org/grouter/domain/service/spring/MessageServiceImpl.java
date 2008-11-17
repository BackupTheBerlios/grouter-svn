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

package org.grouter.domain.service.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.entities.Message;
import org.grouter.domain.service.MessageService;

import java.util.Date;
import java.util.List;


/**
 * The implementation of the interface {@link org.grouter.domain.service.RouterService} uses underlying
 * generic DAOs providing transaction demarcation for the service layer.
 * <p/>
 * Client such as - gswing and gweb - uses this service layer.
 * <p/>
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext xml file/s
 * or if you are using Ejb3 in the annotations of the Ejb3 session beans.
 * <p/>
 * DAOs are injected using Spring.
 *
 * @author Georges Polyzois
 */
public class MessageServiceImpl implements MessageService
{
    private static Log logger = LogFactory.getLog(MessageServiceImpl.class);
    private MessageDAO messageDAO;

    /**
     * Constructor.
     */
    public MessageServiceImpl()
    {

    }


    /**
     * Injected.
     *
     * @param messageDAO injected DAO
     */
    public void setMessageDAO(final MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }


    public List<Message> findMessage(final Long id, final Date fromDate, final Date toDate, final String nodeId)
    {
        return messageDAO.findMessagesBy(id, fromDate, toDate, nodeId);
    }
}