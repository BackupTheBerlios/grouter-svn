package org.siri.nodeviewer.swing;


import com.jidesoft.action.CommandBar;
import com.jidesoft.action.DefaultDockableBarDockableHolder;
import com.jidesoft.action.DockableBar;
import com.jidesoft.action.DockableBarFactory;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.DockableFrameFactory;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideButton;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.panels.logging.LogPanel;
import org.siri.nodeviewer.swing.panels.menubar.MenuBar;
import org.siri.nodeviewer.swing.panels.messageview.MessageTabbedPane;
import org.siri.nodeviewer.swing.panels.serviceexplorer.ServiceNodeExplorerPanel;
import org.siri.nodeviewer.swing.panels.statusbar.StatusBarPanel;
import org.siri.nodeviewer.swing.panels.toolbar.ToolBar;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Main extends DefaultDockableBarDockableHolder
{
    private static Logger logger = Logger.getLogger(Main.class);
    private static MenuBar menuBar;
    private static ToolBar toolBar = new ToolBar("ToolBar");
    private static LogPanel logPanel = new LogPanel();
    private static MessageTabbedPane messageTabbedPane = new MessageTabbedPane();
    private final StatusBarPanel statusBarPanel = new StatusBarPanel();
    private final static ServiceNodeExplorerPanel serviceNodeExplorerPanel = new ServiceNodeExplorerPanel();
    public static final String FRAMEID_SERVICE_NODES = "Service Nodes";
    public static final String FRAMEID_MESSAGES = "Messages";
    public static final String FRAMEID_LOGS = "Logs";
    private static final String FILE_NAME = "C:\\gepo\\mycvsprojects\\siri\\modules\\swingclient_dockable\\dockablelayout.xml";


    /**
     * Creates a new fram using a title as input. Loads config from xml file.
     *
     * @param title
     * @throws java.awt.HeadlessException
     */
    public Main(String title) throws HeadlessException
    {
        super(title);

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException e)
        {
            logger.error(e, e);
        }
        catch (InstantiationException e)
        {
            logger.error(e, e);
        }
        catch (IllegalAccessException e)
        {
            logger.error(e, e);
        }
        catch (UnsupportedLookAndFeelException e)
        {
            logger.error(e, e);
        }
        LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);

        // Add additional UIDefault used by JIDE to UIDefault treeTable. You have an option to specify
        // a style. If leaving it as empty, it will use VSNET_STYLE.
        LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);

        //mainFrame = new Main("Siri");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try
        {
            this.load(FILE_NAME);
            ///////////////////////////////////////////////
            // TODO: If you added code to saveLayoutData() to user preference when application exits, -
            // TODO: change the following line(s) to use loadLayoutData() instead so that the same  -
            // TODO: layout can be persisted. See below.
            //frame.getDockingManager().loadLayoutData();
            this.getDockingManager().resetToDefault();

            this.getDockableBarManager().resetToDefault();

            // add menu bar
            menuBar = new MenuBar(this);
            this.setJMenuBar(menuBar.createMenuBar());
        }
        catch (ParserConfigurationException e)
        {
            logger.error(e, e);
        }
        catch (SAXException e)
        {
            logger.error(e, e);
        }
        catch (IOException e)
        {
            logger.error(e, e);
        }

        this.toFront();
    }

    /**
     * Loads initial layout data from file.
     *
     * @param fileName
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException
     */
    public void load(String fileName) throws SAXException, ParserConfigurationException, IOException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        load(document);
    }

    /**
     * Loads initial layout data from Document.
     *
     * @param document
     */
    public void load(Document document)
    {
        ////////////////////////////////////////////////
        // Begin to creates and adds DockableFrames
        //
        getDockingManager().beginLoadLayoutData();
        getDockingManager().setDockableFrameFactory(new DockableFrameFactory()
        {
            public DockableFrame create(String key)
            {
                if (key.equals("Logs"))
                {
                    return createLogFrame(key);
                } else if (key.equals("Messages"))
                {
                    return createMessageFrame(key);
                } else if (key.equals("Service Nodes"))
                {
                    return createServiceNodeFrame(key);
                }

                DockableFrame frame = new DockableFrame(key);
                frame.getContentPane().add(new JScrollPane(new JTextArea()));
                return frame;
            }
        });
        getDockingManager().loadInitialLayout(document);
        //
        // End of creates and add DockableFrames
        ////////////////////////////////////////////////

        //User might change the layout after application started. When
        //application exits, it will save current layout. So loadLayoutData() will
        // load that layout back. If there is no saved layout file, it will use the
        // exactly layout as initial layout.
        //getDockingManager().loadLayoutData();

        ////////////////////////////////////////////////
        // Begin to creates and adds DockableBars
        //
        getDockableBarManager().beginLoadLayoutData();

        getDockableBarManager().setDockableBarFactory(new DockableBarFactory()
        {
            public DockableBar create(String key)
            {
               /* if (key.equals("StatusBar"))
                {
                    //return createStatusBar();
                    DockableBar bar = new CommandBar();
                    bar.add(statusBarPanel.getStatusPanel());
                    return bar;
                }*/
                if (key.equals("ToolBar"))
                {
                    //return createStatusBar();
                    CommandBar bar = new CommandBar("ToolBar");
                    bar.add(toolBar.getPanel());
                    return bar;
                }

                return null;
            }
        });
        getDockableBarManager().loadInitialLayout(document);

        // Add status bar to root container
        getDockableBarManager().getRootPaneContainer().getContentPane().add(statusBarPanel.getStatusPanel(),BorderLayout.SOUTH);


    }


    public static void main(String[] args)
    {
        new Main("Siri Viewer");

    }

    /**
     * Log frame for output of processes.
     *
     * @param key
     * @return DockableFrame
     */
    public static DockableFrame createLogFrame(String key)
    {
        DockableFrame frame = new DockableFrame(key, JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME6));
        frame.getContentPane().add(logPanel.getPanel());
        return frame;
    }


    /**
     * The central frame for displaying messages.
     *
     * @param key
     * @return DockableFrame
     */
    public static DockableFrame createMessageFrame(String key)
    {
        DockableFrame frame = new DockableFrame(key, JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME6));
        frame.getContentPane().add(new JScrollPane(messageTabbedPane.getTabbedPane()));
        return frame;
    }

    public static DockableFrame createServiceNodeFrame(String key)
    {
        DockableFrame frame = new DockableFrame(key, JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME6));
        frame.getContentPane().add(new JScrollPane(serviceNodeExplorerPanel.getPanel()));
        return frame;
    }
}

