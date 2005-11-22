package org.grouter.core.config;

import org.grouter.core.command.CommandTypes;

/**
 * Class description.
 */
public abstract class ServiceNodeConfig
{
    protected CommandTypes commandType = null;
    private boolean transform;
    private boolean backup;
    /** Unique within a feederId process. */
    private String serviceNodeId;
    /** Unique within a farm of feeders. */
    private String feederId;

    public ServiceNodeConfig()
    {
    }

    public CommandTypes getCommandType()
    {
        return commandType;
    }

    public void setCommandType(CommandTypes commandType)
    {
        this.commandType = commandType;
    }

    public boolean isTransform()
    {
        return transform;
    }

    public void setTransform(boolean transform)
    {
        this.transform = transform;
    }

    public boolean isBackup()
    {
        return backup;
    }

    public void setBackup(boolean backup)
    {
        this.backup = backup;
    }

    public String getServiceNodeId()
    {
        return serviceNodeId;
    }

    public void setServiceNodeId(String serviceNodeId)
    {
        this.serviceNodeId = serviceNodeId;
    }

    public String getFeederId()
    {
        return feederId;
    }

    public void setFeederId(String feederId)
    {
        this.feederId = feederId;
    }
}
