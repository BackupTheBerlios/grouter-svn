package org.siri.nodeviewer.swing.panels.serviceexplorer;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


/**
 * A renderer for service nodes in the explorer view.
 */
public class ServiceNodeRowRenderer extends DefaultTableCellRenderer
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ServiceNodeRowRenderer.class);

    /**
     * Overrrides method from DefaultTableCellRenderer.
     *
     * @param table
     * @param value
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param column
     * @return
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (value instanceof ServiceNodeRow)
        {
            ServiceNodeRow serviceNodeRow = (ServiceNodeRow) value;
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, serviceNodeRow.getName(), isSelected, hasFocus, row, column);
            try
            {
                label.setIcon(serviceNodeRow.getIcon());
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
