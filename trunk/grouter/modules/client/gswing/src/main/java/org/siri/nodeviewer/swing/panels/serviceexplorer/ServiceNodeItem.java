package org.siri.nodeviewer.swing.panels.serviceexplorer;

import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * A ServiceNodeItem contains information for a service node. It is a java bean
 * with getters/setters and a public constructor which should be used to correctly
 * initialize the java bean.
 *
 * @author Georges Polyzois
 */
public class ServiceNodeItem
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ServiceNodeItem.class);
    private String nodeId;
    private String id;
    private String name;
    private String state;
    private Icon icon;

    public ServiceNodeItem(String nodeId, String id, String name, String state, Icon icon)
    {
        this.nodeId = nodeId;
        this.id = id;
        this.name = name;
        this.state = state;
        this.icon = icon;
    }

    public Icon getIcon()
    {
        return icon;
    }

    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
