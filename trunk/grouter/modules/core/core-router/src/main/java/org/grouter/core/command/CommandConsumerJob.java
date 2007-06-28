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
 * (calling execute on concrete command).
 *
 * @author Georges Polyzois
 */
public class CommandConsumerJob implements Job
{
    private static Logger logger = Logger.getLogger(CommandConsumerJob.class);
    private BlockingQueue<AbstractCommand> blockingQueue;
    private final int TIMEOUT = 2;

    /**
     * Neede by Quartz scheduler framework.
     */
    public CommandConsumerJob()
    {
    }

    /**
     * Only used by ThreadPoolService
     *
     * @param queue
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

            //node = (Node) jobDataMap.get( "node" );
            this.blockingQueue = (BlockingQueue<AbstractCommand>) jobDataMap.get("queue");

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
