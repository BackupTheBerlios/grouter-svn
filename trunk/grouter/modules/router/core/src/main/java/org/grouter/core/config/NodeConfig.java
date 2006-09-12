package org.grouter.core.config;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-21
 * Time: 14:00:23
 * To change this template use File | Settings | File Templates.
 */
public class NodeConfig
{
    private String id;
    private boolean createuniquename;
    private OutFolderConfig outFolderConfig;
    private InFolderConfig inFolderConfig;
    private BackupConfig backupConfig;
    private Type nodeType;

    public enum Type
    {
        FILE_TO_FILE
    }

    ;

    /**
     * Do not use this NodeConfig constructor direclty, instead use the NodeConfigFactory. This constructor is meant
     * to be used for file-to.file node types.
     *
     * @param nodeType         e.g. NodeType.FILE-TO-FILE
     * @param id
     * @param createuniquename
     * @param inFolderConfig
     * @param outFolderConfig
     * @thrwos IllegalArgumentException if nodeType == null || id = null || inFolderConfig == null || outFolderConfig == null
     */
    public NodeConfig(NodeConfig.Type nodeType, String id, boolean createuniquename, InFolderConfig inFolderConfig, OutFolderConfig outFolderConfig)
    {
        if (nodeType == null || id == null || inFolderConfig == null || outFolderConfig == null)
        {
            throw new IllegalArgumentException("Trying to create a NodeConfig with null parameters");
        }
        this.nodeType = nodeType;
        this.id = id;
        this.createuniquename = createuniquename;
        this.inFolderConfig = inFolderConfig;
        this.outFolderConfig = outFolderConfig;
    }


    public String getId()
    {
        return id;
    }


    public Type getNodeType()
    {
        return nodeType;
    }

    public boolean isCreateuniquename()
    {
        return createuniquename;
    }

    public OutFolderConfig getOutFolder()
    {
        return outFolderConfig;
    }

    public InFolderConfig getInFolder()
    {
        return inFolderConfig;
    }
}
