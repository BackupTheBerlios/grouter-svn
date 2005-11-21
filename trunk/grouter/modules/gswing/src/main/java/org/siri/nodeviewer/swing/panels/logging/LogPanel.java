package org.siri.nodeviewer.swing.panels.logging;

import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.events.ApplicationEventHandler;
import org.siri.nodeviewer.swing.events.ApplicationEventType;
import org.siri.nodeviewer.swing.events.ApplicationStateEvent;
import org.siri.nodeviewer.swing.events.ApplicationStateEventListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Georges Polyzois
 */
public class LogPanel implements ApplicationStateEventListener
{
    private static Logger logger = Logger.getLogger(LogPanel.class);
    JScrollPane scrollPane;
    JTextArea textArea;
    JPanel panel;

    /**
     * Constructor.
     */
    public LogPanel()
    {
        init();
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
    }

    /**
     * Init layout.
     */
    private void init()
    {
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        panel = new JPanel(new BorderLayout(5, 5));
        panel.add(scrollPane);
    }

    public Component getPanel()
    {
        return panel;
    }

    public void dataChanged(ApplicationStateEvent e)
    {
        if (e.getApplicationEventType() == ApplicationEventType.UPDATELOGPANEL)
        {
            textArea.setText(textArea.getText() + (String) e.getSource());

        }
    }

}
