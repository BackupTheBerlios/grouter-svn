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
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.TabPanelListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.VerticalLayout;
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

        //add north panel
        //createNortPanel(borderPanel);




        createAndAddWestPanel(borderPanel);

        createAndAddCenterPanel(borderPanel);

        panel.add(borderPanel);

        Viewport viewport = new Viewport(panel);

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
        final TabPanel tabPanel = new TabPanel();

         panel.setBorder(false);
         panel.setPaddings(15);
         Panel verticalPanel = new Panel();
         verticalPanel.setLayout(new VerticalLayout(15));
            Button button = new Button("Add Tab", new ButtonListenerAdapter() {
             public void onClick(Button button, EventObject e) {
                 Panel tab = addTab(tabPanel);
                 tabPanel.activate(tab.getId());
                 tabPanel.scrollToTab(tab, true);
             }
         });
         button.setIconCls("new-tab-icon");
         verticalPanel.add(button);


         tabPanel.setResizeTabs(true);
         tabPanel.setMinTabWidth(115);
        tabPanel.setTabWidth(135);
         tabPanel.setEnableTabScroll(true);
         tabPanel.setWidth(450);  
         tabPanel.setHeight(250);
         tabPanel.setActiveTab(0);

         tabPanel.addListener(new TabPanelListenerAdapter() {
             public void onContextMenu(TabPanel source, Panel tab, EventObject e) {
                 showMenu(tab, e);
             }
         });

         for (int index = 0; index < 7; index++) {
             addTab();
         }
         verticalPanel.add(tabPanel);
         panel.add(verticalPanel);









        borderPanel.add(panel, new BorderLayoutData(RegionPosition.CENTER));
    }


       private Panel addTab(final TabPanel tabPanel, int index) {
         Panel tab = new Panel();
         tab.setAutoScroll(true);
         tab.setTitle("New Tab " + (++index));
         tab.setIconCls("tab-icon");
         tab.setHtml("Tab Body " + index + "<br/><br/>" );  
         tab.setClosable(true);
         tabPanel.add(tab);
         return tab;
     }


    private void createAndAddWestPanel(Panel borderPanel)
    {
        Panel westPanel = new Panel();
        westPanel.setHtml("<p>west panel</p>");
        westPanel.setTitle("West");
        westPanel.setBodyStyle("background-color: EEEEEE");
        westPanel.setCollapsible(true);
        westPanel.setWidth(200);

        BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
        westData.setSplit(true);
        westData.setMinSize(175);
        westData.setMaxSize(400);
        westData.setMargins(new Margins(0, 5, 0, 0));


        final TreePanel treePanel = new SampleTree();
        treePanel.setTitle("Default Appearance");
        treePanel.setWidth(190);
        treePanel.setHeight(400);


        borderPanel.add(treePanel, westData);
    }


       class SampleTree extends TreePanel {

           public SampleTree() {

               TreeNode root = new TreeNode("Company Heirarchy");

               TreeNode ceo = new TreeNode("Julie W. Walker");
               ceo.setExpanded(true);

               TreeNode manager1 = new TreeNode("William J. Vear");
               manager1.setExpanded(true);
               TreeNode manager2 = new TreeNode("Dennis E. Walker");  
               manager2.setExpanded(true);
               TreeNode manager3 = new TreeNode("Joann R. Williams");
               manager3.setExpanded(true);

               ceo.appendChild(manager1);
               ceo.appendChild(manager2);
               ceo.appendChild(manager3);

               TreeNode director1 = new TreeNode("Robert L. Carbaugh");
               director1.setExpanded(true);

               TreeNode director2 = new TreeNode("Agnes H. Keene");
               director2.setExpanded(true);

               manager1.appendChild(director1);
               manager1.appendChild(director2);

               TreeNode director3 = new TreeNode("Erin T. Marks");
               manager2.appendChild(director3);

               manager3.appendChild(new TreeNode("Harry L. Krieger"));

               director1.appendChild(new TreeNode("Jim H. Baker"));
               director1.appendChild(new TreeNode("Randy M. Smith"));
               director1.appendChild(new TreeNode("Annie P. Burke"));
               director2.appendChild(new TreeNode("Shirley P. Tanaka"));
               director2.appendChild(new TreeNode("Anthony C. Decarlo"));
               director2.appendChild(new TreeNode("Katherine D. Saenz"));
               director3.appendChild(new TreeNode("Carolyn M. Gauna"));
               director3.appendChild(new TreeNode("Johanna E. Armistead"));
               director3.appendChild(new TreeNode("Duane E. Ashe"));
               director3.appendChild(new TreeNode("Norman N. Gardner"));
               root.appendChild(ceo);

               setRootVisible(false);

               setTitle("Company");
               setWidth(200);
               setHeight(400);
               setRootNode(root);
               root.setExpanded(true);
           }
       }


}


