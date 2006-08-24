package org.grouter.core.config;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-21
 * Time: 16:46:05
 * To change this template use File | Settings | File Templates.
 */
public class BatchRead {
    private static Logger logger = Logger.getLogger(BatchRead.class);
    private boolean isBatchOn;
    private int batchSize;
    private int batchThreshold;

    public BatchRead(boolean batchOn, int batchSize, int batchThreshold)
    {
        isBatchOn = batchOn;
        this.batchSize = batchSize;
        this.batchThreshold = batchThreshold;
    }

    /**
     * Flush out state of object to logger.
     */
    public void logState() {
        logger.info(ToStringBuilder.reflectionToString(this));
    }
}
