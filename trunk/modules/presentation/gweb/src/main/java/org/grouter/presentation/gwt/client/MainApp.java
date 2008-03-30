package org.grouter.presentation.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import net.mygwt.ui.client.widget.*;
import net.mygwt.ui.client.widget.Button;
import net.mygwt.ui.client.widget.table.TableColumn;
import net.mygwt.ui.client.widget.table.TableColumnModel;
import net.mygwt.ui.client.widget.table.Table;
import net.mygwt.ui.client.widget.table.TableItem;
import net.mygwt.ui.client.widget.layout.*;
import net.mygwt.ui.client.Style;
import net.mygwt.ui.client.Events;
import net.mygwt.ui.client.event.Listener;
import net.mygwt.ui.client.event.BaseEvent;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Dec 25, 2007
 * Time: 10:26:04 PM
 * To change this template use File | Settings | File Templates.
 */

public class MainApp implements EntryPoint, ApplicationStateEventListener {
    final WidgetContainer messageContentPanel = MessageContainer.getInstance().getPanel();
    private static final String WIDTH = "100%";
    private static final String HEIGTH = "100%";


    public MainApp() {
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
    }

    private ContentPanel createCenterPanel(WidgetContainer messageWC) {

        TabFolder tabFolder = new TabFolder(Style.TOP);
        tabFolder.setTabWidth(85);
        tabFolder.addListener(Events.SelectionChange, new Listener() {
            public void handleEvent(BaseEvent be) {
                TabItem item = (TabItem) be.item;
                Info.show("Selection Changed", "The '{0}' itemMessages was selected", item.getText());
            }
        });

        TabItem itemMessages = new TabItem(Style.NONE);
        itemMessages.setIconStyle("icon-tabs");
        itemMessages.setText("Messages");
        itemMessages.getContainer().add(messageWC);
        //itemMessages.getContainer().add( new Label("TTTTTTT"));

        TabItem itemUsers = new TabItem(Style.CLOSE);
        itemUsers.setText("Users");
        itemUsers.setIconStyle("icon-tabs");
        itemUsers.addListener(Events.Close, new Listener() {
            public void handleEvent(BaseEvent be) {
                TabItem item = (TabItem) be.widget;
                Info.show("Close", "Closing {0}", item.getText());
            }
        });

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


    public void onModuleLoad() {

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

    public void dataChanged(ApplicationStateEvent e) {
        Info.show("Selection Changed", "Event: '{0}' ", e.getMetaInfo());
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getListenerName() {
        return "MainApp";
    }
}

