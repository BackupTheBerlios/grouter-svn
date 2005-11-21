package org.siri.nodeviewer.swing.panels.messageview;

import com.jidesoft.grid.HierarchicalTableModel;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.events.ApplicationEventHandler;
import org.siri.nodeviewer.swing.events.ApplicationEventType;
import org.siri.nodeviewer.swing.events.ApplicationStateEvent;
import org.siri.nodeviewer.swing.events.ApplicationStateEventListener;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * MessageTableModel is used in the Main View area displaying what
 * actually goes on in the service nodes.
 *
 * @author Georges Polyzois
 */
public class MessageTableModel extends AbstractTableModel implements HierarchicalTableModel, ApplicationStateEventListener
{
    private static Logger logger = Logger.getLogger(MessageTableModel.class);
    private List<MessageItem> messageItems = new ArrayList<MessageItem>();
    private static MessageTableModel messageTableModel;
    private final static String[] COLUMNS = new String[]{"Id", "Content", "Last", "Change"};

    public static MessageTableModel getInstance()
    {
        if (messageTableModel == null)
        {
            messageTableModel = new MessageTableModel();
        }
        return messageTableModel;
    }

    private MessageTableModel()
    {
        //Register to receive notifications on events
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
    }

    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public Object getValueAt(int row, int col)
    {
        MessageItem item = (MessageItem) messageItems.get(row);
        switch (col)
        {
            case 0:
                return item.getServiceId();
            case 1:
                return item.getMessageId();
            case 2:
                return item.getContent();
            case 3:
                return item.isSent();
        }
        return null;
    }

    public boolean hasChild(int row)
    {
        return true;
    }

    public boolean isExpandable(int row)
    {
        return true;
    }

    public boolean isHierarchical(int row)
    {
        return true;
    }

    public Object getChildValueAt(int row)
    {
        return null;
    }

    public int getRowCount()
    {
        return messageItems.size();
    }

    public int getColumnCount()
    {
        return COLUMNS.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addNewItem(MessageItem newItem)
    {
        int sizeBeforeAdd = messageItems.size();
        messageItems.add(newItem);
        fireTableRowsInserted(sizeBeforeAdd, messageItems.size());
        // fireTableDataChanged() - this removes current selection if any 
    }

    public String getColumnName(int column)
    {
        if (column > (COLUMNS.length - 1))
        {
            String text = "You are trying to get a column which does not exist. This" +
                    " model only has " + COLUMNS.length + " number of columns";
            throw new IllegalArgumentException(text);
        }
        return COLUMNS[column];
    }

    /**
     * Updates of model are handled here, since we already registered as receivers.
     *
     * @param e
     */
    public void dataChanged(ApplicationStateEvent e)
    {
        if (e.getApplicationEventType() == ApplicationEventType.UPDATEMESSAGEMODEL)
        {
            MessageItem messageItem = (MessageItem) e.getSource();
            addNewItem(messageItem);
        }
    }
}
