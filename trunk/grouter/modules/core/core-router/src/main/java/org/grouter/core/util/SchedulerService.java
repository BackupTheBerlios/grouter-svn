package org.grouter.core.util;

import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandConsumerJob;
import org.grouter.core.readers.FileReaderJob;
import org.grouter.core.readers.FtpReaderJob;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.servicelayer.RouterService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.*;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
            logger.info("Scheduling node : " + node.getName());
            BlockingQueue<AbstractCommand> blockingQueue = new ArrayBlockingQueue<AbstractCommand>(QUEUE_CAPACITY);
            if (node.getInBound().getEndPointType().getId() == EndPointType.FILE_READER.getId())
            {
                JobDetail jobDetail = new JobDetail(node.getInBound().getId().toString(), getTriggerGroup(node), FileReaderJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, true), getTriggerGroup(node), node.getInBound().getScheduleCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }
            if ( node.getOutBound().getEndPointType().getId() == EndPointType.FILE_WRITER.getId())
            {
                JobDetail jobDetail = new JobDetail(node.getOutBound().getId().toString(), getTriggerGroup(node), CommandConsumerJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, false), getTriggerGroup(node), node.getOutBound().getScheduleCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }

            if( node.getInBound().getEndPointType().getId() == EndPointType.FTP_READER.getId())
            {

                JobDetail jobDetail = new JobDetail(node.getInBound().getId().toString(), getTriggerGroup(node), FtpReaderJob.class);
                jobDetail.getJobDataMap().put("node", node);
                jobDetail.getJobDataMap().put("queue", blockingQueue);
                CronTrigger cronTrigger = new CronTrigger(getTriggerName(node, true), getTriggerGroup(node), node.getInBound().getScheduleCron());
                scheduler.scheduleJob(jobDetail, cronTrigger);

            }

        }

        // Start the Scheduler running
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


    public void rescheduleNode(Node node) throws Exception
    {
        logger.info("Rescheduling node : " + node.getName());
        CronTrigger cronTriggerIn = (CronTrigger) scheduler.getTrigger(getTriggerName(node, true), getTriggerGroup(node));
        cronTriggerIn.setCronExpression(node.getInBound().getScheduleCron());
        scheduler.rescheduleJob(getTriggerName(node, true), getTriggerGroup(node), cronTriggerIn);

        CronTrigger cronTriggerOut = (CronTrigger) scheduler.getTrigger(getTriggerName(node, false), getTriggerGroup(node));
        cronTriggerOut.setCronExpression(node.getOutBound().getScheduleCron());
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
