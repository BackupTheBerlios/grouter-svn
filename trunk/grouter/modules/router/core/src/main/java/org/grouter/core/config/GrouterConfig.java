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
    private NodeConfig[] nodeConfigs;


    /**
     * Constructor for grouter config.
     *
     * @param nodeConfigs
     * @throws IllegalArgumentException if nodeConfigs == null
     */
    public GrouterConfig(NodeConfig[] nodeConfigs)
    {
        if(nodeConfigs == null)
        {
            throw new IllegalArgumentException("Nodes can not be null!");
        }
        this.nodeConfigs = nodeConfigs;
    }

    /**
     * Getter.
     * @return
     */
    public NodeConfig[] getNodes()
    {
        return nodeConfigs;
    }

    
}
