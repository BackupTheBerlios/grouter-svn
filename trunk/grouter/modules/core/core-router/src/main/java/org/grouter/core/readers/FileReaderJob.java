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

package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandMessage;
import org.grouter.core.exception.ValidationException;
import org.grouter.domain.entities.Node;
import org.quartz.JobExecutionContext;
import org.quartz.UnableToInterruptJobException;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.List;


/**
 * Creates commands an puts them on queue for further handling by consumers.
 *
 * @author Georges Polyzois
 */
public class FileReaderJob extends AbstractReader
{
    private static Logger logger = Logger.getLogger(FileReaderJob.class);

    /**
     * Empty - needed by Quartz framework.
     */
    public FileReaderJob()
    {
    }

    void init(final Node node, BlockingQueue<AbstractCommand> blockingQueue)
    {
        if (node == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.node = node;
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(node);

       
    }

    @Override
    protected List<CommandMessage> readFromSource()
    {
        logger.info(node.getId() + " is reading files from " + node.getInBound().getUri());
        return FileReaderHelper.getCommands(node);
    }


    @Override
    void pushToIntMemoryQueue()
    {
        logger.debug("Putting cmd on queue " + command.toStringUsingReflection());
        queue.offer(command);
    }

    @Override
    void validate(Node node)
    {
        File file = new File(node.getInBound().getUri());
        if (!file.isDirectory())
        {
            throw new ValidationException("Path does not exist for :" + node.getInBound().getUri());
        }
    }

    /**
     * We are a thread - are we not...
     */
    public void run()
    {
        try
        {
            execute();
        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }


    public void execute(JobExecutionContext jobExecutionContext)
    {
        setJobExecutionContext(jobExecutionContext);
        execute();
    }


    public void setQueue(BlockingQueue<AbstractCommand> queue)
    {
        this.queue = queue;
    }

    public void interrupt() throws UnableToInterruptJobException
    {
        logger.info(node.getId() + " got request to stop");
    }
}
