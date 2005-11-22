package org.grouter.core.config;

/**
 * Class description.
 */
public class GlobalSettings
{
    public ArchiveHandler getArchiveHandler()
    {
        return archiveHandler;
    }

    public void setArchiveHandler(ArchiveHandler archiveHandler)
    {
        this.archiveHandler = archiveHandler;
    }

    ArchiveHandler archiveHandler;

}
