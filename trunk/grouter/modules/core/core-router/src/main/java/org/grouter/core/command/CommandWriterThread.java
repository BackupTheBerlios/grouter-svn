package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommandWriter;
import org.grouter.core.command.FileCommandWriter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Workerthreads are responsible for taking commands and executing them.
 *
 *
 * @author Georges Polyzois
 */
public class CommandWriterThread implements Runnable //Callable<Boolean>
{
    private static Logger logger = Logger.getLogger(CommandWriterThread.class);
    private BlockingQueue<AbstractCommandWriter> queue;
    private static final int TIMEOUT = 2;

    public CommandWriterThread(BlockingQueue<AbstractCommandWriter> queue)
    {
        this.queue = queue;
    }

    public Boolean call() throws Exception
    {
        logger.debug("Qeuue size" + queue.size());
        //take is blocking - an initial condition where no commandMessages are found in
        //the infolder e.g. will make the reader thread to block also.
        AbstractCommandWriter cmd = queue.poll(TIMEOUT, TimeUnit.SECONDS);//queue.take();
        if (cmd instanceof FileCommandWriter)
        {
            logger.debug("Got filewrite command. Qeuue size " + queue.size());
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
}
