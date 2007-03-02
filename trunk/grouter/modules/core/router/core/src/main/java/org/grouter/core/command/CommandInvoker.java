package org.grouter.core.command;

import org.apache.log4j.Logger;


/**
 * Invoker in the Command pattern terminology.
 */
public class CommandInvoker
{
    private static Logger logger = Logger.getLogger(CommandInvoker.class);

    /**
     * Take a command an execute it.
     * @param command Command to be executed.
     */
    public void handleCommand(Command command)
    {
        logger.debug("Invoker received command");
        command.execute();
    }
}