package org.grouter.core.readers;


import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommandWriter;
import org.grouter.core.command.CommandFactory;
import org.grouter.core.command.CommandHolder;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.Node;

import java.util.List;


public abstract class AbstractReader //extends TimerTask
{
    private static Logger logger = Logger.getLogger(AbstractReader.class);
    AbstractCommandWriter command;
    EndPoint endPoint;
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

    protected AbstractCommandWriter getCommand(final Node nodeConfig)
    {
        return CommandFactory.getCommand(nodeConfig);
    }



    protected AbstractCommandWriter getCommand2(final EndPoint outBoundEndPoint)
    {
        return CommandFactory.getCommand2(outBoundEndPoint);
    }
}
