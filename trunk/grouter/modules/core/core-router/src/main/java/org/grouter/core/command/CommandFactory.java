package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.Node;


/**
 * Class description.
 *
 * @author Georges Polyzois
 */
public class CommandFactory
{
    private static Logger logger = Logger.getLogger(CommandFactory.class);


    public static AbstractCommandWriter getCommand(Node node)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("Config was null");
        }

        if( node.getOutBound().getEndPointType().getId() == EndPointType.FILE_WRITER.getId() )
        {

                FileCommandWriter fileWriterCommand = new FileCommandWriter(node);
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
        return null;
    }

    public static AbstractCommandWriter getCommand2(EndPoint outBoundEndPoint)
    {
        Validate.notNull( outBoundEndPoint, "Can not handle a null Endpoint");

        if( outBoundEndPoint.getEndPointType().getId() ==  EndPointType.FILE_WRITER.getId() )
        {
            FileCommandWriter fileWriterCommand = new FileCommandWriter( outBoundEndPoint );
            return fileWriterCommand;
        }
        return null;
    }
}
