package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 * An outfolder is used to represent a
 */
public class OutFolder {
    private String outFolderPath;
    private static Logger logger = Logger.getLogger(OutFolder.class);

    /**
     *
     * @param outFolderPath
     * @throws IllegalArgumentException if outFolderPath == null
     */
    public OutFolder(String outFolderPath)
    {
        if(outFolderPath == null)
        {
            throw new IllegalArgumentException("outFolderPath can not be null");
        }
        this.outFolderPath = outFolderPath;
    }

    public String getOutFolderPath() {
        return outFolderPath;
    }



    /**
     * Flush out state of object to logger.
     */
    public void logState() {
        logger.info(ToStringBuilder.reflectionToString(this));
    }
}
