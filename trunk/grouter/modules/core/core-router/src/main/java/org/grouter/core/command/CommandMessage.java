package org.grouter.core.command;

import org.grouter.common.guid.GuidGenerator;

import java.io.Serializable;
import java.io.File;

/**
 * Class description.
 */
public class CommandMessage implements Serializable
{
    private String message;
    private String fileUriToMessage;
    private String guid;
    private String encoding = "UTF-8";

    public CommandMessage(String message, String internalUri)
    {
        this.message = message;
        this.fileUriToMessage = internalUri + File.separator + GuidGenerator.getInstance().getGUID();
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


    public String getFileUriToMessage()
    {
        return fileUriToMessage;
    }

    public void setFileUriToMessage(String fileUriToMessage)
    {
        this.fileUriToMessage = fileUriToMessage;
    }
}
