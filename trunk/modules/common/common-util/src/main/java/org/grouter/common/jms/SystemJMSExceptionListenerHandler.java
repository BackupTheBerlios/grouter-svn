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

package org.grouter.common.jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.log4j.Logger;

/**
 * ExceptionListener called from JMS provider when something goes wrong
 * like 'ping timeout'.
 *
 * @author Georges Polyzois
 */
public class SystemJMSExceptionListenerHandler implements ExceptionListener
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(SystemJMSExceptionListenerHandler.class);

    /**
     * Strategy to use for rebinding. We set it to eternal and wait for
     * JMS provider to restart.
     */
    private RebindBehavior rebindBehavior = new EternalRebind();
    
    /** Destination on which a rebind will issued on if needed. */
    private AbstractDestination destination;

    /**
     * Constructor taking a {@link SystemJMSExceptionListenerHandler}.
     *
     * @param dest Destination  to perform rebind on.
     */
    public SystemJMSExceptionListenerHandler(AbstractDestination dest)
    {
        this.destination = dest;
    }

    /**
     * Logging the exception and trying a rebind to JMS provider using
     * the {@link SystemJMSExceptionListenerHandler}.
     *
     * @param exception the JMSException we received
     * @see javax.jms.ExceptionListener#onException(javax.jms.JMSException)
     */
    public void onException(JMSException exception)
    {
        logger.info("Trying to rebind accourding to strategy!!");
        rebindBehavior.rebind(destination);
    }
}
