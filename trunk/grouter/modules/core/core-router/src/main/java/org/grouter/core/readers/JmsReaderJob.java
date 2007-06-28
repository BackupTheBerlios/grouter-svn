package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.WildcardFilter;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandMessage;
import org.grouter.domain.entities.Node;
import org.grouter.common.jms.AbstractDestination;
import org.grouter.common.jms.EternalRebind;
import org.grouter.common.jms.QueueListenerDestination;
import org.quartz.JobExecutionContext;
import org.quartz.JobDataMap;
import org.quartz.UnableToInterruptJobException;

import javax.jms.MessageListener;
import javax.jms.Message;
import java.util.concurrent.BlockingQueue;
import java.util.List;
import java.util.Map;

/**
 * Connects to JMS server. Then we create commandmessages
 * and put them on queue for further processing.
 *
 *
 * TODO: What kind of messages are we listening to? String / Objects ?
 *       Need to handle selectors in JMS?
 *
 * @author Georges Polyzois
 */
public class JmsReaderJob extends AbstractReader implements MessageListener
{
    private static Logger logger = Logger.getLogger(FtpReaderJob.class);
    //private NodeConfig node;
    private BlockingQueue<AbstractCommand> queue;
    private NotFileFilter notFileFilter;
    private WildcardFilter wildcardFilter;
    private static final String JMS_DESTINATION_NAME = "JMS_DESTINATION_NAME";
    private static final String JMS_QUEUECONNECTIONFACTORY = "JMS_QUEUECONNECTIONFACTORY";


    /**
     * Empty - needed by Quartz framework.
     */
    public JmsReaderJob()
    {
    }


    /**
     * @return a list of CommnadHolder instances or a null if validation fails
     */
    @Override
    protected List<CommandMessage> readFromSource()
    {
        logger.info("Reading JMS messages from :" + node.getInBound().getUri());

        // a list of full paths on ftp server we will download from                                      node
        List<CommandMessage> commandMessages = null;
        AbstractDestination client = null;
        try
        {
            client = initConnection( node );
        }
        catch (Exception e)
        {
            logger.warn("Connection problem with FTP server.", e);
        }
        finally
        {

        }
        return commandMessages;
    }

    private AbstractDestination initConnection(Node node)
    {
        Map endPointContext = (Map) node.getInBound().getEndPointContext();
        String queueName = (String) endPointContext.get(JMS_DESTINATION_NAME);
        String queueConnectionFactory = (String) endPointContext.get(JMS_QUEUECONNECTIONFACTORY);


        AbstractDestination destination = new QueueListenerDestination(queueName, queueConnectionFactory, new EternalRebind(),null, this);
        return destination;
    }


    @Override
    protected void validate(Node node)
    {
        //EndPointContext endPointContext = (EndPointContext) node.getInBound().getEndPointContext().get(JMS_DESTINATION_NAME);
        //Validate.notNull(endPointContext.getValue(), "Can not use an empty file list to fetch data from.");


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
     * Called to init every time Quartz innvokes execute.
     *
     * @param node
     * @param blockingQueue
     */
    private void init(final Node node, BlockingQueue<AbstractCommand> blockingQueue)
    {
        if (node == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.node = node;
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(node);
        //       createFilter(node);

    }


    private void setJobExecutionContext(JobExecutionContext jobExecutionContext)
    {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        node = (Node) jobDataMap.get("node");
        BlockingQueue<AbstractCommand> blockingQueue = (BlockingQueue<AbstractCommand>) jobDataMap.get("queue");
        init(node, blockingQueue);
    }

    /**
     * Called by Queartz scheduler. Since we are a MessageListener we only need this to be done once - after
     * inititalization we will continuously rebind to Q or Topic and receive messages.
     *
     * @param jobExecutionContext
     */
    public void execute(JobExecutionContext jobExecutionContext)
    {
        setJobExecutionContext(jobExecutionContext);
        execute();
    }


    public void setQueue(BlockingQueue<AbstractCommand> queue)
    {
        this.queue = queue;
    }

    public void interrupt() throws UnableToInterruptJobException
    {
        logger.info(node.getId() + " got request to stop");
    }

    public void onMessage(Message message)
    {

    }
}