package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.core.config.Node;


/**
 * Class description.
 */
public class CommandFactory
{
    private static Logger logger = Logger.getLogger(CommandFactory.class);

    public static Command getCommand(Node node)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("Config was null");
        }

        switch (node.getNodeType())
        {
            case FILE_TO_FILE:
            {
                FileWriterCommand fileWriterCommand = new FileWriterCommand(node);
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
