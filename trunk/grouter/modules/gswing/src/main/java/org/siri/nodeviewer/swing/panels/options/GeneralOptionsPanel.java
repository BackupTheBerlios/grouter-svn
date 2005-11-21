package org.siri.nodeviewer.swing.panels.options;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.swing.PartialLineBorder;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Georges Polyzois
 */
public class GeneralOptionsPanel
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(GeneralOptionsPanel.class);


    private CompoundBorder createRoundCornerBorder(String title)
    {
        return BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                new PartialLineBorder(Color.gray, 1, true), title), BorderFactory.createEmptyBorder(0, 6, 4, 6));
    }


    public JPanel getPanel(final AbstractDialogPage page)
    {
        // home page panel
        JPanel homePagePanel = new JPanel(new GridLayout(2, 1, 6, 6));
        JLabel label = new JLabel("Location(s):");
        label.setDisplayedMnemonic('a');
        JTextField textField = new JTextField("http://www.mozilla.org/start");
        label.setLabelFor(textField);
        JPanel textPanel = new JPanel(new BorderLayout(6, 6));
        textPanel.add(label, BorderLayout.BEFORE_LINE_BEGINS);
        textPanel.add(textField, BorderLayout.CENTER);
        ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.RIGHT);
        buttonPanel.addButton(new JButton("Use Current Page"));
        buttonPanel.addButton(new JButton("Use Bookmark..."));
        buttonPanel.addButton(new JButton("Use Blank Page"));
        homePagePanel.add(textPanel);
        homePagePanel.add(buttonPanel);
        homePagePanel.setBorder(createRoundCornerBorder(" Home Page "));

        // windows and tabs panel
        JPanel windowsAndTabsPanel = new JPanel(new BorderLayout(6, 6));
        JCheckBox checkBox = new JCheckBox("Open Link in the background");
        checkBox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
            }
        });
        windowsAndTabsPanel.add(checkBox, BorderLayout.CENTER);
        windowsAndTabsPanel.setBorder(createRoundCornerBorder(" Windows and Tabs "));

        // download folder panel
        JPanel downloadPanel = new JPanel(new GridLayout(2, 1));
        JRadioButton radio1 = new JRadioButton("Ask me where to save every file");
        JRadioButton radio2 = new JRadioButton("Save all files to this folder:");
        downloadPanel.add(radio1);
        downloadPanel.add(radio2);
        ButtonGroup group = new ButtonGroup();
        radio1.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
            }
        });
        radio2.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                page.fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
            }
        });
        group.add(radio1);
        group.add(radio2);
        downloadPanel.setBorder(createRoundCornerBorder(" Download Folder "));

        // default browser panel
        JPanel defaultPanel = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Set Mozilla Firebird as your default browser");
        defaultPanel.add(label1, BorderLayout.CENTER);
        defaultPanel.add(new JButton("Set Default Browser"), BorderLayout.AFTER_LINE_ENDS);
        defaultPanel.setBorder(createRoundCornerBorder(" Default Browser"));


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(Box.createVerticalStrut(6));
        panel.add(homePagePanel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(windowsAndTabsPanel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(downloadPanel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(defaultPanel);
        panel.add(Box.createVerticalStrut(600));
        return panel;
    }


}
