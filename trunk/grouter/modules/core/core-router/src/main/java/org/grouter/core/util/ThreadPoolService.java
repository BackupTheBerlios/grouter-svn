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

package org.grouter.core.util;

import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandConsumerJob;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPointType;

import java.util.concurrent.*;
import java.util.Set;

/**
 * What's the right size for a thread pool, assuming the goal is to keep the
 * processors fully utilized? Amdahl's law gives us a good approximate formula, if
 * we know how many processors our system has and the approximate ratio of
 * compute time to wait time for the tasks.
 * Let WT represent the average wait time per task, and ST the average service
 * time (computation time) per task. Then WT/ST is the percentage of time a task
 * spends waiting. For an N processor system, we would want to have
 * approximately N*(1+WT/ST) threads in the pool.
 *
 *
 * @deprecated 
 * @author Georges Polyzois
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class ThreadPoolService
{
    private static Logger logger = Logger.getLogger(ThreadPoolService.class);
    private ConcurrentMap map = new ConcurrentHashMap();
    private ScheduledExecutorService scheduler;
    private BlockingQueue<AbstractCommand> blockingQueue;
    private static final long INITIAL_DELAY = 1000;
    private static final int CAPACITY = 1000;
    private static final int POLL = 5000;
    private static final int JMSTHREAD_POLLQUEUE_INTERVALL = POLL;

    /**
     * Consturctor
     */
    public ThreadPoolService()
    {
    }


    /**
     * Initialize threads.
     *
     * @param nodes
     * @throws IllegalArgumentException if nodes == null
     */
    public void initNodeThreadScheduling(Set nodes)
    {
        if (nodes == null || nodes == null)
        {
            throw new IllegalArgumentException("Nodes can not be null!!");
        }
        logger.info("Found " + nodes.size() + " number of nodes. Init scheduler executor service!");

        //fire off...

        start(nodes);
    }


    /**
     * Initialize threads.
     *
     * @throws IllegalArgumentException if nodes == null
     */

/*
    public void initNodeThreadScheduling(GrouterConfig grouterConfig)
    {
        NodeConfig[] nodes = grouterConfig.getNodes();
        if (grouterConfig == null || nodes == null)
        {
            throw new IllegalArgumentException("Nodes can not be null!!");
        }
        logger.info("Found " + nodes.length + " number of nodes. Init scheduler executor service!");

        //fire off...
        start(nodes);

        // todo add this to config - if we should send message to listener
        startJMSSEnderThread(grouterConfig);
    }

    private void startJMSSEnderThread(GrouterConfig grouterConfig)
    {
        JMSDestinationSenderThread senderThread = new JMSDestinationSenderThread(grouterConfig);
        scheduler.scheduleAtFixedRate(senderThread, INITIAL_DELAY, JMSTHREAD_POLLQUEUE_INTERVALL, TimeUnit.MILLISECONDS);


    }
                                         */

    private void start(Set<Node> nodes)
    {
        scheduler = Executors.newScheduledThreadPool( nodes.size() );

        for (Node node : nodes)
        {
            blockingQueue = new ArrayBlockingQueue<AbstractCommand>(CAPACITY);
            if( node.getInBound().getEndPointType().getId() == EndPointType.FILE_READER.getId() )
            {
//                FileReaderThread fileReaderThread = new FileReaderThread(node, blockingQueue);
//                scheduler.scheduleAtFixedRate(fileReaderThread, INITIAL_DELAY, POLL, TimeUnit.MILLISECONDS);
            }

            CommandConsumerJob commandConsumerThread = new CommandConsumerJob(blockingQueue);
//            scheduler.scheduleAtFixedRate(commandConsumerThread, INITIAL_DELAY, POLL, TimeUnit.MILLISECONDS);
        }
    }



    /**
     * Delegate to ScheduledExecutorService for a shutdown of node threads.
     */
    public void shutdown()
    {
        scheduler.shutdown();
    }
}
