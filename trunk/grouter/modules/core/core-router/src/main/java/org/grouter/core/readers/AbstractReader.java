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


import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandFactory;
import org.grouter.core.command.CommandMessage;
import org.grouter.core.exception.ValidationException;
import org.grouter.domain.entities.Node;
import org.quartz.Job;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobDataMap;
import org.apache.commons.io.FileUtils;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.io.File;
import java.io.IOException;


/**
 * Readers should inherit from this class. It defines some abstract methods and a template
 * pattern enforce onto all Readers extending this class.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractReader implements Job, InterruptableJob
{
    AbstractCommand command;
    // How much of the message to be persisted
    protected static final int MESSAGE_LENGTH = 100;
    Node node;
    protected BlockingQueue<AbstractCommand> queue;
    protected static final String NODE = "node";
    protected static final String QUEUE = "queue";

    public void setNode(Node node)
    {
        this.node = node;
    }

    /**
     * Read data from source and store uri in {@link CommandMessage} instance along with an extract of the message.
     *
     * @return list of {@link CommandMessage} instances
     */
    abstract List<CommandMessage> readFromSource();

    /**
     * Push to inmemory queue that we have read a certain number of inbound messages from source.
     */
    abstract void pushToIntMemoryQueue();

    /**
     * Enforce consistent behaviour in subclasses  (template method pattern)
     */
    final protected void execute()
    {
        validate( node );
        List<CommandMessage> arrCommandMessages = readFromSource();
        if (arrCommandMessages != null && arrCommandMessages.size() > 0)
        {
            command.setMessages(arrCommandMessages);
            pushToIntMemoryQueue();
        }
    }

    /**
     * Find out what type of commands this node reader should handle
     *
     * @param node a {@link Node}
     * @return a command
     */
    protected AbstractCommand getCommand(final Node node)
    {
        return CommandFactory.getCommand( node );
    }

    /**
     * All subclasses need to be able to validate them selves.
     *
     * @param node validate the {@link Node}
     * @throws  org.grouter.core.exception.ValidationException if validation fails an exception is reaised (Runtime).
     */
    abstract void validate(Node node) throws ValidationException;


    /**
     * Initialize the node and everything it needs, like endpoint connections.
     * @param node a {@link Node}
     * @param blockingQueue a queue
     */
    abstract void init(final Node node, BlockingQueue<AbstractCommand> blockingQueue);



    /**
     * Set contect upon every job execution. The {@link Node } and the internal queue is set
     * retrieven from the internal map of Quartz.
     * @param jobExecutionContext Quartz context param
     */
    public void setJobExecutionContext(JobExecutionContext jobExecutionContext)
    {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        node = (Node) jobDataMap.get("node");
        BlockingQueue<AbstractCommand> blockingQueue = (BlockingQueue<AbstractCommand>) jobDataMap.get("queue");
        init(node, blockingQueue);
    }

    /**
     * Extract a part of the message.
     * @param currentFile the internal file to extract message from
     * @return a String representing the message
     * @throws IOException if exception reading internalfile
     */
    protected static String getMessage(File currentFile)
            throws IOException
    {
        String message = FileUtils.readFileToString(new File(currentFile.getPath()), "UTF-8");
        return message.substring(0, MESSAGE_LENGTH);

    }
}
