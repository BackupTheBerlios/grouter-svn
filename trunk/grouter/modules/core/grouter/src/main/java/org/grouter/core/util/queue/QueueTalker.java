package org.grouter.core.util.queue;

import org.apache.log4j.Logger;
import org.grouter.core.command.Command;

/**
 * Creates connection/session to queue and send commands to queue
 * for further processing.
 *
 */
public class QueueTalker
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(QueueTalker.class);
    CommandQueueFascade queue = CommandQueueFascade.getInstance();

	
    public void sendToQueue(Command com)
    {
        logger.debug("Sending to queue");
        queue.push(com);

    }
}
