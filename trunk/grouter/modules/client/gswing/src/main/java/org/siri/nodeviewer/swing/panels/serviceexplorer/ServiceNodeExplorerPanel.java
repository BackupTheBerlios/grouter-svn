package org.siri.nodeviewer.swing.panels.serviceexplorer;


import com.jidesoft.grid.Row;
import com.jidesoft.grid.TreeTable;
import com.jidesoft.grid.TreeTableModel;
import com.jidesoft.swing.TableSearchable;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.util.IconFactory;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This panel shows a tree with service nodes using a treetable implementation.
 */
public class ServiceNodeExplorerPanel
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ServiceNodeExplorerPanel.class);
    public TreeTable treeTable;
    protected AbstractTableModel tableModel;
    protected int _pattern;
    public final static String NETWORK = "e_network.png";
    public final static String FLAG = "flag.png";
    final static TableCellRenderer serviceNodeRowRenderer = new ServiceNodeRowRenderer();
    final static TableCellRenderer FILE_SIZE_RENDERER = new ServiceNodeSizeRenderer();
    //       final static TableCellRenderer FILE_DATE_RENDERER = new FileDateCellRenderer();

    public ServiceNodeExplorerPanel()
    {
    }

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
                        if (row instanceof ServiceNodeRow) //&& ((MetaDataTreeTableModel.MetaDataRow) row).getKey() != null)
                        {
                            logger.debug("Row : " + row);
                        }
                    }
                }
            }
        });

        // set renderers
        treeTable.getColumnModel().getColumn(0).setCellRenderer(serviceNodeRowRenderer);
        //treeTable.getColumnModel().getColumn(1).setCellRenderer(FILE_SIZE_RENDERER);
        //    treeTable.getColumnModel().getColumn(3).setCellRenderer(FILE_DATE_RENDERER);

        // add searchable feature
        TableSearchable searchable = new TableSearchable(treeTable)
        {
            protected String convertElementToString(Object item)
            {
                if (item instanceof ServiceNodeRow)
                {
                    return ((ServiceNodeRow) item).getName();
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
        ServiceNodeItem nodeItemNode1 = new ServiceNodeItem("Feeder1", "snode 1", "", "", IconFactory.getImageIcon(NETWORK));
        ServiceNodeItem nodeItemNode1Child1 = new ServiceNodeItem("Feeder1", "snode 2", "service-kalle-child1", "runing", IconFactory.getImageIcon(FLAG));
        ServiceNodeItem nodeItemNode1Child2 = new ServiceNodeItem("Feeder1", "snode 3", "service-kalle-child2", "runing", IconFactory.getImageIcon(FLAG));

        ServiceNodeRow nodeRowChild1 = new ServiceNodeRow(nodeItemNode1Child1);
        ServiceNodeRow nodeRowChild2 = new ServiceNodeRow(nodeItemNode1Child2);

        ArrayList children = new ArrayList();
        children.add(nodeRowChild1);
        children.add(nodeRowChild2);

        ServiceNodeRow root1 = new ServiceNodeRow(nodeItemNode1);
        root1.setChildren(children);

        ///////
        ServiceNodeItem nodeItemNode2 = new ServiceNodeItem("Feeder2", "snode 1", "", "", IconFactory.getImageIcon(NETWORK));
        ServiceNodeItem nodeItemNode2Child1 = new ServiceNodeItem("Feeder2", "snode 2", "service-maja-child1", "runing", IconFactory.getImageIcon(FLAG));
        ServiceNodeItem nodeItemNode2Child2 = new ServiceNodeItem("Feeder2", "snode 3", "service-nisss-child2", "stopped", IconFactory.getImageIcon(FLAG));

        ServiceNodeRow node2RowChild1 = new ServiceNodeRow(nodeItemNode2Child1);
        ServiceNodeRow node2RowChild2 = new ServiceNodeRow(nodeItemNode2Child2);

        ArrayList children2 = new ArrayList();
        children2.add(node2RowChild1);
        children2.add(node2RowChild2);

        ServiceNodeRow root2 = new ServiceNodeRow(nodeItemNode2);
        root2.setChildren(children2);


        ArrayList<ServiceNodeRow> rows = new ArrayList<ServiceNodeRow>();
        rows.add(root1);
        rows.add(root2);

        return new ServiceTreeTableModel(rows);
    }
}

