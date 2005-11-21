package org.siri.nodeviewer.swing.panels.messageview.expandable;

import com.jidesoft.grid.HierarchicalTable;
import com.jidesoft.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Georges Polyzois
 */
class OptionPanel extends JPanel
{
    private HierarchicalTable _table;
    private int _row;

    public OptionPanel(HierarchicalTable table, int row)
    {
        _table = table;
        _row = row;
        initComponents();
    }

    public OptionPanel()
    {
        initComponents();
    }

    void initComponents()
    {
        setLayout(new BorderLayout(4, 4));
        setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialLineBorder(UIManager.getColor("controlShadow"), 1, PartialSide.NORTH), "Option", JideTitledBorder.RIGHT, JideTitledBorder.CENTER),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        add(createDetailPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.AFTER_LAST_LINE);
        JideSwingUtilities.setOpaqueRecursively(this, false);
        setOpaque(true);
    }

    JComponent createDetailPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.X_AXIS));
        panel.add(new LabelCombobox("Order Type:", 'O', new String[]{"Select one", "Buy to open", "Buy to close", "Sell to open", "Sell to close"}));
        panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
        panel.add(new LabelTextField("# of Contracts:", 'C', 8));
        panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
        panel.add(new LabelTextField("Price:", 'P', 8));
        panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
        panel.add(new LabelCombobox("Order Type:", 'R', new String[]{"Select one", "Limit", "Market", "Stop market", "Stop limit"}));
        panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
        panel.add(new LabelCombobox("Term:", 'T', new String[]{"Day", "GTC"}));
        return panel;
    }

    JComponent createButtonPanel()
    {
        com.jidesoft.dialog.ButtonPanel buttonPanel = new com.jidesoft.dialog.ButtonPanel();
        buttonPanel.addButton(new JButton(new AbstractAction("Trade")
        {
            public void actionPerformed(ActionEvent e)
            {
            }
        }));
        buttonPanel.addButton(new JButton(new AbstractAction("Cancel")
        {
            public void actionPerformed(ActionEvent e)
            {
            }
        }));
        return buttonPanel;
    }
}
