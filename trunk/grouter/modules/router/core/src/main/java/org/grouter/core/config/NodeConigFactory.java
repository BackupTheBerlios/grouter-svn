package org.grouter.core.config;

import org.apache.log4j.Logger;
import org.grouter.config.NodeType;

import java.io.File;

/**
 * Use this factory (or assembler) to creae config NodeConfig objects from an xml binding type object of type
 * NodeType.
 */
public class NodeConigFactory
{
    private static Logger logger = Logger.getLogger(NodeConigFactory.class);

    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param nodeType config for a node
     * @return
     * @throws IllegalArgumentException if nodeType == null
     */
    public static NodeConfig getNode(NodeType nodeType)
    {
        if (nodeType == null)
        {
            throw new IllegalArgumentException("Config was null");
        }
        NodeConfig nodeConfig = null;
        if (nodeType.getType().equals("file-file"))
        {
            OutFolderConfig outFolderConfig = new OutFolderConfig(new File(nodeType.getOutFolder().getOutFolderPath()));
            BatchReadConfig batchReadConfig = new BatchReadConfig(nodeType.getInFolder().getBatchRead().getIsBatchOn(), nodeType.getInFolder().getBatchRead().getBatchSize(), nodeType.getInFolder().getBatchRead().getBatchThreshold());
            InFolderConfig inFolderConfig = new InFolderConfig(new File(nodeType.getInFolder().getInFolderPath()), nodeType.getInFolder().getPollIntervallMilliSeconds(), nodeType.getInFolder().getSkipFirstblankline(), batchReadConfig);
            nodeConfig = new NodeConfig(NodeConfig.Type.FILE_TO_FILE, nodeType.getId().getStringValue(), nodeType.getCreateuniquename(), inFolderConfig, outFolderConfig);
        }
        return nodeConfig;
    }

    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param nodeTypes config for all nodes
     * @return NodeConfig[] - an array of Nodes
     * @throws IllegalArgumentException if nodeTypes == null
     */
    public static NodeConfig[] getNodes(NodeType[] nodeTypes)
    {
        if (nodeTypes == null)
        {
            throw new IllegalArgumentException("Config was null - are path to config file correct?");
        }
        NodeConfig[] nodeConfigs = new NodeConfig[nodeTypes.length];
        int i = 0;
        for (NodeType nodeType : nodeTypes)
        {
            nodeConfigs[i++] = getNode(nodeType);
        }
        return nodeConfigs;
    }
}
