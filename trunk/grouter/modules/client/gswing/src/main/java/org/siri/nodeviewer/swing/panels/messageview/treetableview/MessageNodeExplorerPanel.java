package org.siri.nodeviewer.swing.panels.messageview.treetableview;


import com.jidesoft.grid.Row;
import com.jidesoft.grid.TreeTable;
import com.jidesoft.grid.TreeTableModel;
import com.jidesoft.swing.TableSearchable;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.panels.messageview.MessageItem;
import org.siri.nodeviewer.swing.util.IconFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 */
public class MessageNodeExplorerPanel
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(MessageNodeExplorerPanel.class);
    public TreeTable treeTable;
    protected AbstractTableModel tableModel;
    protected int _pattern;
    public final static String NETWORK = "e_network.png";
    public final static String FLAG = "flag.png";

    public MessageNodeExplorerPanel()
    {
    }

    final static TableCellRenderer messageNodeRowRenderer = new MessageNodeRowRenderer();
    final static TableCellRenderer FILE_SIZE_RENDERER = new MessageNodeSizeRenderer();
    //       final static TableCellRenderer FILE_DATE_RENDERER = new FileDateCellRenderer();

    public Component getPanel()
    {
        tableModel = createTableModel();
        treeTable = new TreeTable(tableModel);

        // configure the TreeTable
        treeTable.setRowHeight(18);
        treeTable.setShowTreeLines(true);
        treeTable.setShowGrid(false);
        treeTable.setIntercellSpacing(new Dimension(0, 0));
        treeTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        treeTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        treeTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        treeTable.getColumnModel().getColumn(3).setPreferredWidth(50);


        treeTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 1)
                {
                    if (treeTable.getModel() instanceof TreeTableModel)
                    {
                        TreeTableModel model = (TreeTableModel) treeTable.getModel();
                        int rowIndex = treeTable.rowAtPoint(e.getPoint());//Get clicked row
                        Row row = model.getRowAt(rowIndex);
                        if (row instanceof MessageNodeRow) //&& ((MetaDataTreeTableModel.MetaDataRow) row).getKey() != null)
                        {
                            logger.debug("Row : " + row);
                        }
                    }
                }
            }
        });

        // set renderers
        treeTable.getColumnModel().getColumn(0).setCellRenderer(messageNodeRowRenderer);
        //treeTable.getColumnModel().getColumn(1).setCellRenderer(FILE_SIZE_RENDERER);
        //    treeTable.getColumnModel().getColumn(3).setCellRenderer(FILE_DATE_RENDERER);

        // add searchable feature
        TableSearchable searchable = new TableSearchable(treeTable)
        {
            protected String convertElementToString(Object item)
            {
                if (item instanceof MessageNodeRow)
                {
                    return ((MessageNodeRow) item).getName();
                }
                return super.convertElementToString(item);
            }
        };
        searchable.setMainIndex(1); // only search for name column
        searchable.setSearchLabel("Focus on service name: ");


        JScrollPane scrollPane = new JScrollPane(treeTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.add(scrollPane, BorderLayout.CENTER);
        //panel.add(new JLabel("Service Nodes: "), BorderLayout.BEFORE_FIRST_LINE);
        panel.setPreferredSize(new Dimension(300, 500));
        return panel;
    }

    private TreeTableModel createTableModel()
    {
        MessageNodeItem node1root = new MessageNodeItem(new MessageItem("Feeder1", "msg id 1", "som content", "serviceId 1", true), IconFactory.getImageIcon(NETWORK));
        MessageNodeItem node1child1 = new MessageNodeItem(new MessageItem("Feeder1", "msg id 1", "som content", "serviceId 2", true), IconFactory.getImageIcon(FLAG));
        MessageNodeItem node1child2 = new MessageNodeItem(new MessageItem("Feeder1", "msg id 1", "som content", "serviceId 3", true), IconFactory.getImageIcon(FLAG));

        MessageNodeRow nodeRowChild1 = new MessageNodeRow(node1child1);
        MessageNodeRow nodeRowChild2 = new MessageNodeRow(node1child2);

        ArrayList children = new ArrayList();
        children.add(nodeRowChild1);
        children.add(nodeRowChild2);

        MessageNodeRow root1 = new MessageNodeRow(node1root);
        root1.setChildren(children);

        ///////
        MessageNodeItem nodeItemNode2 = new MessageNodeItem(new MessageItem("Feeder1", "msg id 1", "som content", "serviceId 3", true), IconFactory.getImageIcon(NETWORK));
        MessageNodeItem nodeItemNode2Child1 = new MessageNodeItem(new MessageItem("Feeder1", "msg id 1", "som content", "serviceId 3", true), IconFactory.getImageIcon(FLAG));
        MessageNodeItem nodeItemNode2Child2 = new MessageNodeItem(new MessageItem("Feeder1", "msg id 1", "som content", "serviceId 3", true), IconFactory.getImageIcon(FLAG));

        MessageNodeRow node2RowChild1 = new MessageNodeRow(nodeItemNode2Child1);
        MessageNodeRow node2RowChild2 = new MessageNodeRow(nodeItemNode2Child2);

        ArrayList children2 = new ArrayList();
        children2.add(node2RowChild1);
        children2.add(node2RowChild2);

        MessageNodeRow root2 = new MessageNodeRow(nodeItemNode2);
        root2.setChildren(children2);


        ArrayList<MessageNodeRow> rows = new ArrayList<MessageNodeRow>();
        rows.add(root1);
        rows.add(root2);

        return new MessageTreeTableModel(rows);
    }
}

