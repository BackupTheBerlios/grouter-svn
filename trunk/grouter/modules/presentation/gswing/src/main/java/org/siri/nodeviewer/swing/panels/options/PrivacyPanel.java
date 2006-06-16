package org.siri.nodeviewer.swing.panels.options;

import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.plaf.CollapsiblePaneUI;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.MultilineLabel;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author Georges Polyzois
 */
public class PrivacyPanel
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(PrivacyPanel.class);


    private JComponent createPrivacyMiddlePanel()
    {
        CollapsiblePane pane1 = createCollapsiblePane("History");
        pane1.getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel label1 = new JLabel("Remember visited pages for the last ");
        JTextField text1 = new JTextField("9 ");
        text1.setColumns(4);
        JLabel label2 = new JLabel(" days");
        pane1.getContentPane().add(label1);
        pane1.getContentPane().add(text1);
        pane1.getContentPane().add(label2);

        CollapsiblePane pane2 = createCollapsiblePane("Saved Form Information");
        pane2.getContentPane().setLayout(new BorderLayout(4, 4));
        MultilineLabel label3 = new MultilineLabel("Information entered in web  page forms and the Search Bar is saved to make filling out forms and searching faster.");
        JCheckBox check1 = new JCheckBox("Save information I enter in web page forms and the Search Bar");
        check1.setBackground(Color.white);
        pane2.getContentPane().add(label3, BorderLayout.CENTER);
        pane2.getContentPane().add(check1, BorderLayout.AFTER_LAST_LINE);

        CollapsiblePane pane3 = createCollapsiblePane("Saved Passwords");
        pane3.getContentPane().setLayout(new BorderLayout(4, 4));
        MultilineLabel label4 = new MultilineLabel("Login information for web pages can be kept in the Password Manager so that you do not need to re-enter your login details every time you visit.");
        JCheckBox check2 = new JCheckBox("Remember Passwords");
        check2.setBackground(Color.white);
        pane3.getContentPane().add(label4, BorderLayout.CENTER);
        pane3.getContentPane().add(check2, BorderLayout.AFTER_LAST_LINE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(pane1);
        panel.add(pane2);
        panel.add(pane3);
        panel.add(Box.createVerticalStrut(200));
        panel.setBackground(Color.white);
        return new JScrollPane(panel);
    }


    public JPanel getPanel()
    {
        MultilineLabel label = new MultilineLabel("As you browse the web, information about where you have been, " +
                "what you have done, etc is kept in the following areas");

        JComponent middlePanel = createPrivacyMiddlePanel();

        JPanel bottomPanel = new JPanel(new BorderLayout(6, 6));
        bottomPanel.add(new JLabel("Clear all information stored while browsing:", JLabel.RIGHT), BorderLayout.CENTER);
        bottomPanel.add(new JButton("Clear All"), BorderLayout.AFTER_LINE_ENDS);

        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(middlePanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.AFTER_LAST_LINE);
        panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        return panel;
    }


    private CollapsiblePane createCollapsiblePane(String title)
    {
        CollapsiblePane pane1 = new CollapsiblePane(title);
        pane1.setBackground(Color.white);
        pane1.setStyle(CollapsiblePane.PLAIN_STYLE);
        ((CollapsiblePaneUI) pane1.getUI()).getTitlePane().setFocusable(false);
        pane1.getContentPane().setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));
        pane1.getContentPane().setOpaque(false);
        pane1.getActualComponent().setBackground(Color.white);
        JComponent actualComponent = pane1.getActualComponent();
        JideSwingUtilities.setOpaqueRecursively(actualComponent, false);
        return pane1;
    }
}
