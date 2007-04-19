package org.grouter.core.readers;


import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandFactory;
import org.grouter.core.command.CommandMessage;
import org.grouter.domain.entities.Node;
import org.quartz.Job;

import java.util.List;


public abstract class AbstractReader implements Job
{
    private static Logger logger = Logger.getLogger(AbstractReader.class);
    AbstractCommand command;
    Node node;

    public void setNode(Node node)
    {
        this.node = node;
    }

    /**
     * Read data from source and store uri in {@link CommandMessage} instance along with an extract of the message.
     *
     * @return list of {@link CommandMessage} instances
     */
    abstract List<CommandMessage> readFromSource();

    /**
     * Log that we have read a certain number of inbound messages from source.
     */
    abstract void pushToQueue();

    /**
     * Template method pattern.
     */
    final protected void execute()
    {
        try
        {
            validate(node);
        } catch (Exception e)
        {
            logger.error("Validation failed", e );
            return;
        }
        List<CommandMessage> arrCommandMessages = readFromSource();
        if (arrCommandMessages != null && arrCommandMessages.size() > 0)
        {
            command.setMessages(arrCommandMessages);
            pushToQueue();
        }
    }

    protected AbstractCommand getCommand(final Node node)
    {
        return CommandFactory.getCommand( node );
    }

    /**
     * All subclasses need to be able to validate them selves.
     */
    abstract void validate(Node node);


}
