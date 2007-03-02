package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.grouter.core.command.Command;
import org.grouter.core.command.CommandMessage;
import org.grouter.core.config.NodeConfig;
import org.grouter.common.guid.GuidGenerator;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;


/**
 * Creates commands an puts them on queue for further handling by consumers.
 *
 * @author Georges Polyzois
 */
public class FileReaderThread extends AbstractReader implements Runnable//implements Callable<String>
{
    private static Logger logger = Logger.getLogger(FileReaderThread.class);
    //private NodeConfig nodeConfig;
    private BlockingQueue<Command> queue;
    private NotFileFilter notFileFilter;
    private WildcardFilter wildcardFilter;
    private FileFilter fileFilter;

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
        createFilter(nodeConfig);

    }

    /**
     * A null filter means we do not have a filter and File.listfiles will take all files present in a dir.
     *
     * @param nodeConfig
     */
    private void createFilter(NodeConfig nodeConfig)
    {
        String filter = nodeConfig.getInFolderConfig().getFilterConfig().getFilter();
        if (filter != null && filter.equalsIgnoreCase(""))
        {
            return;
        }

        this.wildcardFilter = new WildcardFilter(nodeConfig.getInFolderConfig().getFilterConfig().getFilter());
        this.notFileFilter = new NotFileFilter(wildcardFilter);
        this.fileFilter = notFileFilter;
    }


    /**
     * Forced by abstract method.
     *
     * @return CommandMessage[]
     */
    protected CommandMessage[] readFromSource()
    {
        logger.debug("Trying to read files from " + nodeConfig.getInFolderConfig().getInPath());
        File[] curFiles = nodeConfig.getInFolderConfig().getInPath().listFiles(fileFilter);
        if (curFiles == null || curFiles.length == 0)
        {
            logger.debug("No files found.");
            return null;
        }
        logger.debug("Found number of files: " + curFiles.length);

        CommandMessage[] arrCommandMessages = new CommandMessage[curFiles.length];
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
                arrCommandMessages[i] = new CommandMessage(readFileToString(curFiles[i]));
                if (nodeConfig.isCreateuniquename())
                {
                    arrCommandMessages[i].setId(GuidGenerator.getInstance().getGUID());
                }

                logger.debug("CommandMessage : " + arrCommandMessages[i].getMessage());
            }
            catch (Exception ex)
            {
                logger.info(ex, ex);
            }
            // curFiles[i].delete();
        }
        return arrCommandMessages;
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
            read();
        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }
}
