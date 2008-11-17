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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Margins;
import com.gwtext.client.core.Position;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.*;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
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


    private AsyncCallback callbackFromCalculatorService = new AsyncCallback()
    {

        public void onFailure(Throwable caught)
        {
            Window.alert("Error during invocation of the calculator service:"
                    + caught);
        }

        public void onSuccess(Object result)
        {
            MessageBox.alert("Result: " + (String) result);

            //Window.alert("Error during invocation of the calculator service:"+             (String) result);
        }

    };

    public void dataChanged(ApplicationStateEvent e)
    {
        //     Info.show("Selection Changed", "Event: '{0}' ", e.getMetaInfo());
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getListenerName()
    {
        return "MainApp";
    }


    @SuppressWarnings({"GwtToHtmlReferences"})
    public void onModuleLoad()
    {
        // Remove the loading message
        DOM.setInnerHTML(RootPanel.get("Loading-Message").getElement(), "");

      // Get the Application Container div from the DOM
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


    }

    private void createAndAddCenterPanel(Panel borderPanel)
    {
        Panel centerPanel = new Panel();
        centerPanel.setBorder(false);
        centerPanel.setPaddings(0);

        FormPanel formPanel = new FormPanel();
        formPanel.setLabelAlign(Position.TOP);
        //formPanel.setTitle("Tabs");
        formPanel.setPaddings(0);
        formPanel.setWidth(600);

        Panel topPanel = new Panel();
        topPanel.setLayout(new ColumnLayout());
        topPanel.setBorder(false);



        Button button7 = new Button("Show Me", new ButtonListenerAdapter() {
             public void onClick(Button button, EventObject e) {
                 GWTRemoteService.App.getInstance().getMessages(1, callbackFromCalculatorService);
             }
         });
        topPanel.add( button7 );

        Panel firstColumn = new Panel();
        firstColumn.setLayout(new FormLayout());
        firstColumn.setBorder(false);

        firstColumn.add(new TextField("Messages", "first"), new AnchorLayoutData("95%"));
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
        firstTab.setTitle("Messages");
        firstTab.setLayout(new FormLayout());
        firstTab.setPaddings(10);

        firstTab.add(new TextField("First Name", "first", 230, "James"));



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

        centerPanel.add(formPanel);

        //   RootPanel.get().add(centerPanel);


        borderPanel.add(centerPanel, new BorderLayoutData(RegionPosition.CENTER));
    }


    private Panel addTab(final TabPanel tabPanel, int index)
    {
        Panel tab = new Panel();
        tab.setAutoScroll(true);
        tab.setTitle("New Tab " + (++index));
        tab.setIconCls("tab-icon");
        tab.setHtml("Tab Body " + index + "<br/><br/>");
        tab.setClosable(true);


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
            TreeNode routerNodes1 = new TreeNode("Nodes");
            routerNodes1.setExpanded(true);
            router1.appendChild( routerNodes1 );
            TreeNode node1 = new TreeNode("Node 1");
            node1.setLeaf(true);
            TreeNode node2 = new TreeNode("Node 2");
            node2.setLeaf(true);
            TreeNode node3 = new TreeNode("Node 3");
            node3.setLeaf(true);
            routerNodes1.appendChild(node1);
            routerNodes1.appendChild(node2);
            routerNodes1.appendChild(node3);
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

            router1.appendChild(configuration);


            setRootVisible(true);

            setTitle("Grouter");
            setWidth(200);
            setHeight(400);
            setRootNode(root);
            root.setExpanded(true);
        }
    }


}


