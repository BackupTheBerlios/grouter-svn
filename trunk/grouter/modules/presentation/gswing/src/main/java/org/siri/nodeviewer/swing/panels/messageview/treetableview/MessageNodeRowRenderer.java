package org.siri.nodeviewer.swing.panels.messageview.treetableview;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class MessageNodeRowRenderer extends DefaultTableCellRenderer
{
    private static Logger logger = Logger.getLogger(MessageNodeRowRenderer.class);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (value instanceof MessageNodeRow)
        {
            MessageNodeRow fileRow = (MessageNodeRow) value;
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, fileRow.getName(), isSelected, hasFocus, row, column);
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
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
