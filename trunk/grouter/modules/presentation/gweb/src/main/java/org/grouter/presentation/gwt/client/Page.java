package org.grouter.presentation.gwt.client;


import net.mygwt.ui.client.Events;
import net.mygwt.ui.client.Style;
import net.mygwt.ui.client.MyGWT;
import net.mygwt.ui.client.event.BaseEvent;
import net.mygwt.ui.client.event.Listener;
import net.mygwt.ui.client.widget.TabFolder;
import net.mygwt.ui.client.widget.TabItem;
import net.mygwt.ui.client.widget.WidgetContainer;
import net.mygwt.ui.client.widget.layout.FillLayout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public abstract class Page extends WidgetContainer implements EntryPoint {

  public boolean initialized = false;

  private TabFolder tabFolder;
  private TabItem contentItem, sourceItem;
  private boolean loadedSource = false;

  protected boolean hasSource;

  public void onModuleLoad() {
    WidgetContainer c = new WidgetContainer();
    createWidget(c);

    RootPanel.get().add(c);
    MyGWT.hideLoadingPanel("my-start-panel");
  }

  protected abstract void createWidget(WidgetContainer container);

  final public String getId() {
    String s = GWT.getTypeName(this);
    return s.substring(s.lastIndexOf(".") + 1, s.length());
  }

  protected void init() {
    initialized = true;
    FillLayout layout = new FillLayout();
    setLayout(layout);

    tabFolder = new TabFolder(Style.NONE);
    tabFolder.setBorders(false);
    final Page fPage = this;
    tabFolder.addListener(Events.SelectionChange, new Listener() {
      public void handleEvent(BaseEvent be) {
        TabItem item = (TabItem)be.item;
        if (item == sourceItem && !loadedSource) {
          loadedSource = true;
          String name = GWT.getTypeName(fPage);
          name = name.substring(name.lastIndexOf(".") + 1);
          name = "code/" + name + ".html";
          item.setURL(name);
        }
      }

    });
    add(tabFolder);

    contentItem = new TabItem(Style.NONE);
    contentItem.setText("View");

    createWidget(contentItem.getContainer());

    tabFolder.add(contentItem);

    sourceItem = new TabItem(Style.NONE);
    sourceItem.setText("Source");
    tabFolder.add(sourceItem);

    tabFolder.setSelection(contentItem);

  }

}
