package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.FileWriterCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Workerthreads are responsible for taking commands and executing them.
 */
public class CommandConsumerThread implements Runnable //Callable<Boolean>
{
    private static Logger logger = Logger.getLogger(CommandConsumerThread.class);
    private BlockingQueue<Command> queue;
    private static final int TIMEOUT = 2;

    public CommandConsumerThread(BlockingQueue<Command> queue)
    {
        this.queue = queue;
    }

    public Boolean call() throws Exception
    {
        logger.debug("Qeuue size" + queue.size());
        //take is blocking - an initial condition where no commandMessages are found in
        //the infolder e.g. will make the reader thread to block also.
        Command cmd = queue.poll(TIMEOUT, TimeUnit.SECONDS);//queue.take();
        if (cmd instanceof FileWriterCommand)
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
