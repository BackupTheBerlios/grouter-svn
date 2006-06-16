package org.siri.nodeviewer.swing.panels.messageview.expandable;

import com.jidesoft.combobox.ListComboBox;

import javax.swing.*;
import java.awt.*;

/**
 * @author Georges Polyzois
 */
class LabelCombobox extends JPanel
{
    ListComboBox _comboxBox;
    JLabel _label;

    public LabelCombobox(String label, char mnemonic, Object[] values)
    {
        _label = new JLabel(label);
        _comboxBox = new ListComboBox(values);
        _comboxBox.setSelectedItem(values[0]);
        _label.setLabelFor(_comboxBox);
        _label.setDisplayedMnemonic(mnemonic);
        setLayout(new BorderLayout(2, 2));
        add(_label, BorderLayout.BEFORE_FIRST_LINE);
        add(_comboxBox, BorderLayout.CENTER);
    }
}
