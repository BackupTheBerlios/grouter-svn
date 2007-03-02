package org.grouter.core.config;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-30
 * Time: 08:49:01
 * To change this template use File | Settings | File Templates.
 */
public class LocalStoreConfig
{
    private File backup;
    private File archive;
    private static final String ARCHIVE = "/archive";

    /**
     *
     * @param backup
     * @throws IllegalArgumentException if backup == null || archive == null
     */
    public LocalStoreConfig(File backup)
    {
        if(backup == null || !backup.isDirectory() )
        {
            throw new IllegalArgumentException("Backup archive may not be null!!");
        }

        this.archive = new File(backup.getParentFile() + ARCHIVE);
        this.backup = backup;
    }

    /**
     * Getter.
     * @return
     */
    public File getBackup()
    {
        return backup;
    }


    /**
     * Getter.
     * @return
     */
    public File getArchive()
    {
        return archive;
    }
}
