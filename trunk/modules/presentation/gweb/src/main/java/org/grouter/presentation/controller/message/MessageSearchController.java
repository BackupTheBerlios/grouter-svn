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
package org.grouter.presentation.controller.message;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Message;
import org.grouter.domain.service.MessageService;
import org.grouter.domain.service.RouterService;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Uses Lucene index to query
 *
 * @author Georges Polyzois
 */
public class MessageSearchController extends AbstractCommandController
{
    private static Logger logger = Logger.getLogger(MessageListController.class);
    private static final String LIST_VIEW = "message/ajaxformMessageSearchResult.jsp";
    private MessageService messageService;
    private MessageSearchCommandValidator messageSearchCommandValidator = new MessageSearchCommandValidator();
    private RouterService routerService;

    public MessageSearchController()
    {
        setCommandClass( MessageSearchCommand.class );
 //       setCommandName( "messagesearchcommand"); // name in form tag of corresponding view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
    {

        Map<String, Object> map = new HashMap<String, Object>();

        MessageSearchCommand cmd = (MessageSearchCommand) command;

        /*String nodeId = ServletRequestUtils.getStringParameter(request, "search.nodeId", null);
        String id = ServletRequestUtils.getStringParameter(request, "search.id", null);
        Integer idInt = null;
        if( id != null )
        {
            idInt = Integer.parseInt(id);
        }

        String fromDateStr = ServletRequestUtils.getStringParameter(request, "search.fromDate", null);
        String toDateStr = ServletRequestUtils.getStringParameter(request, "search.toDate", null);
        Date fromDate = null;
        Date toDate = null;
        if( fromDateStr != null )
        {
            DateTime dateTime = DateUtil.getFromString(fromDateStr);
            fromDate = dateTime.toDate();
        }
        if( toDateStr != null )
        {
            DateTime dateTime = DateUtil.getFromString(toDateStr);
            toDate = dateTime.toDate();
        }
          */

        List<Message> messages = messageService.findMessage(cmd.getMessageId(),null, null, cmd.getNodeId());
        map.put("messages", messages);
        map.put("command",cmd);

        return new ModelAndView(LIST_VIEW, map);
    }


    public void setMessageService(MessageService messageService)
    {
        this.messageService = messageService;
    }


    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }
}
