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
    private File inPath;
    private long pollIntervallMilliSeconds;
    private boolean skipFirstblankline;
    private BatchReadConfig batchReadConfig;
    public static final int MIN_POLLINTERVALL = 2000;
    private FilterConfig filterConfig;

    /**
     *
     * @param infolderpath
     * @param pollIntervallMilliSeconds
     * @param skipfirstblankline
     * @param batchReadConfig
     * @param filterConfig  the filter should list the exclusion pattern for files that should not be included in read operations
     * @throws IllegalArgumentException  if infolderpath == null || pollIntervallMilliSeconds < MIN_POLLINTERVALL || || !infolderpath.isDirectory()
     */
    public InFolderConfig(File infolderpath, long pollIntervallMilliSeconds, boolean skipfirstblankline,
                          BatchReadConfig batchReadConfig, FilterConfig filterConfig)
    {
        if (infolderpath == null || !infolderpath.isDirectory())
        {
            throw new IllegalArgumentException("Is Infolderpath :" + infolderpath + " a directory?" );
        }

        if (pollIntervallMilliSeconds < MIN_POLLINTERVALL )
        {
            throw new IllegalArgumentException("Is the poll intervall :" + pollIntervallMilliSeconds +" less than " + MIN_POLLINTERVALL );
        }

        this.inPath = infolderpath;
        this.pollIntervallMilliSeconds = pollIntervallMilliSeconds;
        this.skipFirstblankline = skipfirstblankline;
        this.batchReadConfig =  batchReadConfig;
        this.filterConfig =  filterConfig;
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
    public File getInPath()
    {
        return inPath;
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
     * Getter.
     * @return
     */
    public FilterConfig getFilterConfig()
    {
        return filterConfig;
    }

    /**
     * Getter.
     * @return
     */
    public BatchReadConfig getBatchReadConfig()
    {
        return batchReadConfig;
    }
}
