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

package org.grouter.presentation.beans;

import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Message;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Georges Polyzois
 */
public class MessageBean
{
    private RouterService grouterService;
    private Message message;

    public void setGrouterService(RouterService grouterService)
    {
        this.grouterService = grouterService;
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }

    public List<Message> getMessages( String nodeid )
    {
        List<Message> messages =  grouterService.findAllMessages( nodeid  );
        return messages;
    }


}
