package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.Node;

import java.io.File;

/**
 * A concrete command to be performed by a consumer, held by the CommandInvoker. </br>
 * This class acts as a consumer in the Command pattern.
 */
public class FileCommandWriter extends AbstractCommandWriter
{
    private static Logger logger = Logger.getLogger(FileCommandWriter.class);

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

                       /*
    public FileCommandWriter(EndPoint outBound)
    {
        Validate.notNull( outBound , "You must provide a outBound EndPoint !!" );
        this.outBound = outBound;
    }
                         */

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
            logger.debug(node.getName() + " writing to uri : " + node.getOutBound().getUri() );

        for (CommandHolder commandMessage : commandMessages)
        {
            logger.debug("Wrote a new file :" + commandMessage.getMessage());
            try
            {
                FileUtils.writeStringToFile( new File( node.getOutBound().getUri()  + "/"  + commandMessage.getGuid() + ".txt" ) , commandMessage.getMessage(), commandMessage.getEncoding()  );
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
        }
    }

    public void backup()
    {
        logger.debug(node.getName() + " backup to uri : " + node.getBackupUri() );
        for (CommandHolder commandMessage : commandMessages)
        {
            try
            {
                FileUtils.writeStringToFile( new File( node.getBackupUri() + "/" + commandMessage.getGuid() + ".txt" ) , commandMessage.getMessage(), commandMessage.getEncoding()  );
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
        }
    }


    public void log()
    {
        logToJMSDestination();
    }
}
