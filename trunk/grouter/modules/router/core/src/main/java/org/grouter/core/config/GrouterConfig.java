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
    private GlobalConfig globalConfig;
    private NodeConfig[] nodeConfigs;

    /**
     * Constructor for grouter config.
     *
     * @param nodeConfigs
     * @param globalConfig
     * @throws IllegalArgumentException if nodeConfigs == null || globalConfig == null
     */
    public GrouterConfig(NodeConfig[] nodeConfigs, GlobalConfig globalConfig)
    {
        if(nodeConfigs == null || globalConfig == null)
        {
            throw new IllegalArgumentException("Nodes or globalsettings elements can not be null!");
        }
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

}
