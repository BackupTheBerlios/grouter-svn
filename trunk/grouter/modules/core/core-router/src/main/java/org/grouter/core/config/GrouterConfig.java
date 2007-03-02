package org.grouter.core.config;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-30
 * Time: 17:11:18
 * To change this template use File | Settings | File Templates.
 */
public class GrouterConfig
{
    private String name;
    private GlobalConfig globalConfig;
    private NodeConfig[] nodeConfigs;

    /**
     * Constructor for grouter config.
     *
     * @param name
     * @param nodeConfigs
     * @param globalConfig
     *
     * @throws IllegalArgumentException if nodeConfigs == null || globalConfig == null
     */
    public GrouterConfig(String name, NodeConfig[] nodeConfigs, GlobalConfig globalConfig)
    {
        if(name == null || nodeConfigs == null || globalConfig == null)
        {
            throw new IllegalArgumentException("Nodes or globalsettings elements can not be null!");
        }
        this.name = name;
        this.nodeConfigs = nodeConfigs;
        this.globalConfig = globalConfig;
    }

    /**
     * Getter.
     * @return
     */
    public NodeConfig[] getNodes()
    {
        return nodeConfigs;
    }


    /**
     * Getter.
     * @return
     */
    public GlobalConfig getGlobalSettingsConfig()
    {
        return globalConfig;
    }

    /**
     * Getter.
     * @return
     */
    public String getName()
    {
        return name;
    }

}
