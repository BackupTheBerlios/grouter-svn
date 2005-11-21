package org.siri.nodeviewer.swing.panels.messageview.treetableview;

import com.jidesoft.grid.AbstractExpandableRow;
import com.jidesoft.grid.Row;

import javax.swing.*;
import java.util.List;

/**
 * @author Georges Polyzois
 */

public class MessageNodeRow extends AbstractExpandableRow
{
    private MessageNodeItem messageNodeItem;
    private List children;

    public MessageNodeRow(final MessageNodeItem messageNodeItem)
    {
        this.messageNodeItem = messageNodeItem;
    }

    public Object getValueAt(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return this;
            case 1:
                return messageNodeItem.getMessageItem().getServiceId();
            case 2:
                return messageNodeItem.getMessageItem().getMessageId();
            case 3:
                return messageNodeItem.getMessageItem().getContent();
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

    public MessageNodeItem getMessageNodeItem()
    {
        return messageNodeItem;
    }

    public String getName()
    {
        return getName(getMessageNodeItem());
    }

    public Icon getIcon()
    {
        return messageNodeItem.getIcon();
    }

    public String getTypeDescription()
    {
        String desc = getTypeDescription(getMessageNodeItem());
        return desc == null ? "" : desc;
    }

    public static Icon getIcon(MessageNodeItem messageNodeItem)
    {
        return messageNodeItem.getIcon();
    }

    public static String getTypeDescription(MessageNodeItem messageNodeItem)
    {
        return messageNodeItem.getMessageItem().getContent();
    }

    public static String getName(MessageNodeItem messageNodeItem)
    {
        return messageNodeItem.getMessageItem().getContent();
    }
}
