/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.grouter.presentation.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.gwtext.client.core.FxConfig;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.Position;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.*;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;


/**
 * Main entrypoint for starting.
 *
 * @author Georges Polyzois
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
        Panel panel = new Panel();
        panel.setBorder(false);
        panel.setPaddings(15);
        panel.setLayout(new FitLayout());

        Panel borderPanel = new Panel();
        borderPanel.setLayout(new BorderLayout());
        borderPanel.getEl().fadeIn(new FxConfig(0));
        borderPanel.getEl().fadeOut(new FxConfig(0));

        //add north panel
        //createNortPanel(borderPanel);


        createAndAddWestPanel(borderPanel);

        createAndAddCenterPanel(borderPanel);

        panel.add(borderPanel);

        //  Viewport viewport = new Viewport(panel);

        /* // Create a Dock Panel
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

        */
    }

    private void createAndAddCenterPanel(Panel borderPanel)
    {
        Panel panel = new Panel();
        panel.setBorder(false);
        panel.setPaddings(15);

        FormPanel formPanel = new FormPanel();
        formPanel.setLabelAlign(Position.TOP);
        formPanel.setTitle("Inner Tabs");
        formPanel.setPaddings(5);
        formPanel.setWidth(600);

        Panel topPanel = new Panel();
        topPanel.setLayout(new ColumnLayout());
        topPanel.setBorder(false);

        Panel firstColumn = new Panel();
        firstColumn.setLayout(new FormLayout());
        firstColumn.setBorder(false);

        firstColumn.add(new TextField("First Name", "first"), new AnchorLayoutData("95%"));
        firstColumn.add(new TextField("Company", "company"), new AnchorLayoutData("95%"));
        topPanel.add(firstColumn, new ColumnLayoutData(0.5));

        Panel secondColumn = new Panel();
        secondColumn.setLayout(new FormLayout());
        secondColumn.setBorder(false);

        secondColumn.add(new TextField("Last Name", "last"), new AnchorLayoutData("95%"));
        secondColumn.add(new TextField("Email", "email"), new AnchorLayoutData("95%"));
        topPanel.add(secondColumn, new ColumnLayoutData(0.5));

        formPanel.add(topPanel);

        TabPanel tabPanel = new TabPanel();
        tabPanel.setPlain(true);
        tabPanel.setActiveTab(0);
        tabPanel.setHeight(235);

        Panel firstTab = new Panel();
        firstTab.setTitle("Personal Details");
        firstTab.setLayout(new FormLayout());
        firstTab.setPaddings(10);

        firstTab.add(new TextField("First Name", "first", 230, "James"));
        firstTab.add(new TextField("Last  Name", "last", 230));
        firstTab.add(new TextField("Company", "company", 230));
        firstTab.add(new TextField("Email", "email", 230));
        tabPanel.add(firstTab);

        Panel secondTab = new Panel();
        secondTab.setTitle("Phone Numbers");
        secondTab.setLayout(new FormLayout());
        secondTab.setPaddings(10);

        secondTab.add(new TextField("Home", "home", 230, "(888) 555-2222"));
        secondTab.add(new TextField("Business", "business", 230));
        secondTab.add(new TextField("Mobile", "mobile", 230));
        secondTab.add(new TextField("Fax", "fax", 230));
        tabPanel.add(secondTab);


        formPanel.add(tabPanel);
        formPanel.addButton(new Button("Save"));
        formPanel.addButton(new Button("Cancel"));

        panel.add(formPanel);

        //   RootPanel.get().add(panel);  


        borderPanel.add(panel, new BorderLayoutData(RegionPosition.CENTER));
    }


    private Panel addTab(final TabPanel tabPanel, int index)
    {
        Panel tab = new Panel();
        tab.setAutoScroll(true);
        tab.setTitle("New Tab " + (++index));
        tab.setIconCls("tab-icon");
        tab.setHtml("Tab Body " + index + "<br/><br/>");
        tab.setClosable(true);
        tab.getEl().fadeIn(new FxConfig(0));
        tab.getEl().fadeOut(new FxConfig(0));


        tabPanel.add(tab);


        return tab;
    }


    private void createAndAddWestPanel(Panel borderPanel)
    {
        BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
        westData.setSplit(true);
        westData.setMinSize(175);
        westData.setMaxSize(400);
        westData.setMargins(new Margins(0, 5, 0, 0));

        final TreePanel treePanel = new SampleTree();
        treePanel.setWidth(190);
        treePanel.setHeight(400);

        borderPanel.add(treePanel, westData);
    }


    class SampleTree extends TreePanel
    {
        public SampleTree()
        {
            TreeNode root = new TreeNode("Routers");

            TreeNode router1 = new TreeNode("Router 1");
            router1.setExpanded(true);
            TreeNode node1 = new TreeNode("Node 1");
            node1.setLeaf(true);
            TreeNode node2 = new TreeNode("Node 2");
            node2.setLeaf(true);
            TreeNode node3 = new TreeNode("Node 3");
            node3.setLeaf(true);
            router1.appendChild(node1);
            router1.appendChild(node2);
            router1.appendChild(node3);
            root.appendChild(router1);

            TreeNode configuration = new TreeNode("Configuration");
            configuration.setExpanded(true);
            TreeNode database = new TreeNode("Database");
            database.setLeaf(true);
            configuration.appendChild(database);
            TreeNode jndi = new TreeNode("JNDI");
            jndi.setLeaf(true);
            configuration.appendChild(jndi);
            TreeNode logging = new TreeNode("Logging");
            logging.setLeaf(true);
            configuration.appendChild(logging);

            root.appendChild(configuration);


            setRootVisible(false);

            setTitle("Grouter");
            setWidth(200);
            setHeight(400);
            setRootNode(root);
            root.setExpanded(true);
        }
    }


}


