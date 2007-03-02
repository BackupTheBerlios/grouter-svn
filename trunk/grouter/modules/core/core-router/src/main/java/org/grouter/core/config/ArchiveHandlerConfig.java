package org.grouter.core.config;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 12, 2006
 * Time: 3:24:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArchiveHandlerConfig
{
    private CronJobConfig cronJobConfig;


    public ArchiveHandlerConfig(CronJobConfig cronJobConfig)
    {
        this.cronJobConfig = cronJobConfig;
    }
}
