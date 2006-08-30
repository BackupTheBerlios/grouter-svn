package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.core.config.NodeConfig;


/**
 * Class description.
 */
public class CommandFactory
{
    private static Logger logger = Logger.getLogger(CommandFactory.class);

    public static Command getCommand(NodeConfig nodeConfig)
    {
        if (nodeConfig == null)
        {
            throw new IllegalArgumentException("Config was null");
        }

        switch (nodeConfig.getNodeType())
        {
            case FILE_TO_FILE:
            {
                FileWriterCommand fileWriterCommand = new FileWriterCommand(nodeConfig);
                return fileWriterCommand;
                /*FileWriter writer = null;
                try
                {
                    writer = new FileWriter( fileReaderConfig);
                } catch (IOException e)
                {
                    logger.error("Failed creating filewriter for fileReaderConfig",e);
                }

                return new FileWriterCommand(fileWriterConfig);
                */
            }
            default:
                return null;
        }
    }
}
