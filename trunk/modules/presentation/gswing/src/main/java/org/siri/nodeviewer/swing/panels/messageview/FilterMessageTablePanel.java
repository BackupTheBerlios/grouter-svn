package org.siri.nodeviewer.swing.panels.messageview;

import com.jidesoft.grid.*;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;


public class FilterMessageTablePanel extends JPanel
{

    public FilterMessageTablePanel()
    {
    }

    public String getName()
    {
        return "QuickFilter (Table) Demo";
    }


    public Component getPanel()
    {
        final TableModel tableModel = MessageTableModel.getInstance();
        final QuickFilterPane quickFilterPane = new QuickFilterPane(new SortableTableModel(tableModel), new int[]{0, 1, 2});

        JPanel quickSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        QuickTableFilterField filterField = new QuickTableFilterField(quickFilterPane.getDisplayTableModel(), new int[]{0, 1, 2, 3});
        quickSearchPanel.add(filterField);
        quickSearchPanel.setBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "QuickTableFilterField", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP));

        JideSplitPane pane = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);
        quickFilterPane.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "QuickFilterPane", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(6, 0, 0, 0)));
        pane.addPane(quickFilterPane);

        JPanel tablePanel = new JPanel(new BorderLayout(2, 2));
        tablePanel.setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Filtered Song List", JideTitledBorder.LEADING, JideTitledBorder.ABOVE_TOP),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        final JLabel label = new JLabel(filterField.getDisplayTableModel().getRowCount() + " out of " + tableModel.getRowCount() + " items");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        SortableTable sortableTable = new SortableTable(filterField.getDisplayTableModel());
        sortableTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        sortableTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        sortableTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        filterField.setTable(sortableTable);
        JScrollPane scrollPane = new JScrollPane(sortableTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        filterField.getDisplayTableModel().addTableModelListener(new TableModelListener()
        {
            public void tableChanged(TableModelEvent e)
            {
                if (e.getSource() instanceof FilterableTableModel)
                {
                    int count = ((TableModel) e.getSource()).getRowCount();
                    label.setText(count + " out of " + tableModel.getRowCount() + " items");
                }
            }
        });

        tablePanel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        tablePanel.add(scrollPane);
        pane.addPane(tablePanel);

        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.add(quickSearchPanel, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(pane);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                ((AbstractTableModel) tableModel).fireTableDataChanged();
            }
        });
        panel.add(resetButton, BorderLayout.AFTER_LAST_LINE);
        return panel;
    }

}
