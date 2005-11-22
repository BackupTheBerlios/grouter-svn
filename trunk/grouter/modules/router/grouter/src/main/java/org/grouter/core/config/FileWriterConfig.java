package org.grouter.core.config;

import org.grouter.core.command.CommandTypes;

import java.io.File;

/**
 * Class description.
 */
public class FileWriterConfig extends ServiceNodeConfig
{
    File toDir;

    public FileWriterConfig(File toDir)
    {
        super.commandType = CommandTypes.TOFILE;
        this.toDir = toDir;
    }

    public File getToDir()
    {
        return toDir;
    }

    public void setToDir(File toDir)
    {
        this.toDir = toDir;
    }


}
