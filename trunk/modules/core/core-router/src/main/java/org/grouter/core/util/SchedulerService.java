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

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandConsumerJob;
import org.grouter.core.readers.FileReaderJob;
import org.grouter.core.readers.FtpReaderJob;
import org.grouter.core.readers.HttpReaderJob;
import org.grouter.core.readers.JmsReaderJob;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.NodeStatus;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * What's the right size for a thread pool, assuming the goal is to keep the
 * processors fully utilized? Amdahl's law gives us a good approximate formula, if
 * we know how many processors our system has and the approximate ratio of
 * compute time to wait time for the tasks.
 * Let WT represent the average wait time per task, and ST the average service
 * time (computation time) per task. Then WT/ST is the percentage of time a task
 * spends waiting. For an N processor system, we would want to have
 * approximately N*(1+WT/ST) threads in the pool.
 * <p/>
 * <p/>
 * We are using a Fascade round the Quartz scheduler framework.
 *
 * @author Georges Polyzois
 */
public class SchedulerService
{
    private static Logger logger = Logger.getLogger(SchedulerService.class);
    private static final int QUEUE_CAPACITY = 1000;
    Scheduler scheduler;
    private Set<Node> nodes;
    private static final String GROUTER = "grouter";


    /**
     * Consturctor
     */
    public SchedulerService(Set<Node> nodes)
    {
        Validate.noNullElements(nodes, "Can not handle null nodes!!");

        this.nodes = nodes;
        for (Node node : nodes)
        {
            node.setNodeStatus( NodeStatus.NOTSTARTED );
        }

        try
        {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e)
        {
            logger.error("Could not create scheduler frm SchedulerFactory", e);
            throw new RuntimeException("Could not create scheduler from SchedulerFactory", e);
        }



    }

    /**
     * Start the scheduler using all available nodes.
     *
     * @throws Exception
     */
    public void startAllNodes() throws Exception
    {
        for (Node node : this.nodes)
        {
            logger.info("Scheduling node : " + node.getDisplayName());
            BlockingQueue<AbstractCommand> blockingQueue = new ArrayBlockingQueue<AbstractCommand>(QUEUE_CAPACITY);
            if (node.getInBound().getEndPointType().getId() == EndPointType.FILE_READER.getId())
            {
                JobDetail jobDetail = new JobDetail(node.getInBound().getId().toString(), getTriggerGroup(node), FileReaderJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, true), getTriggerGroup(node), node.getInBound().getCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
            if (node.getInBound().getEndPointType().getId() == EndPointType.FTP_READER.getId())
            {

                JobDetail jobDetail = new JobDetail(node.getInBound().getId().toString(), getTriggerGroup(node), FtpReaderJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, true), getTriggerGroup(node), node.getInBound().getCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
            if (node.getInBound().getEndPointType().getId() == EndPointType.JMS_READER.getId())
            {
                JobDetail jobDetail = new JobDetail(node.getInBound().getId().toString(), getTriggerGroup(node), JmsReaderJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, true), getTriggerGroup(node), node.getInBound().getCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);

            }


            if (node.getInBound().getEndPointType().getId() == EndPointType.HTTP_READER.getId())
            {
                JobDetail jobDetail = new JobDetail(node.getInBound().getId().toString(), getTriggerGroup(node), HttpReaderJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, true), getTriggerGroup(node), node.getInBound().getCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);

            }

            // For all WRITERS we need only create this
            if ( node.getOutBound().getEndPointType().getId() == EndPointType.FILE_WRITER.getId() || 
                 node.getOutBound().getEndPointType().getId() == EndPointType.JMS_WRITER.getId()  ||
                 node.getOutBound().getEndPointType().getId() == EndPointType.FTP_WRITER.getId()   )
            {
                JobDetail jobDetail = new JobDetail(node.getOutBound().getId().toString(), getTriggerGroup(node), CommandConsumerJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, false), getTriggerGroup(node), node.getOutBound().getCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
            node.setNodeStatus(NodeStatus.SCHEDULED_TO_START);
        }

        // Start the Scheduler
        scheduler.start();
    }

    private String getTriggerGroup(Node node)
    {
        String group = GROUTER + "_" + node.getId();
        logger.debug("Trigger group :" + group);
        return group;
    }


    private String getTriggerName(Node node, boolean inbound)
    {
        String triggerName;
        if (inbound)
        {
            triggerName = node.getId() + "_" + node.getInBound().getId();
        } else
        {
            triggerName = node.getId() + "_" + node.getOutBound().getId();
        }
        logger.debug("Trigger name:" + triggerName);
        return triggerName;
    }


    /**
     * Shudtowns the scheduler.
     */
    public void shutdown() throws Exception
    {
        scheduler.shutdown();
    }


    /**
     * Stop a node from running - both inbound and outbound endpoints of the Node are stopped.  In Quartz this
     * means that we remove the job from the scheduler.
     *
     * @param node the node which we want to stop
     */
    public void stop(Node node) throws Exception
    {
        //scheduler.interrupt(getTriggerName(node, true), getTriggerGroup(node));  -> callback to job implmeenting Interruptable.. interface for gracefully stoping
        //scheduler.interrupt(getTriggerName(node, false), getTriggerGroup(node));
        scheduler.unscheduleJob(getTriggerName(node, true), getTriggerGroup(node));
        scheduler.unscheduleJob(getTriggerName(node, false), getTriggerGroup(node));

        logStatus();
    }


    /**
     * Reschedules a job.
     * @param node node to be rescheduled
     * @throws Exception
     */
    public void rescheduleNode(Node node) throws Exception
    {
        logger.info("Rescheduling node : " + node.getDisplayName());
        CronTrigger cronTriggerIn = (CronTrigger) scheduler.getTrigger(getTriggerName(node, true), getTriggerGroup(node));
        cronTriggerIn.setCronExpression(node.getInBound().getCron());
        scheduler.rescheduleJob(getTriggerName(node, true), getTriggerGroup(node), cronTriggerIn);

        CronTrigger cronTriggerOut = (CronTrigger) scheduler.getTrigger(getTriggerName(node, false), getTriggerGroup(node));
        cronTriggerOut.setCronExpression(node.getOutBound().getCron());
        scheduler.rescheduleJob(getTriggerName(node, false), getTriggerGroup(node), cronTriggerOut);
    }


    /**
     * Util for printing out some statistics.
     */
    private void logStatus()
    {
        try
        {
            logger.info("Scheduler meta info :" + scheduler.getMetaData());
            List<JobExecutionContext> list = scheduler.getCurrentlyExecutingJobs();
            logger.info("Currently number of running jobs:" + list.size());
            for (JobExecutionContext jobExecutionContext : list)
            {
                logger.info("Job running :" + jobExecutionContext.getJobDetail().getFullName());
            }

            String[] groups = scheduler.getJobGroupNames();
            logger.info("Registered groups and jobs for each group");
            for (String group : groups)
            {
                String[] jobnames = scheduler.getJobNames(group);
                logger.info("Jobnames for group :" + group + " -> " + ToStringBuilder.reflectionToString(jobnames));
            }
        } catch (SchedulerException e)
        {
            //ignore
        }
    }


    public void setNodes(Set<Node> nodes)
    {
        this.nodes = nodes;
    }
}
