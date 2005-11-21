package org.grouter.core.command;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Interface for commands.
 */
public abstract class Command
{
    protected Message[] message;


    public Message[] getMessage()
    {
        return message;
    }

    public void setMessage(Message[] message)
    {
        this.message = message;
    }

    public abstract void execute();

    /**
     * Use reflection to pull out all attributs and values.
     */
    public String toStringUsingReflection()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
