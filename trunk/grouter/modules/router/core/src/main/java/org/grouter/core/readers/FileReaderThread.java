package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.Message;
import org.grouter.core.config.Node;

import java.io.File;
import java.util.concurrent.BlockingQueue;


/**
 * Creates commands an puts them on queue for further handling by consumers.
 *
 * @author Georges Polyzois
 */
public class FileReaderThread extends AbstractReader implements Runnable//implements Callable<String>
{
    private static Logger logger = Logger.getLogger(FileReaderThread.class);
    private Node node;
    private BlockingQueue<Command> queue;

    /**
     * Construcotr
     *
     * @param node
     * @param blockingQueue
     * @throws IllegalArgumentException if node == null || blockingQueue == null
     */
    public FileReaderThread(final Node node, BlockingQueue<Command> blockingQueue)
    {
        if (node == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.node = node;
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(node);

    }

    /**
     * Forced by interface Callable.
     *
     * @return String
     * @throws Exception
     */
    public String call() throws Exception
    {
        logger.debug(node.getId() + " + FileReaderThread executing");
        read(node);
        /*if(arrMessages==null)
        {
            logger.debug("null messages ................");
            return "";
        }
        else
        {
            command.setMessage(arrMessages);
            logger.debug("Putting cmd on queue" + command.toStringUsingReflection());
                    queue.offer(command);

        }*/
        return "";
    }

    /**
     * Forced by abstract method.
     *
     * @return Message[]
     */
    protected Message[] readFromSource()
    {
        logger.debug("Trying to read files from " + node.getInFolder().getInFolderPath());
        File[] curFiles = node.getInFolder().getInFolderPath().listFiles();
        if (curFiles == null || curFiles.length == 0)
        {
            logger.debug("No files found.");
            return null;
        }
        logger.debug("Found number of files: " + curFiles.length);

        Message[] arrMessages = new Message[curFiles.length];
        for (int i = 0; i < curFiles.length; i++)//fileCollectionSize; i++)
        {
            if (curFiles[i].length() == 0)
            {
                logger.debug("A folder..");
                //  curFiles[i].delete();
                //  return null;
                //return;
            }
            try
            {
                arrMessages[i] = new Message(getMessageAsString(curFiles[i]));
                logger.debug("Message : " + arrMessages[i].getMessage());
            }
            catch (Exception ex)
            {
                logger.info(ex, ex);
            }
            // curFiles[i].delete();
        }
        return arrMessages;
    }

    /**
     * Hand it over to the in memory queue.
     */
    void sendToConsumer()
    {
        logger.debug("Putting cmd on queue " + command.toStringUsingReflection());
        queue.offer(command);
    }

    /**
     * We are a thread - are we not...
     */
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
