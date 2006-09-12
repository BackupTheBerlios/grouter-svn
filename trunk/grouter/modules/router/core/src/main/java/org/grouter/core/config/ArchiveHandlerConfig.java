package org.grouter.core.config;

/**
 * Class description.
 */
public class ArchiveHandlerConfig
{
    CronJobConfig cronJobConfig;


    /**
     * Constructor.
     * 
     * @param cronJobConfig
     * @throws IllegalArgumentException if cronJobConfig == null
     */
    public ArchiveHandlerConfig(CronJobConfig cronJobConfig)
    {
        if(cronJobConfig == null)
        {
            throw new IllegalArgumentException("An ArchiveHandler must have a valid cron job intervall. Was : " + cronJobConfig);
        }
        this.cronJobConfig = cronJobConfig;
    }

    /**
     * Getter.
     * @return
     */
    public CronJobConfig getCronJob()
    {
        return cronJobConfig;
    }

}
