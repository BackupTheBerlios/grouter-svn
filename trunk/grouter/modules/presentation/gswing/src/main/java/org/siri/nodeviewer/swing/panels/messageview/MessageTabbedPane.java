package org.siri.nodeviewer.swing.panels.messageview;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentPane;
import com.jidesoft.swing.JideTabbedPane;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.panels.messageview.expandable.ExpandableMessageTablePanel;
import org.siri.nodeviewer.swing.panels.messageview.treetableview.MessageNodeExplorerPanel;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Georges Polyzois
 */
public class MessageTabbedPane
{
    private static Logger logger = Logger.getLogger(MessageTabbedPane.class);
    private SimpleMessageTablePanel messageTablePanel = new SimpleMessageTablePanel();
    private ExpandableMessageTablePanel tradingMessageTablePanel = new ExpandableMessageTablePanel();
    private FilterMessageTablePanel filterMessageTablePanel = new FilterMessageTablePanel();
    private MessageNodeExplorerPanel messageNodeExplorerPanel = new MessageNodeExplorerPanel();
    DocumentPane documentPane = new DocumentPane();
    List list = new ArrayList();

    public MessageTabbedPane()
    {
        init();
    }

    private void init()
    {
        DocumentComponent dcMessage = new DocumentComponent((JComponent) messageTablePanel.getPanel(), "Table View");
        DocumentComponent dcExpandable = new DocumentComponent((JComponent) tradingMessageTablePanel.getPanel(), "Expand View");
        DocumentComponent dcFilter = new DocumentComponent((JComponent) filterMessageTablePanel.getPanel(), "Filter View");
        DocumentComponent dcMessageNode = new DocumentComponent((JComponent) messageNodeExplorerPanel.getPanel(), "Tree Table");

        list.add(dcMessage);
        list.add(dcExpandable);
        list.add(dcFilter);
        list.add(dcMessageNode);

        documentPane.setOpenedDocuments(list);
        documentPane.getLayoutPersistence().setProfileKey("documents");
        documentPane.setUpdateTitle(true);
        documentPane.getLayoutPersistence().loadLayoutData();
    }


    public DocumentPane getTabbedPane()
    {
        return documentPane;
    }
}
