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

package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;

import java.util.concurrent.BlockingQueue;
import static java.util.concurrent.TimeUnit.*;

/**
 * A CommandConsumerJob is responsible for consuming commands (popping from queue) and executing them
 * (calling execute on a given concrete command).
 *
 * @author Georges Polyzois
 */
public class CommandConsumerJob implements Job
{
    private static Logger logger = Logger.getLogger(CommandConsumerJob.class);
    private BlockingQueue<AbstractCommand> blockingQueue;
    private final int TIMEOUT = 2;
    private static final String QUEUE = "queue";

    /**
     * Neede by Quartz scheduler framework.
     */
    public CommandConsumerJob()
    {
    }

    /**
     * Only used by ThreadPoolService
     *
     * @param queue   a queue to pop commands from
     */
    public CommandConsumerJob(BlockingQueue<AbstractCommand> queue)
    {
        this.blockingQueue = queue;
    }


    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        if (this.blockingQueue == null)
        {
            JobDataMap jobDataMap = context.getMergedJobDataMap();
            this.blockingQueue = (BlockingQueue<AbstractCommand>) jobDataMap.get(QUEUE);

        }

        logger.debug("Queue size is : " + blockingQueue.size());

        //take is blocking - an initial condition where no commandMessages are found in
        //the infolder e.g. will make the reader thread to block also.
        //queue.take();
        AbstractCommand cmd = null;
        try
        {
            cmd = blockingQueue.poll(TIMEOUT, SECONDS);
            if (cmd instanceof FileWriteCommand)
            {
                cmd.execute();
            }
        } catch (InterruptedException e)
        {
            logger.error(e, e);
        }

    }
}
