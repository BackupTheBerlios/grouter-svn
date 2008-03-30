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
import java.util.concurrent.TimeUnit;

/**
 * EternalRebind will continue to rebind "4ever". It will gracefully
 * wait longer end longer before rebinding again up till a MAXWAITFORREBIND
 * time period.
 *
 * @author Georges Polyzois
 */
public class EternalRebind extends RebindBehavior
{
    private static Logger logger = Logger.getLogger(EternalRebind.class);
    private int rebindCounter = 0;
    private final static int MAXWAITFORREBIND = 10;

    public EternalRebind()
    {
    }

    /**
     * The algorithm implementation for rebinding goes here.
     */
    public synchronized void rebind(AbstractDestination dest)
    {
        logger.info("Rebinding using behavior implementation : " + this.getClass().getName());
        boolean isBoundToDestination = false;
        while(!isBoundToDestination)
        {
            hold();
            dest.unbind();
            dest.bind();
            isBoundToDestination = true;
            rebindCounter = 0;
        }
    }

    /**
     * Hold increasingly up until MAXWAITFORREBIND seconds.
     */
    private void hold()
    {
        long holdTime = rebindCounter;
        try
        {
            TimeUnit.SECONDS.sleep(holdTime);
        } catch (InterruptedException ex)
        {
            //do nothing
        }
        if(rebindCounter < MAXWAITFORREBIND)
        {
            rebindCounter++;
        }
    }
}
