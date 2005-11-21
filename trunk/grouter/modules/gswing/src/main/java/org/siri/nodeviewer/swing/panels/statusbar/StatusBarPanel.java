package org.siri.nodeviewer.swing.panels.statusbar;

import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.status.*;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.utils.SystemInfo;
import com.jidesoft.plaf.LookAndFeelFactory;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Georges Polyzois
 */
public class StatusBarPanel
{

    StatusBar statusBar = new StatusBar();

    Timer timer;
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(StatusBarPanel.class);

       public Component getStatusPanel() {
        // add status bar
        StatusBar statusBar = createStatusBar();
        JPanel dummyPanel = new JPanel();
        dummyPanel.setPreferredSize(new Dimension(600, 300));
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        //panel.add(dummyPanel, BorderLayout.CENTER);
        panel.add(statusBar, BorderLayout.AFTER_LAST_LINE);
        return panel;
    }


    public static void main(String[] args) {
        try {
            // force to Metal L&F as in JDK1.5, GTK L&F is used as default L&F. We currently don't support GTK L&F.
            if (SystemInfo.isLinux() || SystemInfo.isUnix()) {
                UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
            }
            else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        }
        catch (ClassNotFoundException e) {
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        catch (UnsupportedLookAndFeelException e) {
        }
        LookAndFeelFactory.installJideExtension();
        StatusBarPanel statusBarPanel = new StatusBarPanel();
        JFrame frame = new JFrame();
        frame.add(statusBarPanel.getStatusPanel());
        frame.setVisible(true);


       // showAsFrame(new StatusBarDemo());
    }

    private StatusBar createStatusBar() {
        // setup status bar
        StatusBar statusBar = new StatusBar();
        final ProgressStatusBarItem progress = new ProgressStatusBarItem();
        progress.setCancelCallback(new ProgressStatusBarItem.CancelCallback() {
            public void cancelPerformed() {
                timer.stop();
                timer = null;
                progress.setStatus("Cancelled");
                progress.showStatus();
            }
        });
        statusBar.add(progress, JideBoxLayout.VARY);
        ButtonStatusBarItem button = new ButtonStatusBarItem("READ-ONLY");
        button.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.BLANK));
        button.setPreferredWidth(20);
        statusBar.add(button, JideBoxLayout.FLEXIBLE);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timer != null && timer.isRunning())
                    return;
                timer = new Timer(100, new ActionListener() {
                    int i = 0;

                    public void actionPerformed(ActionEvent e) {
                        if (i == 0)
                            progress.setProgressStatus("Initializing ......");
                        if (i == 10)
                            progress.setProgressStatus("Running ......");
                        if (i == 90)
                            progress.setProgressStatus("Completing ......");
                        progress.setProgress(i++);
                        if (i > 100)
                            timer.stop();
                    }
                });
                timer.start();
            }
        });

        final LabelStatusBarItem label = new LabelStatusBarItem("Line");
        label.setText("100:42");
        label.setAlignment(JLabel.CENTER);
        statusBar.add(label, JideBoxLayout.FLEXIBLE);

        final OvrInsStatusBarItem ovr = new OvrInsStatusBarItem();
        ovr.setPreferredWidth(100);
        ovr.setAlignment(JLabel.CENTER);
        statusBar.add(ovr, JideBoxLayout.FLEXIBLE);

        final TimeStatusBarItem time = new TimeStatusBarItem();
        statusBar.add(time, JideBoxLayout.FLEXIBLE);
        final MemoryStatusBarItem gc = new MemoryStatusBarItem();
        statusBar.add(gc, JideBoxLayout.FLEXIBLE);

        return statusBar;
    }
}
