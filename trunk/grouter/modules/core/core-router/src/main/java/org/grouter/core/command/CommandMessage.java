package org.grouter.core.command;

import java.io.Serializable;
import java.io.File;

/**
 * Class description.
 */
public class CommandMessage implements Serializable
{
    private String message;
    private File internalInFile;
    private File internalOutFile;
    private String guid;
    private String encoding = "UTF-8";

    public CommandMessage(String message, File internalInFile)
    {
        this.message = message;
        this.internalInFile = internalInFile;
        //internalOutFile = new File( internalInFile + GuidGenerator.getInstance().getGUID() );
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



    public File getInternalInFile()
    {
        return internalInFile;
    }

    public void setInternalInFile(File internalInFile)
    {
        this.internalInFile = internalInFile;
    }

    public File getInternalOutFile()
    {
        return internalOutFile;
    }

    public void setInternalOutFile(File internalOutFile)
    {
        this.internalOutFile = internalOutFile;
    }
}
