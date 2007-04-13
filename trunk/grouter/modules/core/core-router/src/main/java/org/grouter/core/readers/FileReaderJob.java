package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.apache.commons.io.FileUtils;
import org.grouter.core.command.AbstractCommandWriter;
import org.grouter.core.command.CommandHolder;
import org.grouter.domain.entities.Node;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;

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
public class FileReaderJob extends AbstractReader 
{
    private static Logger logger = Logger.getLogger(FileReaderJob.class);
    //private NodeConfig node;
    private BlockingQueue<AbstractCommandWriter> queue;
    private NotFileFilter notFileFilter;
    private WildcardFilter wildcardFilter;
    private FileFilter fileFilter;
    private FileReaderHelper fileReaderHelper;
    private static final String NODE = "node";
    private static final String QUEUE = "queue";


    /**
     * Empty - needed by Quartz framework.
     */
    public FileReaderJob()
    {
    }

    private void init( final Node node, BlockingQueue<AbstractCommandWriter> blockingQueue )
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


    /**
     * A null filter means we do not have a filter and File.listfiles will take all files present in a dir.
     *
     * @param
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


    @Override
    protected List<CommandHolder> readFromSource()
    {
        logger.info("Reading files from " + node.getInBound().getUri());
        //File[] curFiles = node.getInFolderConfig().getInPath().listFiles(fileFilter);

        return FileReaderHelper.getCommands( node );
    }




    /**
     * Hand it over to the in memory queue.
     */
    void pushToQueue()
    {
        logger.debug("Putting cmd on queue " + command.toStringUsingReflection());
        queue.offer(command);
    }

    void validate(Node node)
    {
        // TODO validate 
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

    public void setJobExecutionContext( JobExecutionContext jobExecutionContext )
    {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        node = (Node) jobDataMap.get(NODE);
        BlockingQueue<AbstractCommandWriter> blockingQueue = (BlockingQueue<AbstractCommandWriter>) jobDataMap.get(QUEUE);
        init(node, blockingQueue);
    }

    public void execute(JobExecutionContext jobExecutionContext) 
    {
        setJobExecutionContext( jobExecutionContext );
        execute();
    }



    public void setQueue(BlockingQueue<AbstractCommandWriter> queue)
    {
        this.queue = queue;
    }
    
}
