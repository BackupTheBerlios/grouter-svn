/**
 * FileReader.java
 */
package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.grouter.core.command.Message;
import org.grouter.core.config.ServiceNodeConfig;
import org.grouter.core.config.FileReaderConfig;


import java.io.File;
import java.util.concurrent.Callable;
import java.util.Queue;


/**
 * Creates commands an puts them on queue for further handling by
 * invokers and receivers.
 * 
 * @author Georges Polyzois
 */
public class FileReader extends AbstractReader implements Callable<String>
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(FileReader.class);
    private FileReaderConfig fileReaderConfig;

    private Queue queue;

    private File readFromFile;

    public FileReader(final FileReaderConfig fileReaderConfig, Queue queue, ServiceNodeConfig toDestinationConfig)
    {
        if(fileReaderConfig == null || queue == null || toDestinationConfig == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.fileReaderConfig = fileReaderConfig;
        this.queue = queue;
        //which type of commands should this servicenode worker handle
        command = getCommand(toDestinationConfig);
        readFromFile = fileReaderConfig.getFromDir();
    }

    /**
     * Forced by interface Callable.
     *
     * @return String
     * @throws Exception
     */
    public String call() throws Exception
    {
        read(fileReaderConfig);
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
     * @return Message[]
     */
    protected Message[] readFromSource()
    {
        logger.debug("Trying to read files from " + readFromFile);
        File[] curFiles = readFromFile.listFiles();
        if(curFiles.length == 0)
        {
            logger.debug("No files found.");
            return null;
        }
        Message[] arrMessages = new Message[curFiles.length];
        for (int i = 0; i < curFiles.length;i++)//fileCollectionSize; i++)
        {
            if (curFiles[i].length() == 0)
            {
                curFiles[i].delete();
                return null;
            }
            try
            {
                arrMessages[i] = new Message(getMessageAsString(curFiles[i]));
            }
            catch (Exception ex)
            {
                logger.info(ex,ex);
            }
            curFiles[i].delete();
        }
        return arrMessages;
    }

    void sendToDestination()
    {
        logger.debug("Putting cmd on queue" + command.toStringUsingReflection());
        queue.offer(command);

    }
}
