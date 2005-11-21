package org.siri.nodeviewer.swing.panels.messageview;

import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.SortableTableModel;
import com.jidesoft.swing.JidePopupMenu;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Georges Polyzois
 */
public class SimpleMessageTablePanel extends JPanel
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(SimpleMessageTablePanel.class);
    private MessageTableModel model;
    public SortableTable sortableTable;

    public SimpleMessageTablePanel()
    {
    }


    public Component getPanel()
    {
        model = MessageTableModel.getInstance();

        sortableTable = new SortableTable(model);
        sortableTable.getTableHeader().addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);
                if (e.isPopupTrigger())
                {
                    showPopup(e);
                }
            }

            public void mouseReleased(MouseEvent e)
            {
                super.mouseReleased(e);
                if (e.isPopupTrigger())
                {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e)
            {
                int column = ((JTableHeader) e.getSource()).getColumnModel().getColumnIndexAtX(e.getPoint().x);
                JMenuItem[] menuItems = ((SortableTableModel) sortableTable.getModel()).getPopupMenuItems(column);
                JPopupMenu popupMenu = new JidePopupMenu();
                for (int i = 0; i < menuItems.length; i++)
                {
                    JMenuItem item = menuItems[i];
                    popupMenu.add(item);
                }
                popupMenu.show((Component) e.getSource(), e.getPoint().x, e.getPoint().y);
            }
        });

        sortableTable.setPreferredScrollableViewportSize(new Dimension(550, 400));
        return new JScrollPane(sortableTable);
    }
}