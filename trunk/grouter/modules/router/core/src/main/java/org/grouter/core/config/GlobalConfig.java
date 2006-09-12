package org.grouter.core.config;

/**
 * Class description.
 */
public class GlobalConfig
{
    ArchiveHandlerConfig archiveHandlerConfig;
    String grouterHome;


    /**
     * Constructor.
     * @param grouterHome
     */
    public GlobalConfig(String grouterHome)
    {
        this.grouterHome = grouterHome;
    }

    /**
     *
     * @param archiveHandlerConfig
     * @param grouterHome
     * @throws IllegalArgumentException if grouterHome == null
     */
    public GlobalConfig(ArchiveHandlerConfig archiveHandlerConfig, String grouterHome)
    {
        if(grouterHome == null)
        {
            throw new IllegalArgumentException("No home specified for grouter in config file!!");
        }
        this.archiveHandlerConfig = archiveHandlerConfig;
        this.grouterHome = grouterHome;
    }

    /**
     * Getter.
     * @return
     */
    public ArchiveHandlerConfig getArchiveHandler()
    {
        return archiveHandlerConfig;
    }

    /**
     * Getter.
     * @return
     */
    public String getGrouterHome()
    {
        return grouterHome;
    }

}
