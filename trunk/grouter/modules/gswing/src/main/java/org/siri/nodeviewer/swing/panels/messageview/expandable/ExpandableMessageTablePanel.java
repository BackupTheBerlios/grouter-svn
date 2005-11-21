package org.siri.nodeviewer.swing.panels.messageview.expandable;

import com.jidesoft.grid.*;
import com.jidesoft.pane.BookmarkPane;
import org.siri.nodeviewer.swing.panels.messageview.MessageTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * @author Georges Polyzois
 */
public class ExpandableMessageTablePanel
{
    protected static final Color BG1 = new Color(255, 255, 255);
    protected TableModel quotesTableModel;

    public ExpandableMessageTablePanel()
    {
    }

    public String getName()
    {
        return "HierarchicalTable (Trading) Demo";
    }


    public Component getPanel()
    {
        HierarchicalTable table = createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    // create property treeTable
    private HierarchicalTable createTable()
    {
        quotesTableModel = MessageTableModel.getInstance();
        final HierarchicalTable table = new HierarchicalTable(quotesTableModel)
        {
            protected SortableTableModel createSortableTableModel(TableModel model)
            {
                return new StripeSortableTableModel(model);
            }
        };
        table.setName("Quote Table");
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setExpandableColumn(-1);
        table.setSingleExpansion(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (table.getSelectedRow() != -1)
                {
                    table.expandRow(table.getSelectedRow());
                }
            }
        });
        table.setComponentFactory(new HierarchicalTableComponentFactory()
        {
            public Component createChildComponent(HierarchicalTable table, Object value, int row)
            {
                BookmarkPane pane = new BookmarkPane();
                pane.setBorder(new HierarchicalPanelBorder());
                pane.addTab("Message Content", new MessageContentPanel(table, row));
                pane.addTab("Sender & Receivers", new OptionPanel(table, row));
                return new HierarchicalPanel(pane, BorderFactory.createEmptyBorder());
            }

            public void destroyChildComponent(HierarchicalTable table, Component component, int row)
            {
            }
        });
        table.setPreferredScrollableViewportSize(new Dimension(600, 400));
        table.getSelectionModel().setAnchorSelectionIndex(0);
//        treeTable.getSelectionModel().setLeadSelectionIndex(0);
        return table;
    }

    class StripeSortableTableModel extends SortableTableModel implements StyleModel
    {
        protected final Color BACKGROUND1 = new Color(253, 253, 244);
        protected final Color BACKGROUND2 = new Color(255, 255, 255);

        public StripeSortableTableModel(TableModel model)
        {
            super(model);
        }

        CellStyle cellStyle = new CellStyle();

        public CellStyle getCellStyleAt(int rowIndex, int columnIndex)
        {
            if (rowIndex % 2 == 0)
            {
                cellStyle.setBackground(BACKGROUND1);
            } else
            {
                cellStyle.setBackground(BACKGROUND2);
            }
            return cellStyle;
        }

        public boolean isCellStyleOn()
        {
            return true;
        }
    }

    class HierarchicalPanelBorder implements Border
    {
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
        {
            g.setColor(UIManager.getColor("controlShadow"));
            g.drawLine(x, y, x + width - 1, y);
            g.setColor(UIManager.getColor("controlShadow"));
            g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
            g.setColor(UIManager.getColor("control"));
            g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
            g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
        }

        public Insets getBorderInsets(Component c)
        {
            return new Insets(1, 1, 1, 1);
        }

        public boolean isBorderOpaque()
        {
            return true;
        }

    }
}