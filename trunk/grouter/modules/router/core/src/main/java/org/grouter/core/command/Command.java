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
    protected Message[] message;

    /**
     * Commands must override this method to provide an implementation of an execute command
     * in the context for that command.
     */
    public abstract void execute();

    public Message[] getMessage()
    {
        return message;
    }

    public void setMessage(Message[] message)
    {
        this.message = message;
    }

    /**
     * Use reflection to pull out all attributs and values.
     */
    public String toStringUsingReflection()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
