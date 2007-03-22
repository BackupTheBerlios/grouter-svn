package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Node;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.*;

/**
 * Workerthreads are responsible for taking commands and executing them.
 *
 * @author Georges Polyzois
 */
public class CommandWriterJob implements Job
{
    private static Logger logger = Logger.getLogger(CommandWriterJob.class);
    private BlockingQueue<AbstractCommandWriter> blockingQueue;
    private final int TIMEOUT = 2;

    /**
     * Neede by Quartz scheduler framework.
     */
    public CommandWriterJob()
    {
    }

    /**
     * Only used by ThreadPoolService
     *
     * @param queue
     * @deprecated
     */
    public CommandWriterJob(BlockingQueue<AbstractCommandWriter> queue)
    {
        this.blockingQueue = queue;
    }


    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        //node = (Node) jobDataMap.get( "node" );
        this.blockingQueue = (BlockingQueue<AbstractCommandWriter>) jobDataMap.get("queue");


        logger.debug("Queue size is : " + blockingQueue.size());

        //take is blocking - an initial condition where no commandMessages are found in
        //the infolder e.g. will make the reader thread to block also.
        //queue.take();
        AbstractCommandWriter cmd = null;
        try
        {
            cmd = blockingQueue.poll(TIMEOUT, SECONDS);
            if (cmd instanceof FileCommandWriter)
            {
                cmd.execute();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
