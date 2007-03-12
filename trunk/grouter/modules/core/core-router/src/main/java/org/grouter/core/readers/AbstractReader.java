package org.grouter.core.readers;


import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommandWriter;
import org.grouter.core.command.CommandFactory;
import org.grouter.core.command.CommandHolder;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.Node;
import org.quartz.Job;

import java.util.List;


public abstract class AbstractReader implements Job
{
    private static Logger logger = Logger.getLogger(AbstractReader.class);

    public void setNode(Node node)
    {
        this.node = node;
    }

    AbstractCommandWriter command;
    Node node;

    abstract List<CommandHolder> readFromSource();

    abstract void pushToQueue();

    /**
     * Template method pattern.
     */
    final protected void execute()
    {
        List<CommandHolder> arrCommandMessages = readFromSource();
        if (arrCommandMessages != null && arrCommandMessages.size() > 0)
        {
            command.setMessages(arrCommandMessages);
            pushToQueue();
        }
    }

    protected AbstractCommandWriter getCommand(final Node node)
    {
        return CommandFactory.getCommand( node );
    }



}
