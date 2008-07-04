package org.grouter.presentation.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;

import org.grouter.presentation.gwt.client.widget.MessageContainer;


/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Dec 25, 2007
 * Time: 10:26:04 PM
 * To change this template use File | Settings | File Templates.
 */

public class MainApp implements EntryPoint, ApplicationStateEventListener
{
    //final WidgetContainer messageContentPanel = MessageContainer.getInstance().getPanel();
    private static final String WIDTH = "100%";
    private static final String HEIGTH = "100%";
    /**
     * An instance of the constants.
     */
    //private CwConstants constants;


    public MainApp()
    {
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
    }

    /*
    private ContentPanel createCenterPanel(WidgetContainer messageWC)
    {

        TabFolder tabFolder = new TabFolder(Style.TOP);
        tabFolder.setTabWidth(85);
        tabFolder.addListener(Events.SelectionChange, new Listener()
        {
            public void handleEvent(BaseEvent be)
            {
                TabItem item = (TabItem) be.item;
                Info.show("Selection Changed", "The '{0}' itemMessages was selected", item.getText());
            }
        });

        TabItem itemRouter = new TabItem(Style.NONE);
        itemRouter.setIconStyle("icon-tabs");
        itemRouter.setText("Router");
        itemRouter.getContainer().add(messageWC);

        TabItem itemNodes = new TabItem(Style.NONE);
        itemNodes.setIconStyle("icon-tabs");
        itemNodes.setText("Nodes");
        itemNodes.getContainer().add(messageWC);

        TabItem itemMessages = new TabItem(Style.NONE);
        itemMessages.setIconStyle("icon-tabs");
        itemMessages.setText("Messages");
        itemMessages.getContainer().add(messageWC);
        //itemMessages.getContainer().add( new Label("TTTTTTT"));

        TabItem itemUsers = new TabItem(Style.CLOSE);
        itemUsers.setText("Users");
        itemUsers.setIconStyle("icon-tabs");
        itemUsers.addListener(Events.Close, new Listener()
        {
            public void handleEvent(BaseEvent be)
            {
                TabItem item = (TabItem) be.widget;
                Info.show("Close", "Closing {0}", item.getText());
            }
        });

        tabFolder.add(itemRouter);
        tabFolder.add(itemNodes);
        tabFolder.add(itemMessages);
        tabFolder.add(itemUsers);
        tabFolder.setLayout(new FillLayout());
        tabFolder.setSelection(itemMessages);

        ContentPanel panel = new ContentPanel(Style.HEADER);
        panel.animateCollapse = true;
        panel.setText("Grouter");

        panel.setLayout(new FillLayout());
        panel.add(tabFolder);

        return panel;
    }


    public void onModuleLoad()
    {
        Window.setTitle("Grouter");

        final ContentPanel centerPanel = createCenterPanel(messageContentPanel);

        WidgetContainer c = new WidgetContainer();
        c.setStyleName("my-border-layout");
        c.setLayout(new BorderLayout());

        BorderLayoutData northData = new BorderLayoutData(Style.NORTH, 20);
        northData.borders = false;
        c.add(new Label(""), northData);

        BorderLayoutData southData = new BorderLayoutData(Style.SOUTH, 20);
        southData.borders = false;
        c.add(new Label(""), southData);

        c.add(centerPanel, new BorderLayoutData(Style.CENTER));
        c.setSize(WIDTH, HEIGTH);

        RootPanel.get().add(c);
    }
    */

    public void dataChanged(ApplicationStateEvent e)
    {
        //     Info.show("Selection Changed", "Event: '{0}' ", e.getMetaInfo());
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getListenerName()
    {
        return "MainApp";
    }


    public void onModuleLoad()
    {

        // Create a Dock Panel
        DockPanel dock = new DockPanel();
        dock.setStyleName("cw-DockPanel");
        dock.setSpacing(4);
        dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);


        // Return the content
        dock.ensureDebugId("cwDockPanel");


        //GWTRemoteService.App.getInstance().getMessages(null,this);

// Create a tab bar with three items.
        TabBar bar = new TabBar();
        bar.addTab("Router");
        bar.addTab("Node");
        bar.addTab("Message");
        bar.addTab("User");
        bar.addTab("Job");

        // Hook up a tab listener to do something when the user selects a tab.
        bar.addTabListener(new TabListener()
        {
            public void onTabSelected(SourcesTabEvents sender, int tabIndex)
            {
                // Let the user know what they just did.
                Window.alert("You clicked tab " + tabIndex);
            }

            public boolean onBeforeTabSelected(SourcesTabEvents sender,
                                               int tabIndex)
            {

                // Just for fun, let's disallow selection of 'bar'.
                if (tabIndex == 1)
                    return false;
                return true;
            }
        });


        // Add text all around
        dock.add(bar, DockPanel.NORTH);
        dock.add(new HTML("test"), DockPanel.SOUTH);
        //dock.add(new HTML(constants.cwDockPanelEast()), DockPanel.EAST);

        // Add scrollable text in the center
        //HTML contents = new HTML(constants.cwDockPanelCenter());
        //ScrollPanel scroller = new ScrollPanel(contents);
        //scroller.setSize("400px", "100px");
        //dock.add(scroller, DockPanel.CENTER);

        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setWidget(dock);




        // Add it to the root panel.
        RootPanel.get().add(dock);
    }

}


