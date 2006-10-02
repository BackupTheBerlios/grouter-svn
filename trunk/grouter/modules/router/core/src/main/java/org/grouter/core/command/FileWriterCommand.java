package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.core.config.NodeConfig;
import org.grouter.core.util.JMSDestinationSenderThread;

import java.io.FileWriter;
import java.io.IOException;

/**
 * A concrete command to be performed by a consumer, held by the CommandInvoker. </br>
 * This class acts as a consumer in the Command pattern.
 */
public class FileWriterCommand extends Command
{
    private static Logger logger = Logger.getLogger(FileWriterCommand.class);
    private FileWriter writer;
    private NodeConfig nodeConfig;
    private JMSDestinationSenderThread jmsDestinationSenderThread;


    /**
     * Constructor.
     *
     * @param nodeConfig
     * @throws IllegalArgumentException if nodeConfig == null
     */
    public FileWriterCommand(NodeConfig nodeConfig)
    {
        if (nodeConfig == null)
        {
            throw new IllegalArgumentException("You must provide a NodeConfig !!");
        }
        this.nodeConfig = nodeConfig;
    }


    /**
     * Implementing a transformation step.
     */
    public void transform()
    {
        logger.debug("Do a transform !!!!!!!");

    }

    public void write()
    {
        logger.debug(nodeConfig.getId() + " Writing file to dir : " + nodeConfig.getOutFolderConfig().getOutPath());
        for (int i = 0; i < commandMessages.length; i++)
        {
            logger.debug("Wrote a new file :" + commandMessages[i].getMessage());
            try
            {
                writer = new FileWriter(nodeConfig.getOutFolderConfig().getOutPath().getPath() + "/" + commandMessages[i].getId() );
                writer.write(commandMessages[i].getMessage());
                writer.flush();
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
            finally
            {
                if (writer != null)
                {
                    try
                    {
                        writer.close();
                    } catch (IOException e)
                    {
                        //ignore
                    }
                }
            }
        }
    }

    public void storeLocally()
    {
        logger.debug(nodeConfig.getId() + " storing locally for backup to dir : " + nodeConfig.getLocalStoreConfig().getBackup());
        for (int i = 0; i < commandMessages.length; i++)
        {
            logger.debug("Stored a new message in backup folder locally :" + commandMessages[i].getId());
            try
            {
                writer = new FileWriter(nodeConfig.getLocalStoreConfig().getBackup() + "/" + commandMessages[i].getId());
                writer.write(commandMessages[i].getMessage());
                writer.flush();
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
            finally
            {
                if (writer != null)
                {
                    try
                    {
                        writer.close();
                    } catch (IOException e)
                    {
                        //ignore
                    }
                }
            }

        }

    }


    public void sendToJMSDestination()
    {
        logger.info( nodeConfig.getId() + " sending message to JMS consumer.");
       JMSDestinationSenderThread.getQueue().offer(commandMessages);
    }
}
