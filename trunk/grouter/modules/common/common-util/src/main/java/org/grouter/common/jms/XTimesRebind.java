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
 * Rebinds x number of times.
 *
 * @author Georges Polyzois
 */
public class XTimesRebind extends RebindBehavior
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(XTimesRebind.class);
    /** Counter for max number of retires to rebind. */
    private int numberOfTimes = 1;

    /**
     * Constructor.
     *
     * @param numberOfTimes int
     */
    public XTimesRebind(int numberOfTimes)
    {
        this.numberOfTimes = numberOfTimes;
    }

    /**
     * The algorithm implementation for rebinding goes here.
     */
    public void rebind(AbstractDestination dest)
    {
        logger.info("Rebinding using behavior implementation : " + this.getClass().getName());
        for (int i = 0; i < numberOfTimes; i++)
        {
            try
            {
                dest.unbind();
                dest.bind();
                logger.debug("We rebinded to the destination on attempt number " + numberOfTimes);
                return;
            } catch (RemoteGrouterException e)
            {
                //ignore
            }

        }
    }
}
