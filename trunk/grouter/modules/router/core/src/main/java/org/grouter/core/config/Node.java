package org.grouter.core.config;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-21
 * Time: 14:00:23
 * To change this template use File | Settings | File Templates.
 */
public class Node
{
    private String id;
    private boolean createuniquename;
    private OutFolder outFolder;
    private InFolder inFolder;
    private Backup backup;
    private Type nodeType;

    public enum Type
    {
        FILE_TO_FILE
    }

    ;

    /**
     * Do not use this Node constructor direclty, instead use the NodeFactory. This constructor is meant
     * to be used for file-to.file node types.
     *
     * @param nodeType         e.g. NodeType.FILE-TO-FILE
     * @param id
     * @param createuniquename
     * @param inFolder
     * @param outFolder
     * @thrwos IllegalArgumentException if nodeType == null || id = null || inFolder == null || outFolder == null
     */
    public Node(Node.Type nodeType, String id, boolean createuniquename, InFolder inFolder, OutFolder outFolder)
    {
        if (nodeType == null || id == null || inFolder == null || outFolder == null)
        {
            throw new IllegalArgumentException("Trying to create a Node with null parameters");
        }
        this.nodeType = nodeType;
        this.id = id;
        this.createuniquename = createuniquename;
        this.inFolder = inFolder;
        this.outFolder = outFolder;
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

    public OutFolder getOutFolder()
    {
        return outFolder;
    }

    public InFolder getInFolder()
    {
        return inFolder;
    }
}
