package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.servicelayer.spring.logging.JDBCLogStrategy;
import org.grouter.domain.servicelayer.ServiceFactory;

import java.io.File;

/**
 * A concrete command to be performed by a consumer, held by the CommandInvoker. </br>
 * This class acts as a consumer in the Command pattern.
 *
 * @author Georges Polyzois
 */
public class FileCommandWriter extends AbstractCommandWriter
{
    private static Logger logger = Logger.getLogger(FileCommandWriter.class);
    ServiceFactory serviceFactory;


    public FileCommandWriter()
    {
    }


    /**
     * Constructor.
     *
     * @param node
     * @throws IllegalArgumentException if node == null
     */
    public FileCommandWriter(Node node)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("You must provide a NodeConfig !!");
        }
        this.node = node;
    }


    /**
     * Implementing a transformation step.
     */
    public void transform()
    {
        // Todo
        logger.debug("Do a transform !!!!!!!");
    }

    public void write()
    {
        logger.debug(node.getName() + " writing to uri : " + node.getOutBound().getUri());

        for (CommandHolder commandMessage : commandMessages)
        {
            logger.debug("Wrote a new file :" + commandMessage.getMessage());
            try
            {
                FileUtils.writeStringToFile(new File(node.getOutBound().getUri() + "/" + commandMessage.getGuid() + ".txt"), commandMessage.getMessage(), commandMessage.getEncoding());
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
        }
    }

    public void backup()
    {
        logger.debug(node.getName() + " backup to uri : " + node.getBackupUri());
        for (CommandHolder commandMessage : commandMessages)
        {
            try
            {
                FileUtils.writeStringToFile(new File(node.getBackupUri() + "/" + commandMessage.getGuid() + ".txt"), commandMessage.getMessage(), commandMessage.getEncoding());
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
        }
    }

    public void log()
    {

        logger.info("Sending message to JMS consumer.");
        // Todo refactor to use an special global endpoint for sending this message
        //JMSDestinationSenderThread.getQueue().offer(commandMessages);

        for (CommandHolder commandMessage : commandMessages)
        {
            Message message = new Message();
            message.setContent(commandMessage.getMessage());
//  produces stale exceptions            message.setId(commandMessage.getGuid());

            message.setNode( node );
            
            JDBCLogStrategy jdbcLogStrategy = (JDBCLogStrategy) serviceFactory.getLogStrategy(ServiceFactory.JDBCLOGSTRATEGY_BEAN);
            jdbcLogStrategy.log(message);

            //logStrategy.log( message );
        }

    }


    /**
     * Injected.
     *
     * @param serviceFactory
     */
    public void setServiceFactory(ServiceFactory serviceFactory)
    {
        this.serviceFactory = serviceFactory;
    }
}
