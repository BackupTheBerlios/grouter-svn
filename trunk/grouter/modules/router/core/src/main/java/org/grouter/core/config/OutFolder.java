package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * An outfolder is used to represent an OutFolderType (an xml binding type).
 */
public class OutFolder
{
    private File outFolderPath;
    private static Logger logger = Logger.getLogger(OutFolder.class);

    /**
     * @param outFolderPath
     * @throws IllegalArgumentException if outFolderPath == null
     * @throws IllegalArgumentException if outFolderPath == null
     */
    public OutFolder(File outFolderPath)
    {
        if (outFolderPath == null)
        {
            throw new IllegalArgumentException("outFolderPath can not be null");
        }
        this.outFolderPath = outFolderPath;
    }

    /**
     * Simple getter.
     *
     * @return
     */
    public File getOutFolderPath()
    {
        return outFolderPath;
    }

    /**
     * Flush out state of object to logger.
     */
    public void logState()
    {
        logger.info(ToStringBuilder.reflectionToString(this));
    }
}
