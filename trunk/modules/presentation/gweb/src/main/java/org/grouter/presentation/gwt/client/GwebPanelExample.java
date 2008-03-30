package org.grouter.presentation.gwt.client;

import net.mygwt.ui.client.Events;
import net.mygwt.ui.client.Style;
import net.mygwt.ui.client.event.BaseEvent;
import net.mygwt.ui.client.event.Listener;
import net.mygwt.ui.client.event.SelectionListener;
import net.mygwt.ui.client.widget.Button;
import net.mygwt.ui.client.widget.ButtonBar;
import net.mygwt.ui.client.widget.Info;
import net.mygwt.ui.client.widget.TabFolder;
import net.mygwt.ui.client.widget.TabItem;
import net.mygwt.ui.client.widget.WidgetContainer;
import net.mygwt.ui.client.widget.layout.FlowLayout;
import net.mygwt.ui.client.widget.layout.RowData;
import net.mygwt.ui.client.widget.layout.RowLayout;

public class GwebPanelExample extends Page
{
    private int count = 1;

    protected void createWidget(WidgetContainer container)
    {
        final TabFolder tabFolder = new TabFolder(Style.TOP);
        tabFolder.setTabWidth(85);
        tabFolder.addListener(Events.SelectionChange, new Listener()
        {
            public void handleEvent(BaseEvent be)
            {
                TabItem item = (TabItem) be.item;
                Info.show("Selection Changed", "The '{0}' item was selected", item.getText());
            }
        });

        TabItem item = new TabItem(Style.NONE);
        item.setText("GWT");
        item.setIconStyle("icon-tabs");
        item.setText("http://code.google.com/webtoolkit");
        tabFolder.add(item);
        tabFolder.setSelection(item);

        item = new TabItem(Style.CLOSE);
        item.setText("Close");
        item.setIconStyle("icon-tabs");
        item.addListener(Events.Close, new Listener() {
            public void handleEvent(BaseEvent be) {
                TabItem item = (TabItem) be.widget;
                Info.show("Close", "Closing {0}", item.getText());
            }
        });
        WidgetContainer c = item.getContainer();
        c.setScrollEnabled(true);
        c.setLayout(new FlowLayout(0));
        c.addText("Test data");
        tabFolder.add(item);

        item = new TabItem(Style.CLOSE);
        item.setText("Tab 3");
        item.setIconStyle("icon-tabs");
        item.addListener(Events.Close, new Listener() {
            public void handleEvent(BaseEvent be) {
                TabItem item = (TabItem) be.widget;
                Info.show("Close", "Closing {0}", item.getText());
            }
        });
        tabFolder.add(item);

        ButtonBar buttonBar = new ButtonBar(Style.LEFT);
        buttonBar.add(new Button("Add TabItem", new SelectionListener() {

            public void widgetSelected(BaseEvent be) {
                TabItem item = new TabItem(Style.CLOSE);
                item.setText("New Item " + count++);
                tabFolder.add(item);
            }

        }));
        container.setScrollEnabled(false);
        RowLayout layout = new RowLayout(Style.VERTICAL);
        layout.margin = 6;
        container.setLayout(layout);

        container.add(tabFolder, new RowData(RowData.FILL_BOTH));
        container.add(buttonBar);
    }
}
