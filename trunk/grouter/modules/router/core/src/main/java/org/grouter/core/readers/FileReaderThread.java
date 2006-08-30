package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.Message;
import org.grouter.core.config.NodeConfig;

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
    private NodeConfig nodeConfig;
    private BlockingQueue<Command> queue;

    /**
     * Construcotr
     *
     * @param nodeConfig
     * @param blockingQueue
     * @throws IllegalArgumentException if nodeConfig == null || blockingQueue == null
     */
    public FileReaderThread(final NodeConfig nodeConfig, BlockingQueue<Command> blockingQueue)
    {
        if (nodeConfig == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.nodeConfig = nodeConfig;
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(nodeConfig);

    }

    /**
     * Forced by interface Callable.
     *
     * @return String
     * @throws Exception
     */
    public String call() throws Exception
    {
        logger.debug(nodeConfig.getId() + " + FileReaderThread executing");
        read(nodeConfig);
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
        logger.debug("Trying to read files from " + nodeConfig.getInFolder().getInFolderPath());
        File[] curFiles = nodeConfig.getInFolder().getInFolderPath().listFiles();
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
