package org.siri.nodeviewer.swing.panels.messageview.treetableview;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;


public class MessageNodeTreeCellRenderer extends DefaultTreeCellRenderer
{
    private static Logger logger = Logger.getLogger(MessageNodeTreeCellRenderer.class);

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        if (value instanceof org.siri.nodeviewer.swing.panels.serviceexplorer.ServiceNodeRow)
        {
            MessageNodeRow fileRow = (MessageNodeRow) value;
            JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, fileRow.getName(), sel, expanded, leaf, row, hasFocus);
            try
            {
                label.setIcon(fileRow.getIcon());
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
            return label;
        }
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}

