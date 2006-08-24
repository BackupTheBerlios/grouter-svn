package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 * A node config holder.
 */
public class InFolder {

    private static Logger logger = Logger.getLogger(InFolder.class);
    private String inFolderPath;
    private long pollIntervallMilliSeconds;
    private boolean skipFirstblankline;
    private BatchRead batchRead;

    public InFolder(String infolderpath, long pollIntervallMilliSeconds, boolean skipfirstblankline, BatchRead batchRead) {
        this.inFolderPath = infolderpath;
        this.pollIntervallMilliSeconds = pollIntervallMilliSeconds;
        this.skipFirstblankline = skipfirstblankline;
    }

    /**
     * Flush out state of object to logger.
     */
    public void logState() {
        logger.info(ToStringBuilder.reflectionToString(this));
    }


    public String getInFolderPath() {
        return inFolderPath;
    }

    public long getPollIntervallMilliSeconds() {
        return pollIntervallMilliSeconds;
    }

    public boolean isSkipFirstblankline() {
        return skipFirstblankline;
    }

    public BatchRead getBatchRead() {
        return batchRead;
    }
}
