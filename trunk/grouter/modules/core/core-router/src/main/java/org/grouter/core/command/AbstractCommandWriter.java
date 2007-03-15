package org.grouter.core.command;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Message;
import org.grouter.domain.servicelayer.RouterService;

import java.util.List;

/**
 * Interface for commands.
 * <p/>
 * Commands are object put on a blocking queue from a produces/publisher and handed over to a consumer/writer.
 * A command contains type of command and the message the writer side needs.
 */
public abstract class AbstractCommandWriter
{
    private static Logger logger = Logger.getLogger(AbstractCommandWriter.class);
    protected List<CommandHolder> commandMessages;
    Node node;

    RouterService service;



    /**
     * Commands must override this method to provide an implementation of an execute command
     * in the context for that command.
     * We are trying to enforce a template method pattern on top of this to ensure that we
     * handle backingup, transforming etc.
     */
    public void execute()
    {
        backup();
        transform();
        write();
        log();
    }

    /**
     * Do a transform of the message if applicable.
     */
    public abstract void transform();

    /**
     * Write the message to a destination - file etc.
     */
    public abstract void write();

    /**
     * If configured commandMessages are backuped.
     */
    public abstract void backup();

    /**
     * Log that we have sent a message - typically this should be to a JMS destination.
     */
    public abstract void log();


    public void logToJMSDestination()
    {
        logger.info("Sending message to JMS consumer.");
        // Todo refactor to use an special global endpoint for sending this message
        //JMSDestinationSenderThread.getQueue().offer(commandMessages);

        for (CommandHolder commandMessage : commandMessages)
        {
            Message message = new Message();
            message.setContent( commandMessage.getMessage() );
            message.setId( commandMessage.getGuid() );

            service.saveMessage( message );
        }



    }


    /**
     * Use reflection to pull out all attributs and values.
     */
    public String toStringUsingReflection()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public void setMessages(List<CommandHolder> arrCommandMessages)
    {
        this.commandMessages = arrCommandMessages;
    }
}
