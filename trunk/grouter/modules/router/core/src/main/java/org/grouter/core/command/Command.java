package org.grouter.core.command;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Interface for commands.
 *
 * Commands are object put on queue from a reaer and handed over to a writer. It
 * contains type of command and the message the writer side needs. 
 */
public abstract class Command
{
    protected Message[] message;

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
