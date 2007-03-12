package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Node;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Workerthreads are responsible for taking commands and executing them.
 *
 *
 * @author Georges Polyzois
 */
public class CommandWriterThread implements Job, Runnable
{
    private static Logger logger = Logger.getLogger(CommandWriterThread.class);
    private BlockingQueue<AbstractCommandWriter> blockingQueue;
    private static final int TIMEOUT = 2;
    private Node node;


    /**
     * Neede by Quartz scheduler framework.
     */
    public CommandWriterThread()
    {
    }

    /**
     * Only used by ThreadPoolService
     *
     * @deprecated
     * @param queue
     */
    public CommandWriterThread(BlockingQueue<AbstractCommandWriter> queue)
    {
        this.blockingQueue = queue;
    }

    public Boolean call() throws Exception
    {
        logger.debug("Queue size is : " + blockingQueue.size());

        //take is blocking - an initial condition where no commandMessages are found in
        //the infolder e.g. will make the reader thread to block also.
        AbstractCommandWriter cmd = blockingQueue.poll(TIMEOUT, TimeUnit.SECONDS);//queue.take();
        if (cmd instanceof FileCommandWriter)
        {
            cmd.execute();
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void run()
    {
        try
        {
            call();
        } catch (Exception e)
        {
            logger.error(e,e);
        }
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap jobDataMap = context.getMergedJobDataMap();

        //node = (Node) jobDataMap.get( "node" );
        this.blockingQueue = (BlockingQueue<AbstractCommandWriter>) jobDataMap.get( "queue" );

        try
        {
            call();
        } catch (Exception e)
        {
            logger.error(e,e);
        }
    }
}
