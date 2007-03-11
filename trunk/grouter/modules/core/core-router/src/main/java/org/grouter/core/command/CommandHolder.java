package org.grouter.core.command;

import org.grouter.common.guid.GuidGenerator;

import java.io.Serializable;
import java.io.File;

/**
 * Class description.
 */
public class CommandHolder implements Serializable
{
    private String message;
    private String guid;

    private String encoding = "UTF-8";

    public CommandHolder(String message)
    {
        this.message = message;
        this.guid = GuidGenerator.getInstance().getGUID();

    }


    public CommandHolder(String message, String encoding)
    {
        this.encoding = encoding;
        this.message = message;
        this.guid = GuidGenerator.getInstance().getGUID();

    }

    public String getGuid()
    {
        return guid;
    }


    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }
}
