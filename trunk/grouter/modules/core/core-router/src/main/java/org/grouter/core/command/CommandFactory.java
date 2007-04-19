package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.Node;


/**
 * A factory for creating commands based on a Node's {@link org.grouter.domain.entities.EndPointType}.
 *
 * @author Georges Polyzois
 */
public class CommandFactory
{
    private static Logger logger = Logger.getLogger(CommandFactory.class);

    /**
     * Given a node create a command for this node.
     * @param node
     * @return
     */
    public static AbstractCommand getCommand(Node node)
    {
        // Guard this factory 
        Validate.notNull(node, "Can not handle a null Node");
        Validate.notNull(node.getInBound(), "Can not handle a null EndPoint");
        Validate.notNull(node.getInBound().getEndPointType(), "Can not handle a null EndPointType");

        if (node.getOutBound().getEndPointType().getId() == EndPointType.FILE_WRITER.getId())
        {
            FileWriteCommand fileWriteCommand = new FileWriteCommand(node);
            return fileWriteCommand;
        }
        return null;
    }
    
}
