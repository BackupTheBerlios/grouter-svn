package org.siri.nodeviewer.swing.panels.serviceexplorer;


import com.jidesoft.grid.Row;
import com.jidesoft.grid.TreeTable;
import com.jidesoft.grid.TreeTableModel;
import com.jidesoft.swing.TableSearchable;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This panel shows a tree with service nodes using a treetable implementation.
 */
public class ServiceNodeExplorerPanel
{
    private static Logger logger = Logger.getLogger(ServiceNodeExplorerPanel.class);
    public TreeTable treeTable;
    protected AbstractTableModel tableModel;
    protected int _pattern;
    final static TableCellRenderer serviceNodeRowRenderer = new ServiceNodeRowRenderer();
    final static TableCellRenderer FILE_SIZE_RENDERER = new ServiceNodeSizeRenderer();
    //       final static TableCellRenderer FILE_DATE_RENDERER = new FileDateCellRenderer();

    public ServiceNodeExplorerPanel(AbstractTableModel serviceTreeTableModel)
    {
        if(serviceTreeTableModel == null)
        {
            throw new IllegalArgumentException("Can not init ServiceNodePanel");
        }
        this.tableModel = serviceTreeTableModel;
    }

    public Component getPanel()
    {

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
                return null;
//                return super.convertElementToString(item);
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

}

