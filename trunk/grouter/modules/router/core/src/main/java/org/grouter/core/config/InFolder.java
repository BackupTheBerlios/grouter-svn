package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * A node config holder.
 */
public class InFolder
{
    private static Logger logger = Logger.getLogger(InFolder.class);
    private File inFolderPath;
    private long pollIntervallMilliSeconds;
    private boolean skipFirstblankline;
    private BatchRead batchRead;
    public static final int MIN_POLLINTERVALL = 2000;

    /**
     *
     * @param infolderpath
     * @param pollIntervallMilliSeconds
     * @param skipfirstblankline
     * @param batchRead
     * @throws IllegalArgumentException  if infolderpath == null || pollIntervallMilliSeconds < MIN_POLLINTERVALL
     */
    public InFolder(File infolderpath, long pollIntervallMilliSeconds, boolean skipfirstblankline, BatchRead batchRead)
    {
        if (infolderpath == null || pollIntervallMilliSeconds < MIN_POLLINTERVALL)
        {
            throw new IllegalArgumentException("Infolderpath = " + infolderpath + "(must be non null) or the poll intervall = " + pollIntervallMilliSeconds +" was less than " + MIN_POLLINTERVALL );
        }
        this.inFolderPath = infolderpath;
        this.pollIntervallMilliSeconds = pollIntervallMilliSeconds;
        this.skipFirstblankline = skipfirstblankline;
    }

    /**
     * Flush out state of object to logger.
     */
    public void logState()
    {
        logger.info(ToStringBuilder.reflectionToString(this));
    }


    /**
     * Simple getter.
     * @return
     */
    public File getInFolderPath()
    {
        return inFolderPath;
    }

    /**
     * Simple getter.
     * @return
     */
    public long getPollIntervallMilliSeconds()
    {
        return pollIntervallMilliSeconds;
    }

    /**
     * Simple getter.
     * @return
     */
    public boolean isSkipFirstblankline()
    {
        return skipFirstblankline;
    }

    /**
     * Simple getter.
     * @return
     */
    public BatchRead getBatchRead()
    {
        return batchRead;
    }
}
