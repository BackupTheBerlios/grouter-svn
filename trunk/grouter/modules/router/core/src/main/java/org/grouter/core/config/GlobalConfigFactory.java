package org.grouter.core.config;

import org.apache.log4j.Logger;
import org.grouter.config.GlobalType;

/**
 * Use this factory (or assembler) to creae config NodeConfig objects from an xml binding type object of type
 * NodeType.
 */
public class GlobalConfigFactory
{
    private static Logger logger = Logger.getLogger(GlobalConfigFactory.class);

    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param globalType config for a node
     * @return
     * @throws IllegalArgumentException if globalType == null
     */
    public static GlobalConfig getGlobalConfig(GlobalType globalType)
    {
        if (globalType == null || globalType.getGrouterHome() == null || globalType.getGrouterHome().equalsIgnoreCase(""))
        {
            throw new IllegalArgumentException("Global config section is null, or you are lacking a valid grouter home element in your config file!");
        }

        CronJobConfig cronJobConfig = new CronJobConfig(globalType.getArchiveHandler().getCronJob());
        ArchiveHandlerConfig archiveHandlerConfig = new ArchiveHandlerConfig(cronJobConfig);
        GrouterDomainConfig grouterDomainConfig = null;
        if (globalType.getGrouterDomain().getUseGrouterDomain())
        {
            grouterDomainConfig = new GrouterDomainConfig(globalType.getGrouterDomain().getJavaNamingFactoryInitial(),
                    globalType.getGrouterDomain().getJavaNamingProviderUrl(), globalType.getGrouterDomain().getJavaNamingFactoryUrlPkgs(),
                    globalType.getGrouterDomain().getDestinationJndiName());
        }

        GlobalConfig globalConfig = new GlobalConfig(archiveHandlerConfig, globalType.getGrouterHome(), grouterDomainConfig);
        return globalConfig;
    }
}
