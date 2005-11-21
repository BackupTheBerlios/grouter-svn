package org.siri.nodeviewer.swing.panels.toolbar;

import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideSwingUtilities;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.events.ApplicationEventHandler;
import org.siri.nodeviewer.swing.events.ApplicationEventType;
import org.siri.nodeviewer.swing.events.ApplicationStateEvent;
import org.siri.nodeviewer.swing.events.MessageItemInjector;
import org.siri.nodeviewer.swing.panels.options.OptionsDialog;
import org.siri.nodeviewer.swing.util.IconFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Georges Polyzois
 */
public class ToolBar extends JToolBar
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(ToolBar.class);
    private JideButton[] buttons;


    public final static String move = "icons/e_move.png";
    public final static String copy = "icons/e_copy.png";
    public final static String email = "icons/e_email.png";
    public final static String delet = "icons/e_delete.png";


    public ToolBar(String name)
    {
        super(name);
    }

    private JideButton createJideButton(String name, Icon icon)
    {
        final JideButton button = new JideButton(name, icon);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }


    public Component getPanel()
    {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        buttons = new JideButton[3];
        buttons[0] = createJideButton("Options", IconFactory.getImageIcon("options.png"));
        buttons[0].addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 1)
                {
                    showOptionsDialog();
                }
            }
        });
        buttons[1] = createJideButton("Move", IconFactory.getImageIcon("e_move.png"));
        buttons[2] = createJideButton("Test Inject", IconFactory.getImageIcon("e_copy.png"));
        buttons[2].addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 1)
                {
                    startIncetingTestData();
                }
            }
        });

        for (int i = 0; i < buttons.length; i++)
        {
            JideButton button = buttons[i];
            panel.add(button);
        }
        return JideSwingUtilities.createTopPanel(panel);
    }


    private void startIncetingTestData()
    {
        ApplicationStateEvent event = new ApplicationStateEvent("Starting the test injector.", "", ApplicationEventType.UPDATELOGPANEL);
        ApplicationEventHandler.getInstance().fireDataChanged(event);


        MessageItemInjector messageItemInjector = new MessageItemInjector();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(messageItemInjector);
    }


    private void showOptionsDialog()
    {
        OptionsDialog firebirdOptionsDialog = new OptionsDialog(new JFrame(), "");
        firebirdOptionsDialog.showOptionsDialog(true);
    }
}






