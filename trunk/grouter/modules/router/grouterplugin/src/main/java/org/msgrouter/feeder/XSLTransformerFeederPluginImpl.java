package org.msgrouter.feeder;

import org.apache.log4j.Logger;
import org.msgrouter.feeder.FeederPlugin;


/**
 * @author Georges Polyzois
 */
public class XSLTransformerFeederPluginImpl implements FeederPlugin
{
    /**  Logger. */
    private static Logger logger = Logger.getLogger(XSLTransformerFeederPluginImpl.class);
    private String description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void execute()
    {
        logger.debug("->" + description);
    }
}
