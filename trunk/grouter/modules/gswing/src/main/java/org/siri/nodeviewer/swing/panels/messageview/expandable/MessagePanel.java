package org.siri.nodeviewer.swing.panels.messageview.expandable;

import com.jidesoft.grid.HierarchicalTable;
import com.jidesoft.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Georges Polyzois
 */
class MessageContentPanel extends JPanel
{
    private HierarchicalTable table;
    private int row;

    public MessageContentPanel(HierarchicalTable table, int row)
    {
        this.table = table;
        this.row = row;
        initComponents();
    }

    public MessageContentPanel()
    {
        initComponents();
    }

    void initComponents()
    {
        setLayout(new BorderLayout(4, 4));
        setBorder(BorderFactory.createCompoundBorder(new JideTitledBorder(new PartialLineBorder(UIManager.getColor("controlShadow"), 1, PartialSide.NORTH), "Trade", JideTitledBorder.RIGHT, JideTitledBorder.CENTER),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        add(createDetailPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.AFTER_LAST_LINE);
        JideSwingUtilities.setOpaqueRecursively(this, false);
        setOpaque(true);
        //setBackground(BG1);
    }

    JComponent createDetailPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.X_AXIS));
        panel.add(new LabelCombobox("Order Type:", 'O', new String[]{"Select one", "Buy", "Sell", "Sell short", "Buy to over"}));
        panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
        panel.add(new LabelTextField("Quality:", 'Q', 8));
        panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
        panel.add(new LabelTextField("Price:", 'P', 8));
        panel.add(Box.createHorizontalStrut(6), JideBoxLayout.FIX);
        panel.add(new LabelCombobox("Price Type:", 'R', new String[]{"Select one", "Limit", "Market", "Stop", "Stop limit"}));
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