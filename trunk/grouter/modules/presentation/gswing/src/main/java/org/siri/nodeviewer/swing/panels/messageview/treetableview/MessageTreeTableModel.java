package org.siri.nodeviewer.swing.panels.messageview.treetableview;

import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.StyleModel;
import com.jidesoft.grid.TreeTableModel;
import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.events.ApplicationEventHandler;
import org.siri.nodeviewer.swing.events.ApplicationEventType;
import org.siri.nodeviewer.swing.events.ApplicationStateEvent;
import org.siri.nodeviewer.swing.events.ApplicationStateEventListener;
import org.siri.nodeviewer.swing.panels.messageview.MessageItem;

import java.awt.*;
import java.util.List;

public class MessageTreeTableModel extends TreeTableModel implements StyleModel, ApplicationStateEventListener
{
    private static Logger logger = Logger.getLogger(MessageTreeTableModel.class);
    final static Color BACKGROUND = new Color(247, 247, 247);
    final static CellStyle CELL_STYLE = new CellStyle();
    static final protected String[] COLUMN_NAMES = {"Node", "Service", "Received #", "Sent #"};
    protected int treeDepth = 5;

    static
    {
        CELL_STYLE.setBackground(BACKGROUND);
    }

    public MessageTreeTableModel(List rows)
    {
        super(rows);
        //Register to receive notifications on events
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
    }

    public String getColumnName(int column)
    {
        return COLUMN_NAMES[column];
    }

    public int getColumnCount()
    {
        return COLUMN_NAMES.length;
    }

    public CellStyle getCellStyleAt(int rowIndex, int columnIndex)
    {
        if (columnIndex == 0)
        {
            return CELL_STYLE;
        } else
        {
            return null;
        }
    }

    public boolean isCellStyleOn()
    {
        return true;
    }


    public void addNewItem(MessageItem newItem)
    {
        //super.addRow(newItem);
        //messageItems.add(newItem);
        //isDirty = true;
        fireTableDataChanged();
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
