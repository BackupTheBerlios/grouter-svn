package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Message;
import org.grouter.domain.servicelayer.spring.logging.JDBCLogStrategy;
import org.grouter.domain.servicelayer.spring.logging.LogStrategy;
import org.grouter.domain.servicelayer.ServiceFactory;
import org.grouter.common.guid.GuidGenerator;

import java.io.File;

/**
 * A concrete command to be performed by a consumer, held by the CommandInvoker. </br>
 * This class acts as a consumer in the Command pattern.
 *
 * @author Georges Polyzois
 */
public class FileWriteCommand extends AbstractCommand
{
    private static Logger logger = Logger.getLogger(FileWriteCommand.class);
    ServiceFactory serviceFactory;


    public FileWriteCommand()
    {
    }


    /**
     * Constructor.
     *
     * @param node
     * @throws IllegalArgumentException if node == null
     */
    public FileWriteCommand(Node node)
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
        for (CommandMessage commandMessage : commandMessages)
        {
            logger.debug("Wrote a new file :" + commandMessage.getMessage());
            try
            {
                FileUtils.copyFile( commandMessage.getInternalInFile(), new File(node.getOutBound().getUri() + File.separator  + commandMessage.getInternalInFile().getName() ) );
                File internalOutFile = new File( node.getRouter().getHomePath() + File.separator + "nodes" + File.separator + node.getId() + File.separator + "internal" + File.separator + "out" + File.separator + commandMessage.getInternalInFile().getName() + "_"  + GuidGenerator.getInstance().getGUID() );
                FileUtils.copyFile( commandMessage.getInternalInFile(), internalOutFile  );
                commandMessage.getInternalInFile().delete();
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
        for (CommandMessage commandMessage : commandMessages)
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

        for (CommandMessage commandMessage : commandMessages)
        {
            Message message = new Message();
            message.setContent(commandMessage.getMessage());
//  produces stale exceptions            message.setId(commandMessage.getGuid());

            message.setNode( node );
            
            LogStrategy jdbcLogStrategy = (JDBCLogStrategy) serviceFactory.getLogStrategy(ServiceFactory.JDBCLOGSTRATEGY_BEAN);
            jdbcLogStrategy.log(message);

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
