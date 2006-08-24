package org.grouter.core.command;

import static org.grouter.core.config.Node.Type.FILE_TO_FILE;
import static org.grouter.core.config.Node.Type;
import org.grouter.core.config.FileWriter;
import org.grouter.core.config.Node;
import org.apache.log4j.Logger;

import java.io.IOException;


/**
 * Class description.
 */
public class CommandFactory
{
    private static Logger logger = Logger.getLogger(CommandFactory.class);

    public static Command getCommand(Node node)
    {
        if (node==null)
        {
            throw new IllegalArgumentException("Config was null");
        }

        switch(node.getNodeType())
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
            default :
                return null;
        }
    }
}
