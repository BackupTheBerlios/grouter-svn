package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * An outfolder is used to represent an OutFolderType (an xml binding type).
 */
public class OutFolderConfig
{
    private File outPath;
    private static Logger logger = Logger.getLogger(OutFolderConfig.class);

    /**
     * @param outFolderPath
     * @throws IllegalArgumentException if outPath == null
     * @throws IllegalArgumentException if outPath == null
     */
    public OutFolderConfig(File outFolderPath)
    {
        if (outFolderPath == null || !outFolderPath.isDirectory())
        {
            throw new IllegalArgumentException("OutFolderPath must be valid :" + outFolderPath);
        }
        this.outPath = outFolderPath;
    }

    /**
     * Simple getter.
     *
     * @return
     */
    public File getOutPath()
    {
        return outPath;
    }

    /**
     * Flush out state of object to logger.
     */
    public void logState()
    {
        logger.info(ToStringBuilder.reflectionToString(this));
    }
}
