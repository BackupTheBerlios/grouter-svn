package org.grouter.core.util.queue;

import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.CommandInvoker;

import java.util.TimerTask;

/**
 * Listenes for commands
 * from the queue and calls execute on the command using an Invoker.
 * It is the receivers responsibility to do the actual execution.
 *
 */
public class QueueListener extends TimerTask
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(QueueListener.class);
    CommandQueueFascade comQueue = CommandQueueFascade.getInstance();
    CommandInvoker invoker = new CommandInvoker();

    public void run()
    {
        logger.debug("Popping command from queue");
        Command com = comQueue.pop();
        if (com!=null)
        {
            logger.debug("Received command");
            invoker.handleCommand(com);
        }
    }
}
