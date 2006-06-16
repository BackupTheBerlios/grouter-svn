package org.siri.nodeviewer.swing.panels.serviceexplorer;

import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.StyleModel;
import com.jidesoft.grid.TreeTableModel;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.List;

public class ServiceTreeTableModel extends TreeTableModel implements StyleModel
{
    private static Logger logger = Logger.getLogger(ServiceTreeTableModel.class);
    final static Color BACKGROUND = new Color(247, 247, 247);
    final static CellStyle CELL_STYLE = new CellStyle();
    static final protected String[] COLUMN_NAMES = {"Name", "Sent #", "Received #", "Last"};
    protected int treeDepth = 5;

    public ServiceTreeTableModel(List rows)
    {
        super(rows);
    }

    public String getColumnName(int column)
    {
        return COLUMN_NAMES[column];
    }

    public int getColumnCount()
    {
        return COLUMN_NAMES.length;
    }


    static
    {
        CELL_STYLE.setBackground(BACKGROUND);
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
}
