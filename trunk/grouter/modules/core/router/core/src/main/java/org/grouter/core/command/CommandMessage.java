package org.grouter.core.command;

import java.io.Serializable;

/**
 * Class description.
 */
public class CommandMessage implements Serializable
{
    private String message;
    private String id;

    public CommandMessage(String message)
    {
        this.message = message;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMessage()
    {
        return message;
    }

    public String getId()
    {
        return id;
    }
}
