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

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Message;
import org.grouter.domain.service.RouterService;

import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


/**
 * @author Georges Polyzois
 */
public class RouterQueueListenerImpl implements MessageListener // no need since we are using an adapter implements MessageListener
{
    private static Logger logger = Logger.getLogger(RouterQueueListenerImpl.class);
    RouterService service;

    public void setGrouterService(RouterService service)
    {
        this.service = service;
    }

    public void onMessage(javax.jms.Message receivedMessage)
    {
        try
        {
            ObjectMessage objectMessage = (ObjectMessage) receivedMessage;
            logger.debug("Got message : " + objectMessage.getObject());
            if (objectMessage.getObject() instanceof org.grouter.domain.entities.Message)
            {
                process((Message) objectMessage);

            } else
            {
                logger.debug("Got unknown type of message!!!");
            }

        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }


    /**
     * Call session ejb for processing of event.
     */
    public void process(Message message)
    {
        //org.grouter.domain.entities.Message message = (org.grouter.domain.entities.Message) objectMessage.getObject();
        logger.debug("##### Got a message and persisting it : " + message.getId());
        service.saveMessage(message);
    }

}
