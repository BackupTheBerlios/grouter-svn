package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.common.guid.GuidGenerator;
import org.grouter.core.config.NodeConfig;

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


    /**
     * Constructor.
     * @param nodeConfig
     * @throws  IllegalArgumentException if nodeConfig == null
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
     * Overridden from abstract Command class.
     */
    public void execute()
    {
        logger.debug(nodeConfig.getId() + " Writing file to dir : " + nodeConfig.getOutFolder().getOutFolderPath());
        for (int i = 0; i < message.length; i++)
        {
            logger.debug(message[i].getMessage());
            try
            {
                String fileName = null;
                if (nodeConfig.isCreateuniquename())
                {
                    fileName = GuidGenerator.getInstance().getGUID();
                }
                writer = new FileWriter(nodeConfig.getOutFolder().getOutFolderPath().getPath() + "/" + fileName);
                writer.write(message[i].getMessage());
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

        //writer.write();
        //writer.readSendFiles(null,null);
        //writer.backupFiles(null);

        //writer.backup();
        //etc
    }
}
