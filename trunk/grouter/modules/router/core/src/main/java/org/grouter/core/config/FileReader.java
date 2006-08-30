package org.grouter.core.config;

import org.grouter.core.command.CommandTypes;

import java.io.File;

/**
 * Class description.
 */
public class FileReader extends AbstractNode
{
    CommandTypes commandType = CommandTypes.FROMFILE;
    private File fromDir;

    public FileReader(File fromDir)
    {
        this.fromDir = fromDir;
    }

    public File getFromDir()
    {
        return fromDir;
    }

    public void setFromDir(File fromDir)
    {
        this.fromDir = fromDir;
    }
}
