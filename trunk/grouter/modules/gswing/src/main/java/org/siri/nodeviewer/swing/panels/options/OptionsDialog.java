package org.siri.nodeviewer.swing.panels.options;

import com.jidesoft.dialog.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.utils.SystemInfo;
import org.siri.nodeviewer.swing.util.IconFactory;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 */
public class OptionsDialog extends MultiplePageDialog
{
    GeneralOptionsPanel generalOptionsPanel = new GeneralOptionsPanel();
    PrivacyPanel privacyPanel = new PrivacyPanel();


    public OptionsDialog(Frame owner, String title) throws HeadlessException
    {
        super(owner, title);
    }

    protected void initComponents()
    {
        super.initComponents();
        getContentPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getIndexPanel().setBackground(Color.white);
        getButtonPanel().setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        getPagesPanel().setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
    }

    public ButtonPanel createButtonPanel()
    {
        ButtonPanel buttonPanel = super.createButtonPanel();
        AbstractAction okAction = new AbstractAction(UIManager.getString("OptionPane.okButtonText"))
        {
            public void actionPerformed(ActionEvent e)
            {
                setDialogResult(RESULT_AFFIRMED);
                setVisible(false);
                dispose();
            }
        };
        AbstractAction cancelAction = new AbstractAction(UIManager.getString("OptionPane.cancelButtonText"))
        {
            public void actionPerformed(ActionEvent e)
            {
                setDialogResult(RESULT_CANCELLED);
                setVisible(false);
                dispose();
            }
        };
        ((JButton) buttonPanel.getButtonByName(ButtonNames.OK)).setAction(okAction);
        ((JButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setAction(cancelAction);
        setDefaultCancelAction(cancelAction);
        setDefaultAction(okAction);
        return buttonPanel;
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(600, 520);
    }

    public static void main(String[] args)
    {
        try
        {
            // force to Metal L&F as in JDK1.5, GTK L&F is used as default L&F. We currently don't support GTK L&F.
            if (SystemInfo.isLinux() || SystemInfo.isUnix())
            {
                UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
            } else
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        }
        catch (ClassNotFoundException e)
        {
        }
        catch (InstantiationException e)
        {
        }
        catch (IllegalAccessException e)
        {
        }
        catch (UnsupportedLookAndFeelException e)
        {
        }
        LookAndFeelFactory.installJideExtension();

        OptionsDialog firebirdOptionsDialog = new OptionsDialog(new JFrame(), "");
        firebirdOptionsDialog.showOptionsDialog(true);
    }

    public void showOptionsDialog(final boolean exit)
    {
        final OptionsDialog dialog = new OptionsDialog(null, "Options Dialog");
        dialog.setStyle(MultiplePageDialog.ICON_STYLE);
        PageList model = new PageList();
        dialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                if (exit)
                {
                    System.exit(0);
                } else
                {
                    dialog.dispose();
                }
            }
        });

        // setup model
        AbstractDialogPage panel1 = new FirebirdOptionPage1("General", IconFactory.getImageIcon("general.png"));
        AbstractDialogPage panel2 = new FirebirdOptionPage2("Privacy", IconFactory.getImageIcon("privacy.png"));
        model.append(panel1);
        model.append(panel2);
        dialog.setPageList(model);
        dialog.pack();
        JideSwingUtilities.globalCenterWindow(dialog);
        dialog.setVisible(true);
    }

    public class FirebirdOptionPage extends AbstractDialogPage
    {
        public FirebirdOptionPage(String name)
        {
            super(name);
        }

        public FirebirdOptionPage(String name, Icon icon)
        {
            super(name, icon);
        }

        public void lazyInitialize()
        {
            initComponents();
        }

        public void initComponents()
        {
            BannerPanel headerPanel = new BannerPanel(getTitle(), null);
            headerPanel.setForeground(Color.WHITE);
            headerPanel.setBackground(new Color(10, 36, 106));
            headerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.darkGray, Color.darkGray, Color.gray));

            setLayout(new BorderLayout());
            add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
            add(new JLabel("This is just a demo. \"" + getFullTitle() + "\" page is not implemented yet.", JLabel.CENTER), BorderLayout.CENTER);
        }

    }

    public class FirebirdOptionPage1 extends FirebirdOptionPage
    {
        public FirebirdOptionPage1(String name, Icon icon)
        {
            super(name, icon);
        }

        public void initComponents()
        {
            super.initComponents();
            add(generalOptionsPanel.getPanel(this));//, BorderLayout.CENTER));
        }
    }

    public class FirebirdOptionPage2 extends FirebirdOptionPage
    {
        public FirebirdOptionPage2(String name, Icon icon)
        {
            super(name, icon);
        }

        public void initComponents()
        {
            super.initComponents();
            add(privacyPanel.getPanel(), BorderLayout.CENTER);
        }
    }


}
