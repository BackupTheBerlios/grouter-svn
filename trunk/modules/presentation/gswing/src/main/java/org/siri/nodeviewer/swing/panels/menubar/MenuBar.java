package org.siri.nodeviewer.swing.panels.menubar;

import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.utils.Lm;
import com.jidesoft.utils.SystemInfo;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.Main;

import javax.swing.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Georges Polyzois
 */
public class MenuBar extends JMenuBar
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(MenuBar.class);
    /**
     * Used for callback on mainframe.
     */
    private static Main mainFrame;
    public static JMenuItem redoMenuItem;
    public static JMenuItem undoMenuItem;
    private static WindowAdapter windowListener;
    private static boolean autohideAll = false;

    public MenuBar(Main mainFrame)
    {
        this.mainFrame = mainFrame;

    }

    public static JMenuBar createMenuBar()
    {
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = createFileMenu();
        JMenu viewMenu = createViewMenu();
        JMenu windowMenu = createWindowsMenu();
        JMenu optionMenu = createOptionMenu();
        JMenu lnfMenu = createLookAndFeelMenu();
        JMenu helpMenu = createHelpMenu();

        menu.add(fileMenu);
        menu.add(viewMenu);
        menu.add(windowMenu);
        menu.add(optionMenu);
        menu.add(lnfMenu);
        menu.add(helpMenu);


        return menu;
    }

    private static JMenu createHelpMenu()
    {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic('H');
        JMenuItem item = new JMenuItem("About JIDE Products");
        item.setMnemonic('A');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                Lm.showAboutMessageBox();
            }
        });
        menu.add(item);
        return menu;
    }

    private static JMenu createWindowsMenu()
    {
        JMenu menu = new JMenu("Window");
        menu.setMnemonic('W');

        JMenuItem item = null;

        undoMenuItem = new JMenuItem("Undo");
        menu.add(undoMenuItem);
        redoMenuItem = new JMenuItem("Redo");
        menu.add(redoMenuItem);
        undoMenuItem.setEnabled(false);
        redoMenuItem.setEnabled(false);

        undoMenuItem.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().undo();
                refreshUndoRedoMenuItems();
            }
        });
        redoMenuItem.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().redo();
                refreshUndoRedoMenuItems();
            }
        });

        menu.addSeparator();

        item = new JMenuItem("Load Default Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().loadLayoutData();
            }
        });
        menu.add(item);

        item = new JMenuItem("Load Design Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().loadLayoutDataFrom("design");
            }
        });
        menu.add(item);

        item = new JMenuItem("Load Debug Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().loadLayoutDataFrom("debug");
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Save as Default Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().saveLayoutData();
            }
        });
        menu.add(item);

        item = new JMenuItem("Save as Design Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().saveLayoutDataAs("design");
            }
        });
        menu.add(item);

        item = new JMenuItem("Save as Debug Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().saveLayoutDataAs("debug");
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Reset Layout");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().setUseFrameBounds(false);
                mainFrame.getDockingManager().setUseFrameState(false);
                mainFrame.getDockingManager().resetToDefault();
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Toggle Auto Hide All");
        item.setMnemonic('T');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (!autohideAll)
                {
                    mainFrame.getDockingManager().saveLayoutDataAs("fullscreen");
                    mainFrame.getDockingManager().autohideAll();
                    autohideAll = true;
                } else
                {
                    // call next two methods so that the farme bounds and state will not change.
                    mainFrame.getDockingManager().setUseFrameBounds(false);
                    mainFrame.getDockingManager().setUseFrameState(false);
                    mainFrame.getDockingManager().loadLayoutDataFrom("fullscreen");
                    autohideAll = false;

                }
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Show Workspace Area");
        item.setMnemonic('S');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().showWorkspace();
            }
        });
        menu.add(item);

        item = new JMenuItem("Hide Workspace Area");
        item.setMnemonic('H');
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().hideWorkspace();
            }
        });
        menu.add(item);

        return menu;
    }


    private static void refreshUndoRedoMenuItems()
    {
        undoMenuItem.setEnabled(mainFrame.getDockingManager().getUndoManager().canUndo());
        undoMenuItem.setText(mainFrame.getDockingManager().getUndoManager().getUndoPresentationName());
        redoMenuItem.setEnabled(mainFrame.getDockingManager().getUndoManager().canRedo());
        redoMenuItem.setText(mainFrame.getDockingManager().getUndoManager().getRedoPresentationName());
    }

    private static JMenu createViewMenu()
    {
        JMenuItem item;
        JMenu menu = new JMenu("View");
        menu.setMnemonic('V');

        item = new JMenuItem(Main.FRAMEID_SERVICE_NODES, JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME1));
        item.setMnemonic('S');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().showFrame(Main.FRAMEID_SERVICE_NODES);
            }
        });
        menu.add(item);

        item = new JMenuItem(Main.FRAMEID_MESSAGES, JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME2));
        item.setMnemonic('M');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().showFrame(Main.FRAMEID_MESSAGES);
            }
        });
        menu.add(item);

        item = new JMenuItem(Main.FRAMEID_LOGS, JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME3));
        item.setMnemonic('L');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.getDockingManager().showFrame(Main.FRAMEID_LOGS);
            }
        });
        menu.add(item);

        menu.addSeparator();
        return menu;
    }

    private static JMenu createOptionMenu()
    {
        JMenu menu = new JMenu("Options");
        menu.setMnemonic('P');

        JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("Frames Floatable");
        checkBoxMenuItem.setMnemonic('F');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    mainFrame.getDockingManager().setFloatable(((JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isFloatable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Autohidable");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    mainFrame.getDockingManager().setAutohidable(((JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isAutohidable());

        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Hidable");
        checkBoxMenuItem.setMnemonic('H');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    mainFrame.getDockingManager().setHidable(((JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isHidable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Rearrangable");
        checkBoxMenuItem.setMnemonic('R');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    mainFrame.getDockingManager().setRearrangable(((JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isHidable());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Frames Resizable");
        checkBoxMenuItem.setMnemonic('S');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                    mainFrame.getDockingManager().setResizable(((JCheckBoxMenuItem) e.getSource()).isSelected());
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isResizable());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        JMenu buttonsMenu = new JMenu("Available Titlebar Buttons");

        checkBoxMenuItem = new JCheckBoxMenuItem("Close Button");
        checkBoxMenuItem.setMnemonic('C');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ((JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_CLOSE);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Autohide Button");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ((JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_AUTOHIDE);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Float Button");
        checkBoxMenuItem.setMnemonic('F');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ((JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_FLOATING);
            }
        });
        checkBoxMenuItem.setSelected(true);
        buttonsMenu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Maximize Button");
        checkBoxMenuItem.setMnemonic('M');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean selected = ((JCheckBoxMenuItem) e.getSource()).isSelected();
                toggleButton(selected, DockableFrame.BUTTON_MAXIMIZE);
            }
        });
        checkBoxMenuItem.setSelected(false);
        buttonsMenu.add(checkBoxMenuItem);

        menu.add(buttonsMenu);

        menu.addSeparator();

        checkBoxMenuItem = new JCheckBoxMenuItem("Continuous Layout");
        checkBoxMenuItem.setMnemonic('C');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    mainFrame.getDockingManager().setContinuousLayout(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (mainFrame.getDockingManager().isContinuousLayout())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                                "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Continuous Layout</FONT></B><FONT FACE='Tahoma'>" +
                                "<FONT FACE='Tahoma' SIZE='3'><BR><BR><B>An option to continuously layout affected components during resizing." +
                                "<BR></B><BR>This is the same option as in JSplitPane. If the option is true, when you resize" +
                                "<BR>the JSplitPane's divider, it will continuously redisplay and laid out during user" +
                                "<BR>intervention." +
                                "<BR><BR>Default: off</FONT>" +
                                "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isContinuousLayout());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        checkBoxMenuItem = new JCheckBoxMenuItem("Easy Tab Docking");
        checkBoxMenuItem.setMnemonic('E');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    mainFrame.getDockingManager().setEasyTabDock(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (mainFrame.getDockingManager().isEasyTabDock())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                                "<B><FONT COLOR='BLUE' FACE='Tahoma' SIZE='4'>Easy Tab Docking </FONT></B>" +
                                "<BR><BR><FONT FACE='Tahoma' SIZE='3'><B>An option to make the tab-docking of a dockable frame easier</B>" +
                                "<BR><BR>It used to be dragging a dockable frame and pointing to the title" +
                                "<BR>bar of another dockable frame to tab-dock with it. However if " +
                                "<BR>this option on, pointing to the middle portion of any dockable " +
                                "<BR>frame will tab-dock with that frame." +
                                "<BR><BR>Default: off</FONT>" +
                                "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isEasyTabDock());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Title Bar Dragging All Tabs");
        checkBoxMenuItem.setMnemonic('T');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    mainFrame.getDockingManager().setDragAllTabs(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (mainFrame.getDockingManager().isDragAllTabs())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                                "<B><FONT COLOR='BLUE' FACE='Tahoma' SIZE='4'>Title Bar Dragging All Tabs</FONT></B>" +
                                "<BR><BR><FONT FACE='Tahoma' SIZE='3'><B>An option for title bar dragging</B>" +
                                "<BR><BR>If user drags the title bar, by default, all tabs in the tabbed pane will be dragged." +
                                "<BR>However if you set this options to be false, only the active tab will be dragged" +
                                "<BR>if user drags the title bar." +
                                "<BR><BR>Default: on</FONT>" +
                                "<BR></HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isDragAllTabs());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Allow Nested Floating Windows");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    mainFrame.getDockingManager().setNestedFloatingAllowed(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (mainFrame.getDockingManager().isNestedFloatingAllowed())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                                "<FONT FACE='Tahoma' SIZE='4'><B><FONT COLOR='#0000FF'>Nested Floating Windows<BR></FONT></B><BR></FONT>" +
                                "<FONT FACE='Tahoma' SIZE='3'><B>An option to allow nested windows when in floating mode</B>" +
                                "<BR><BR>JIDE Docking Framework can allow you to have as many nested windows in one floating " +
                                "<BR>container as you want. However, not all your users want to have that complexity. So we " +
                                "<BR>leave it as an option and you can choose to turn it on or off. " +
                                "<BR><BR>Default: off</FONT> <BR>" +
                                "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isNestedFloatingAllowed());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Show Gripper");
        checkBoxMenuItem.setMnemonic('S');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    mainFrame.getDockingManager().setShowGripper(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (mainFrame.getDockingManager().isShowGripper())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                                "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>Show Gripper</B><BR></FONT><BR></FONT>" +
                                "<FONT FACE='Tahoma' SIZE='3'><B>An option to give user a visual hint that the dockable frame can be dragged<BR></B>" +
                                "<BR>Normal tabs in JTabbedPane can not be dragged. However in our demo, " +
                                "<BR>most of them can be dragged. To make it obvious to user, we added an " +
                                "<BR>option so that a gripper is painted on the tab or the title bar of those " +
                                "<BR>dockable frames which can be dragged." +
                                "<BR><BR>Default: off</FONT><BR>" +
                                "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isShowGripper());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("Show TitleBar");
        checkBoxMenuItem.setMnemonic('T');
        checkBoxMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    mainFrame.getDockingManager().getFrame("Property").setShowTitleBar(((JCheckBoxMenuItem) e.getSource()).isSelected());
//                    mainFrame.getDockingManager().setShowTitleBar(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (mainFrame.getDockingManager().isShowTitleBar())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                                "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>Show TitleBar</B><BR></FONT><BR></FONT>" +
                                "<FONT FACE='Tahoma' SIZE='3'><B>An option to show/hide dockable frame's title bar<BR></B>" +
                                "<BR><BR>Default: on</FONT><BR>" +
                                "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isShowTitleBar());
        menu.add(checkBoxMenuItem);

        checkBoxMenuItem = new JCheckBoxMenuItem("SideBar Rollover");
        checkBoxMenuItem.setMnemonic('A');
        checkBoxMenuItem.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JCheckBoxMenuItem)
                {
                    mainFrame.getDockingManager().setSidebarRollover(((JCheckBoxMenuItem) e.getSource()).isSelected());
                    if (mainFrame.getDockingManager().isSidebarRollover())
                    {
                        Lm.showPopupMessageBox("<HTML>" +
                                "<FONT FACE='Tahoma' SIZE='4'><FONT COLOR='#0000FF'><B>SideBar Rollover</B><BR></FONT><BR></FONT>" +
                                "<FONT FACE='Tahoma' SIZE='3'><B>An option to control the sensibility of tabs on sidebar<BR></B>" +
                                "<BR>Each tab on four sidebars is corresponding to a dockable frame. Usually when " +
                                "<BR>user moves mouse over the tab, the dockable frame will show up. However in Eclipse" +
                                "<BR>you must click on it to show the dockable frame. This option will allow you to " +
                                "<BR>control the sensibility of it." +
                                "<BR><BR>Default: on</FONT><BR>" +
                                "</HTML>");
                    }
                }
            }
        });
        checkBoxMenuItem.setSelected(mainFrame.getDockingManager().isSidebarRollover());
        menu.add(checkBoxMenuItem);

        menu.addSeparator();

        JRadioButtonMenuItem radioButtonMenuItem1 = new JRadioButtonMenuItem("Draw Full Outline When Dragging");
        radioButtonMenuItem1.setMnemonic('D');
        radioButtonMenuItem1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JRadioButtonMenuItem)
                {
                    mainFrame.getDockingManager().setOutlineMode(DefaultDockingManager.FULL_OUTLINE_MODE);
                    Lm.showPopupMessageBox("<HTML>" +
                            "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Outline Paint Mode</FONT></B><FONT FACE='Tahoma'>" +
                            "<FONT SIZE='4'>" +
                            "<FONT COLOR='#0000FF' SIZE='3'><BR><BR><B>An option of how to paint the outline during dragging.</B></FONT>" +
                            "<BR><BR><FONT SIZE='3'>Since our demo is purely based on Swing, and there is no way to have transparent native " +
                            "<BR>window using Swing. So we have to develop workarounds to paint the outline of a dragging frame. " +
                            "<BR>As a result, we get two ways to draw the outline. Since neither is perfect, we just leave it as " +
                            "<BR>an option to user to choose. You can try each of the option and see which one you like most." +
                            "<BR><B><BR>Option 1: PARTIAL_OUTLINE_MODE</B><BR>Pros: Fast, very smooth, works the best if user " +
                            "of your application always keeps it as full screen" +
                            "<BR>Cons: Partial outline or no outline at all if outside main frame although it's there wherever " +
                            "your mouse is." +
                            "<BR><BR><B>Option 2: FULL_OUTLINE_MODE</B>" +
                            "<BR>Pros: It always draw the full outline" +
                            "<BR>Cons: Sometimes it's flickering. Slower comparing with partial outline mode." +
                            "<BR><BR>Default: PARTIAL_OUTLINE_MODE</FONT>" +
                            "<BR></HTML>");
                }
            }
        });
        radioButtonMenuItem1.setSelected(mainFrame.getDockingManager().getOutlineMode() == DefaultDockingManager.FULL_OUTLINE_MODE);
        menu.add(radioButtonMenuItem1);

        JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem("Draw Partial Outline When Dragging");
        radioButtonMenuItem2.setMnemonic('P');
        radioButtonMenuItem2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() instanceof JRadioButtonMenuItem)
                {
                    mainFrame.getDockingManager().setOutlineMode(DefaultDockingManager.PARTIAL_OUTLINE_MODE);
                    Lm.showPopupMessageBox("<HTML>" +
                            "<B><FONT FACE='Tahoma' SIZE='4' COLOR='#0000FF'>Outline Paint Mode</FONT></B><FONT FACE='Tahoma'>" +
                            "<FONT SIZE='4'><FONT COLOR='#0000FF'><BR></FONT><BR></FONT><B>An option of how to paint the outline during dragging. " +
                            "<BR><BR></B>Since our demo is purely based on Swing, and there is no way to have transparent native " +
                            "<BR>window using Swing. So we have to develop workarounds to paint the outline of a dragging frame. " +
                            "<BR>As a result, we get two ways to draw the outline. Since neither is perfect, we just leave it as " +
                            "<BR>an option to user to choose. You can try each of the option and see which one you like most." +
                            "<BR><B><BR>Option 1: PARTIAL_OUTLINE_MODE</B>" +
                            "<BR>Pros: Fast, very smooth" +
                            "<BR>Cons: Partial outline or no outline at all if outside main frame although it&#39;s there wherever your mouse is." +
                            "<BR><BR><B>Option 2: FULL_OUTLINE_MODE</B>" +
                            "<BR>Pros: It always draw the full outline<BR>Cons: Sometimes it&#39;s flickering. Slower comparing with partial outline mode.</FONT>" +
                            "<BR><BR><FONT FACE='Tahoma'>Default: PARTIAL_OUTLINE_MODE</FONT>" +
                            "<BR></HTML>");
                }
            }
        });
        radioButtonMenuItem2.setSelected(mainFrame.getDockingManager().getOutlineMode() == DefaultDockingManager.PARTIAL_OUTLINE_MODE);
        menu.add(radioButtonMenuItem2);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonMenuItem1);
        buttonGroup.add(radioButtonMenuItem2);

        return menu;
    }

    private static void toggleButton(boolean selected, int button)
    {
        Collection names = mainFrame.getDockingManager().getAllFrames();
        for (Iterator iterator = names.iterator(); iterator.hasNext();)
        {
            String name = (String) iterator.next();
            DockableFrame frame = mainFrame.getDockingManager().getFrame(name);
            if (selected)
            {
                frame.setAvailableButtons(frame.getAvailableButtons() | button);
            } else
            {
                frame.setAvailableButtons(frame.getAvailableButtons() & ~button);
            }
        }
    }

    private static JMenu createFileMenu()
    {
        JMenuItem item;

        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');

        item = new JMenuItem("Exit");
        item.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                clearUp();
            }
        });
        menu.add(item);
        return menu;
    }

    /**
     * Look and feel menu.
     *
     * @return JMenu
     */
    private static JMenu createLookAndFeelMenu()
    {
        JMenuItem item;

        JMenu lnfMenu = new JMenu("Look And Feel");
        lnfMenu.setMnemonic('L');

        item = new JMenuItem("Window Look And Feel (VSNET style)");
        item.setEnabled(SystemInfo.isWindows());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.WINDOWS_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);
                mainFrame.getDockingManager().updateComponentTreeUI();
                mainFrame.getRootPane().getJMenuBar().updateUI();
            }
        });
        lnfMenu.add(item);

        item = new JMenuItem("Windows Look And Feel (Office 2003 style)");
        item.setEnabled(SystemInfo.isWindows());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.WINDOWS_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.OFFICE2003_STYLE);
                mainFrame.getDockingManager().updateComponentTreeUI();
                mainFrame.getRootPane().getJMenuBar().updateUI();
            }
        });
        lnfMenu.add(item);

        item = new JMenuItem("Windows Look And Feel (Eclipse style)");
        lnfMenu.add(item);
        item.setEnabled(SystemInfo.isWindows());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.WINDOWS_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.ECLIPSE_STYLE);
                mainFrame.getDockingManager().updateComponentTreeUI();
                mainFrame.getRootPane().getJMenuBar().updateUI();
            }
        });

        lnfMenu.addSeparator();

        item = new JMenuItem("Metal Look And Feel (VSNET style)");
        lnfMenu.add(item);
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);
                mainFrame.getDockingManager().updateComponentTreeUI();
                mainFrame.getRootPane().getJMenuBar().updateUI();
            }
        });

        item = new JMenuItem("Metal Look And Feel (Eclipse style)");
        lnfMenu.add(item);
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.METAL_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.ECLIPSE_STYLE);
                mainFrame.getDockingManager().updateComponentTreeUI();
                mainFrame.getRootPane().getJMenuBar().updateUI();
            }
        });

        lnfMenu.addSeparator();

        item = new JMenuItem("Aqua Look And Feel (Mac OS X)");
        lnfMenu.add(item);
        item.setEnabled(SystemInfo.isMacOSX());
        item.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    UIManager.setLookAndFeel(LookAndFeelFactory.AQUA_LNF);
                }
                catch (ClassNotFoundException e1)
                {
                }
                catch (InstantiationException e1)
                {
                }
                catch (IllegalAccessException e1)
                {
                }
                catch (UnsupportedLookAndFeelException e1)
                {
                }
                LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);
                mainFrame.getDockingManager().updateComponentTreeUI();
                mainFrame.getRootPane().getJMenuBar().updateUI();
            }
        });

        // Shading effect is replaced by Office2003 L&F
        lnfMenu.addSeparator();

        final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("Add Shading Effect (only on VSNET style)");
        lnfMenu.add(menuItem);
        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (menuItem.isSelected())
                {
                    System.setProperty("shadingtheme", "true");
                } else
                {
                    System.setProperty("shadingtheme", "false");
                }
                mainFrame.getDockingManager().updateComponentTreeUI();
                mainFrame.getRootPane().getJMenuBar().updateUI();
            }
        });

        return lnfMenu;
    }


    private static void clearUp()
    {
        mainFrame.removeWindowListener(windowListener);
        windowListener = null;

        if (mainFrame.getDockingManager() != null)
        {
            mainFrame.getDockingManager().saveLayoutData();
        }

        mainFrame.dispose();
        Lm.setParent(null);
        mainFrame = null;
        System.exit(0);
    }


}
