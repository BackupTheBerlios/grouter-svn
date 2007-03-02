package org.grouter.core.command;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Interface for commands.
 * <p/>
 * Commands are object put on a blocking queue from a produces/publisher and handed over to a consumer/writer.
 * A command contains type of command and the message the writer side needs.
 */
public abstract class Command
{
    protected CommandMessage[] commandMessages;

    /**
     * Commands must override this method to provide an implementation of an execute command
     * in the context for that command.
     * We are tryign to enforce a template method pattern on top of this to ensure that we
     * handle backingup, transforming etc.
     */
    public void execute()
    {
        storeLocally();
        transform();
        write();
        sendToJMSDestination();
    }

    /**
     * Do a transfomr of the message if applicable.
     */
    public abstract void transform();

    /**
     * Write the message to a destination - file etc.
     */
    public abstract void write();

    /**
     * If configured commandMessages are stored localy for a backup and archived at a given intervall.
     */
    public abstract void storeLocally();

    /**
     * Send message asynchronously to application server.
     */
    public abstract void sendToJMSDestination();




    /**
     * Use reflection to pull out all attributs and values.
     */
    public String toStringUsingReflection()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public void setMessages(CommandMessage[] arrCommandMessages)
    {
        this.commandMessages = arrCommandMessages;
    }
}
