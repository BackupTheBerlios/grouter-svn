package org.siri.nodeviewer.swing.panels.serviceexplorer;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;


public class ServiceNodeTreeCellRenderer extends DefaultTreeCellRenderer
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ServiceNodeTreeCellRenderer.class);

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        if (value instanceof ServiceNode)
        {
            ServiceNode file = (ServiceNode) value;
            JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, file.getName(), sel, expanded, leaf, row, hasFocus);
            try
            {
                label.setIcon(file.getIcon());
            }
            catch (Exception e)
            {
                logger.error(e, e);

                //System.out.println(file.getServiceNodeItem().getAbsolutePath());
            }
            return label;
        }
        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}

