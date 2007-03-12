package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.grouter.core.command.AbstractCommandWriter;
import org.grouter.core.command.CommandHolder;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.Node;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.ArrayList;


/**
 * Creates commands an puts them on queue for further handling by consumers.
 *
 * @author Georges Polyzois
 */
public class FileReaderThread extends AbstractReader implements Runnable
{
    private static Logger logger = Logger.getLogger(FileReaderThread.class);
    //private NodeConfig node;
    private BlockingQueue<AbstractCommandWriter> queue;
    private NotFileFilter notFileFilter;
    private WildcardFilter wildcardFilter;
    private FileFilter fileFilter;

    /**
     * Construcotr
     *
     * @param node
     * @param blockingQueue
     * @throws IllegalArgumentException if node == null || blockingQueue == null
     */
    public FileReaderThread(final Node node, BlockingQueue<AbstractCommandWriter> blockingQueue)
    {
        if ( node == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.node = node;                 
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(node);
 //       createFilter(node);
    }


    public FileReaderThread(EndPoint inbound, BlockingQueue<AbstractCommandWriter> blockingQueue, EndPoint outBound)
    {
        Validate.notNull(inbound, "A null EndPoint can not be used");
        Validate.notNull(blockingQueue, "A null blockingqueue can not be used");

        this.endPoint = inbound;
        this.queue = blockingQueue;
        command = getCommand2(outBound);
    }


    /**
     * A null filter means we do not have a filter and File.listfiles will take all files present in a dir.
     *
     * @param node
     */
/*    private void createFilter(Node node)
    {
        String filter = node.getInFolderConfig().getFilterConfig().getFilter();
        if (filter != null && filter.equalsIgnoreCase(""))
        {
            return;
        }

        this.wildcardFilter = new WildcardFilter(node.getInFolderConfig().getFilterConfig().getFilter());
        this.notFileFilter = new NotFileFilter(wildcardFilter);
        this.fileFilter = notFileFilter;
    }
*/

    /**
     * Forced by abstract method.
     *
     * @return List<CommandHolder>
     */
    protected List<CommandHolder> readFromSource()
    {
        logger.debug("Trying to execute files from " + endPoint.getUri());
        //File[] curFiles = node.getInFolderConfig().getInPath().listFiles(fileFilter);
        File inFolder = new File(endPoint.getUri());
        File[] curFiles = inFolder.listFiles(fileFilter);  // TODO refactor to use generic Filter

        if (curFiles == null || curFiles.length == 0)
        {
            logger.debug("No files found.");
            return null;
        }

        logger.debug("Found number of files: " + curFiles.length);

        List<CommandHolder> commandMessages = new ArrayList<CommandHolder>(curFiles.length);
        for (File curFile : curFiles)
        {
            if (curFile.isDirectory())
            {
                logger.debug("A folder... removing it.");
                try
                {
                    FileUtils.forceDelete( curFile );
                } catch (IOException e)
                {
                    logger.info("Could not delete foder", e);
                }
            } else
            {
                try
                {
                    String message = FileUtils.readFileToString(new File(curFile.getPath()), "UTF-8");
                    CommandHolder cmdHolder = new CommandHolder(message);
                    commandMessages.add(cmdHolder);

                    // remove file from infolder
                    curFile.delete();
                }
                catch (Exception ex)
                {
                    logger.info(ex, ex);
                }
            }
        }
        return commandMessages;
    }

    /**
     * Hand it over to the in memory queue.
     */
    void pushToQueue()
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
            execute();
        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }


    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
    }
}
