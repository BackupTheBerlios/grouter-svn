package org.grouter.core.config;

/**
 * Class description.
 */
public class CronJobConfig
{
    String intervall;

    public CronJobConfig(String intervall)
    {
        this.intervall = intervall;
    }


    public String getIntervall()
    {
        return intervall;
    }
}
