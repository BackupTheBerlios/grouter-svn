package org.siri.nodeviewer.swing.panels.messageview.treetableview;

import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.panels.messageview.MessageItem;

import javax.swing.*;

/**
 * A MessageNodeItem contaisn information for
 *
 * @author Georges Polyzois
 */
public class MessageNodeItem
{
    private static Logger logger = Logger.getLogger(MessageNodeItem.class);

    public MessageItem getMessageItem()
    {
        return messageItem;
    }

    public void setMessageItem(MessageItem messageItem)
    {
        this.messageItem = messageItem;
    }

    private MessageItem messageItem;
    private Icon icon;

    public MessageNodeItem(MessageItem messageItem, Icon icon)
    {
        this.messageItem = messageItem;
        this.icon = icon;
        //new MessageNodeItem(messageItem.getNodeId(),messageItem.getServiceId(),messageItem.getContent(),
        //      messageItem.getMessageId(),icon);
    }


    public Icon getIcon()
    {
        return icon;
    }

    public void setIcon(Icon icon)
    {
        this.icon = icon;
    }


}
