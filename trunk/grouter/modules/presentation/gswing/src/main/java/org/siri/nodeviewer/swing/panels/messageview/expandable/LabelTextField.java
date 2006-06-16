package org.siri.nodeviewer.swing.panels.messageview.expandable;

import javax.swing.*;
import java.awt.*;

/**
 * @author Georges Polyzois
 */
class LabelTextField extends JPanel
{
    JTextField _textField;
    JLabel _label;

    public LabelTextField(String label, char mnemonic, int width)
    {
        _label = new JLabel(label);
        _textField = new JTextField(width);
        _label.setLabelFor(_textField);
        _label.setDisplayedMnemonic(mnemonic);
        setLayout(new BorderLayout(2, 2));
        add(_label, BorderLayout.BEFORE_FIRST_LINE);
        add(_textField, BorderLayout.CENTER);
    }
}
