package org.grouter.core.config;

import org.apache.log4j.Logger;
import org.grouter.config.NodeType;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-21
 * Time: 17:00:58
 * To change this template use File | Settings | File Templates.
 */
public class NodeFactory
{
    private static Logger logger = Logger.getLogger(NodeFactory.class);

    public static Node getNode(NodeType nodeType)
    {
        Node node = null;
        if (nodeType==null)
        {
            throw new IllegalArgumentException("Config was null");
        }

        if(nodeType.getType().equals("file-to-file"))
        {
            OutFolder outFolder = new OutFolder(nodeType.getOutFolder().getOutFolderPath());
            BatchRead batchRead = new BatchRead(nodeType.getInFolder().getBatchRead().getIsBatchOn(),nodeType.getInFolder().getBatchRead().getBatchSize(),nodeType.getInFolder().getBatchRead().getBatchThreshold());
            InFolder inFolder = new InFolder(nodeType.getInFolder().getInFolderPath(),nodeType.getInFolder().getPollIntervallMilliSeconds(),nodeType.getInFolder().getSkipFirstblankline(),batchRead);
            node = new Node(Node.Type.FILE_TO_FILE, nodeType.getId().toString(),nodeType.getCreateuniquename(),inFolder, outFolder);
        }
        return node;
    }

}
