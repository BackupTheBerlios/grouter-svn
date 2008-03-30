package org.grouter.presentation.gwt.client;

import net.mygwt.ui.client.widget.*;
import net.mygwt.ui.client.widget.layout.RowLayout;
import net.mygwt.ui.client.widget.layout.RowData;
import net.mygwt.ui.client.widget.table.Table;
import net.mygwt.ui.client.widget.table.TableColumn;
import net.mygwt.ui.client.widget.table.TableColumnModel;
import net.mygwt.ui.client.widget.table.TableItem;
import net.mygwt.ui.client.Style;
import net.mygwt.ui.client.Events;
import net.mygwt.ui.client.event.Listener;
import net.mygwt.ui.client.event.BaseEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;

import java.util.List;


public class MessageContainer implements ApplicationStateEventListener {
    private static final String WIDTH = "100%";
    private static final String HEIGTH = "100%";
    private static TextBox searchTextBox = new TextBox();
    static Button searchButton = new Button("SystemServiceImpl");
    static IconButton infoButton = new IconButton("icon-infoButton-white");
    static HTML html = new HTML("<span class=my-label>SystemServiceImpl:</span>");
    private static WidgetContainer panel;
    private static Table table;
    private static MessageContainer messageContainer;

    private MessageContainer() {
        createTable();
        createMessageTableContentPanel();
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
    }


    public static MessageContainer getInstance() {
        if (messageContainer == null) {
            messageContainer = new MessageContainer();
        }
        return messageContainer;
    }


    private void createTable() {
        TableColumn[] tableColumns;
        TableColumnModel myModel;
        Table table;

        tableColumns = new TableColumn[3];
        tableColumns[0] = new TableColumn("Id", 25);
        tableColumns[0].minWidth = 40;
        tableColumns[0].maxWidth = 200;
        tableColumns[1] = new TableColumn("Content", 25);
        tableColumns[1].minWidth = 50;
        tableColumns[1].maxWidth = 300;
        tableColumns[2] = new TableColumn("Created", 120);
        tableColumns[2].minWidth = 300;
        tableColumns[2].maxWidth = 500;

        //Set Table Column Properties
        for (int i = 0; i < tableColumns.length; i++) {
            tableColumns[i].resizable = true;
            tableColumns[i].sortable = true;
        }
        //new TableColumnModel
        myModel = new TableColumnModel(tableColumns);
        //new Table
        table = new Table(Style.SINGLE | Style.HORIZONTAL, myModel);
        table.setBorders(false);
        table.setSize( WIDTH, HEIGTH );


        this.table = table;
    }

    public void addMessagesToTable(List messages) {
        for (int i = 0; i < messages.size(); i++) {
            Message t = (Message) messages.get(i);
            Object[] values = new Object[6];
            values[0] = new Integer(t.getId());
            values[1] = new String(t.getContent());
            values[2] = new String(t.getCreationTimestamp().toString());
            TableItem myItems = new TableItem(values);
            table.add(myItems);
        }
    }


    public void createMessageTableContentPanel() {
        RowLayout layout = new RowLayout(Style.VERTICAL);
        layout.margin = 4; 
        layout.spacing = 4;

        searchButton.addListener(Events.Click, new Listener() {
            public void handleEvent(BaseEvent be) {
                String searchString = searchTextBox.getText();
                ApplicationStateEvent event = new ApplicationStateEvent(this, "Searching for " + searchString);
                ApplicationEventHandler.getInstance().fireDataChanged(event);

                Message message = new Message();
                message.setContent("ASDAsd asd as da  sd as d sa ");
                message.setId("id");
           //     TableItem tableItem = new TableItem();
          //      tableItem.set
          //      table.add();

//                TabItem item = (TabItem) be.item;
                //Info.show("Selection Changed", "The '{0}' itemMessages was selected", searchString);
            }
        });


        infoButton.setToolTip("Enter SystemServiceImpl Criteria");
        
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(2);
        hp.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
        hp.add(html);
        hp.add(searchTextBox);
        hp.add(infoButton);
        hp.add(searchButton);

        WidgetContainer panel = new WidgetContainer();
        panel.setScrollEnabled(true);
        panel.setSize(WIDTH, HEIGTH);
        panel.setLayout(layout);
        panel.add(hp, new RowData(RowData.FILL_HORIZONTAL));
        panel.add(table, new RowData(RowData.FILL_BOTH));

        this.panel = panel;
    }


    public WidgetContainer getPanel() {
        return this.panel;
    }

    public void dataChanged(ApplicationStateEvent e) {

    }

    public String getListenerName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
