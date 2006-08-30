package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * A node config holder.
 */
public class InFolderConfig
{
    private static Logger logger = Logger.getLogger(InFolderConfig.class);
    private File inFolderPath;
    private long pollIntervallMilliSeconds;
    private boolean skipFirstblankline;
    private BatchReadConfig batchReadConfig;
    public static final int MIN_POLLINTERVALL = 2000;

    /**
     *
     * @param infolderpath
     * @param pollIntervallMilliSeconds
     * @param skipfirstblankline
     * @param batchReadConfig
     * @throws IllegalArgumentException  if infolderpath == null || pollIntervallMilliSeconds < MIN_POLLINTERVALL
     */
    public InFolderConfig(File infolderpath, long pollIntervallMilliSeconds, boolean skipfirstblankline, BatchReadConfig batchReadConfig)
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
    public BatchReadConfig getBatchRead()
    {
        return batchReadConfig;
    }
}
