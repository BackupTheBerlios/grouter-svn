package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.grouter.core.util.file.FileUtils;

import java.io.File;

/**
 * An outfolder is used to represent an OutFolderType (an xml binding type).
 */
public class OutFolderConfig
{
    private File outFolderPath;
    private static Logger logger = Logger.getLogger(OutFolderConfig.class);

    /**
     * @param outFolderPath
     * @throws IllegalArgumentException if outFolderPath == null
     * @throws IllegalArgumentException if outFolderPath == null
     */
    public OutFolderConfig(File outFolderPath)
    {
        if (outFolderPath == null || !outFolderPath.isDirectory())
        {
            throw new IllegalArgumentException("OutFolderPath must be valid :" + outFolderPath);
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
