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
public class BatchReadConfig
{
    private static Logger logger = Logger.getLogger(BatchReadConfig.class);
    private boolean isBatchOn;
    private int batchSize;
    private int batchThreshold;

    /**
     * Constructor.
     * @param batchOn
     * @param batchSize
     * @param batchThreshold
     */
    public BatchReadConfig(boolean batchOn, int batchSize, int batchThreshold)
    {
        this.isBatchOn = batchOn;
        this.batchSize = batchSize;
        this.batchThreshold = batchThreshold;
    }

    /**
     * Flush out state of object to logger.
     */
    public void logState()
    {
        logger.info(ToStringBuilder.reflectionToString(this));
    }
}
