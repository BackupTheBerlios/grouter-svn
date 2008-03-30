package org.siri.nodeviewer.swing.panels.serviceexplorer;

import com.jidesoft.grid.AbstractExpandableRow;
import com.jidesoft.grid.Row;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A service node row holds a service nodeitem and extends AbstractExpandableRow
 *
 * @author Georges Polyzois
 */

public class ServiceNode extends AbstractExpandableRow
{
    private ServiceNodeItem serviceNodeItem;
    private List children = new ArrayList();
    static HashMap icons = new HashMap();

    public ServiceNode()
    {
    }

    public ServiceNode(final ServiceNodeItem serviceNodeItem)
    {
        this.serviceNodeItem = serviceNodeItem;
    }

    public Object getValueAt(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return this;
            case 1:
                return serviceNodeItem.getId();
            case 2:
                return serviceNodeItem.getName();
            case 3:
                return serviceNodeItem.getState();
        }
        return null;
    }

    public Class getCellClassAt(int columnIndex)
    {
        return null;
    }


    public void setChildren(List children)
    {
        this.children = children;
        if (this.children != null)
        {
            for (int i = 0; i < this.children.size(); i++)
            {
                Row row = (Row) this.children.get(i);
                row.setParent(this);
            }
        }
    }

    public boolean hasChildren()
    {
        if (children != null && children.size() > 0)
        {
            return true;
        } else
        {
            return false;
        }

    }

    public List getChildren()
    {
        if (children != null)
        {
            return children;
        }
        return children;
    }

    public ServiceNodeItem getServiceNodeItem()
    {
        return serviceNodeItem;
    }

    public String getName()
    {
        return serviceNodeItem.getName();
    }


    public String getId()
    {
        return serviceNodeItem.getId();
    }

    public Icon getIcon()
    {
        return serviceNodeItem.getIcon();
        /*Icon icon = (Icon) icons.get(this);
       if (icon == null)
       {
           icon = getIcon(getServiceNodeItem());
           icons.put(this, icon);
           return icon;
       } else
       {
           return icon;
       } */
    }

    public String getTypeDescription()
    {
        return serviceNodeItem.getNodeId();
        /*String desc = getTypeDescription(getServiceNodeItem());
        return desc == null ? "" : desc;*/
    }

    public String toString()
    {
        return this.getName();
    }
}
