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


import org.apache.log4j.Logger;
import org.grouter.common.exception.RemoteGrouterException;

/**
 * This "algorithm" just unbindes and leaves it with that.
 *
 * @author Georges Polyzois
 */
public class NeverRebind extends RebindBehavior
{
    private static Logger logger = Logger.getLogger(NeverRebind.class);

    /**
     * Default constructor.
     */
    public NeverRebind()
    {
    }

    /**
     * The algorithm implementation for rebinding goes here.
     * @throws RemoteGrouterException to clients that want to handle an exception and
     * log it this exception is thrown (Runtime exception)
     */
    public void rebind(AbstractDestination dest)
    {
        logger.info("Rebinding using behavior implementation : " + this.getClass().getName());
        // We unbind and do nothing more
        dest.unbind();
        logger.debug("We unbinded from the destination, if any.");
        throw new RemoteGrouterException("Could not bind to JMS destination");
    }
}
