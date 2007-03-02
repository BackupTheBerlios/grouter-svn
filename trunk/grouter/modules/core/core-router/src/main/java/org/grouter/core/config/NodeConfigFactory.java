package org.grouter.core.config;

import org.apache.log4j.Logger;
import org.grouter.config.NodeType;
import org.grouter.config.GlobalType;

import java.io.File;

/**
 * Use this factory (or assembler) to creae config NodeConfig objects from an xml binding type object of type
 * NodeType.
 */
public class NodeConfigFactory
{
 //   private static Logger logger = Logger.getLogger(NodeConfigFactory.class);


    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param nodeType config for a node
     * @return
     * @throws IllegalArgumentException if nodeType == null
     */
    /*
    public static NodeConfig getNode(NodeType nodeType, GlobalType globalType)
    {
        if (nodeType == null || globalType == null || globalType.getGrouterHome() == null)
        {
            throw new IllegalArgumentException("Config had null values - check your xml setup for grouter!");
        }
        NodeConfig nodeConfig = null;
        if (nodeType.getType().equals("file-file"))
        {
            nodeConfig = createNodeConfig(nodeType, nodeType.getUseRelativeRootpath(), globalType.getGrouterHome());

        }
        return nodeConfig;

    }

    */

    /**
     * Creates nodeconfig either with a realtive to grouter home or with an absolut path.
     *
     * @param nodeType
     * @param isRelative if this node is relative to grouter home then we will use the relativePath
     * @return
     */
    /*
    private static NodeConfig createNodeConfig(NodeType nodeType, boolean isRelative, String relativePath)
    {
        String nodePath = "";
        if (isRelative)
        {
            nodePath = relativePath + "/nodes/" + nodeType.getId().getStringValue();

        }
        logger.debug("Using nodepath :" + nodePath);
        NodeConfig nodeConfig;
        OutFolderConfig outFolderConfig = new OutFolderConfig(new File(nodePath + nodeType.getOutFolder().getOutPath()));

        BatchReadConfig batchReadConfig = new BatchReadConfig(nodeType.getInFolder().getBatchRead().getIsBatchOn(),
                nodeType.getInFolder().getBatchRead().getBatchSize(), nodeType.getInFolder().getBatchRead().getBatchThreshold());
        FilterConfig filterConfig = new FilterConfig( nodeType.getInFolder().getExcludeFilter() );
        InFolderConfig inFolderConfig = new InFolderConfig(new File(nodePath + nodeType.getInFolder().getInPath()),
                nodeType.getInFolder().getPollIntervallMilliSeconds(), nodeType.getInFolder().getSkipFirstblankline(),
                batchReadConfig, filterConfig);

        LocalStoreConfig localStoreConfig = null;
        if (nodeType.getLocalStore() != null && nodeType.getLocalStore().getLocalStoreOn())
        {
            localStoreConfig = new LocalStoreConfig(new File(nodePath + nodeType.getLocalStore().getBackupPath()));
        }



        nodeConfig = new NodeConfig(NodeConfig.Type.FILE_TO_FILE, nodeType.getId().getStringValue(), nodeType.getCreateuniquename(),
                inFolderConfig, outFolderConfig, localStoreConfig);
        return nodeConfig;
    }
    */

    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param nodeTypes config for all nodes
     * @return NodeConfig[] - an array of Nodes
     * @throws IllegalArgumentException if nodeTypes == null
     */
    /*
    public static NodeConfig[] getNodes(NodeType[] nodeTypes, GlobalType globalType)
    {
        if (nodeTypes == null || globalType == null)
        {
            throw new IllegalArgumentException("Config was null - are path to config file correct?");
        }
        NodeConfig[] nodeConfigs = new NodeConfig[nodeTypes.length];
        int i = 0;
        for (NodeType nodeType : nodeTypes)
        {
            nodeConfigs[i++] = getNode(nodeType, globalType);
        }
        return nodeConfigs;
    }


    */
}
