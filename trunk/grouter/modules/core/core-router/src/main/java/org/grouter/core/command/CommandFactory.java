package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
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

    /*
    public static AbstractCommandWriter getCommand(Node node)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("Config was null");
        }

        if (node.getOutBound().getEndPointType().getId() == EndPointType.FILE_WRITER.getId())
        {

            FileCommandWriter fileWriterCommand = new FileCommandWriter(node);
            return fileWriterCommand;
        }
        return null;
    }
    */

    public static AbstractCommandWriter getCommand2(Node node)
    {
        Validate.notNull(node, "Can not handle a null Endpoint");

        if (node.getOutBound().getEndPointType().getId() == EndPointType.FILE_WRITER.getId())
        {
            FileCommandWriter fileWriterCommand = new FileCommandWriter(node);
            return fileWriterCommand;
        }
        return null;
    }
}
