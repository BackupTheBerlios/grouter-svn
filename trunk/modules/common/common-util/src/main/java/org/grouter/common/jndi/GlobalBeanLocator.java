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
package org.grouter.common.jndi;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;

/**
 * Holds the beanfactory for global use.
 *
 * @author Georges Polyzois
 */
public class  GlobalBeanLocator
{
    private static Logger logger = Logger.getLogger(GlobalBeanLocator.class);
    private static ApplicationContext context;
    private static final String APPLICATION_CONTEXT_XML = "applicationContext.xml";
    private static GlobalBeanLocator globalBeanLocator;

    public static GlobalBeanLocator getInstance()
    {
        if(globalBeanLocator == null)
        {
            globalBeanLocator = new GlobalBeanLocator();
        }
        return globalBeanLocator;

    }

    // do not return beanfactory
    public  ApplicationContext getApplicationContext()
    {
        if (context == null)
        {
            throw new IllegalStateException("Beanfactory has not been set, context from " + APPLICATION_CONTEXT_XML + " not set");
        }
        return context;//.getParentBeanFactory();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        logger.info("Setting application context");
        context = applicationContext;
    }


    protected String[] getConfigLocations()
    {
        return new String[]{
                "ac-dao.xml",
                "ac-datasource.xml",
                "ac-service.xml",
                "ac-sessionfactory.xml"

        };
    }
}
