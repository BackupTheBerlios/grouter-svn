package org.grouter.core.config;

import org.grouter.core.command.CommandTypes;

/**
 * Class description.
 */
public abstract class AbstractNode
{
    protected CommandTypes commandType = null;
    private boolean transform;
    private boolean backup;


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
}
