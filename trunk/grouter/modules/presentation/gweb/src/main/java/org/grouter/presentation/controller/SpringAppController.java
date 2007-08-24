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

package org.grouter.presentation.controller;


import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Message;
import org.grouter.common.jndi.GlobalBeanLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

class SpringAppController implements Controller, ApplicationContextAware
{
    public void setGRouterService(RouterService gRouterService)
    {
        this.gRouterService = gRouterService;
    }

    public org.grouter.domain.servicelayer.RouterService gRouterService;
    protected static BeanFactory factory;
    private final static String beanName = "messageServiceManager";


    private static Logger logger = Logger.getLogger(SpringAppController.class);

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        logger.info("SpringappController - returning hello view");

        WebApplicationContext webApplicationContext = (WebApplicationContext) GlobalBeanLocator.getInstance().getApplicationContext();
        RouterService gRouterServiceService = (RouterService) webApplicationContext.getBean(beanName);

        Message message = gRouterServiceService.findMessageById("MESSAGE1");
   //     logger.info("grouterServiceImpl...." + message.getContent());
        return new ModelAndView("message.jsp", "message", message);
    }



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {

        logger.debug("Setting context for use in our dao factory");
         GlobalBeanLocator.getInstance().setApplicationContext(applicationContext);
    }
}


