package org.grouter.core.util.queue;

import org.apache.log4j.Logger;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.UnboundedFifoBuffer;
//import org.iris.server.messagehandlers.email.*;
import org.grouter.core.command.Command;

/**
 * Fascade round a FIFO queue implementation.
 * 
 * @author Georges Polyzois
 */
public class CommandQueueFascade
{
	/** Logger. */
    static Logger logger = Logger.getLogger(CommandQueueFascade.class.getName());
	/** Implementation queue from Apache. */
    Buffer fifoQueue = BufferUtils.synchronizedBuffer(new UnboundedFifoBuffer());
	/** Singeltoninstance. */
    private static CommandQueueFascade instance = null;

	/**
	 * Hidden.
	 */
    private CommandQueueFascade()
    {
    }
	
	/**
	 * Singelton.
	 * @return
	 */
    public static CommandQueueFascade getInstance()
    {
        if(instance == null)
        {
            instance = new CommandQueueFascade();
        }
        return instance;
    }

	/** 
	 * Push one command on queue.
	 * @param cmd
	 */
    public void push(Command cmd)
    {
        fifoQueue.add(cmd);
    }

	/**
	 * Pop one cómmand.
	 * @return
	 */
    public Command pop()
    {
        Object obj = null;
        try
        {
            obj = fifoQueue.get();
        }
        catch (Exception ex)
        {
            logger.debug("Command queue empty");
        }
        Command item = null;
        if (obj != null)
        {
            item = (Command) obj;
            fifoQueue.remove(obj);
        }
        return item;
    }

	/**
	 * Pop collection of commands.
	 * @param maxSize
	 * @return
	 */
    public Command[] popCollection(int maxSize)
    {
        int collSize = maxSize;
        if (maxSize > fifoQueue.size())
        {
            collSize = fifoQueue.size();
        }
        Command[] items = new Command[collSize];
        for (int i = 0; i < collSize; i++)
        {
            items[i] = pop();
        }
        return items;
    }

	/**
	 * Get implemenation FIFO queue.
	 * @return
	 */
    public Buffer getImplementationQueue()
    {
        return fifoQueue;
    }
}


