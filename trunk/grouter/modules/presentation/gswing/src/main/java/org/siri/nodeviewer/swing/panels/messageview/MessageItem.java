package org.siri.nodeviewer.swing.panels.messageview;

import org.apache.log4j.Logger;

/**
 * @author Georges Polyzois
 */
public class MessageItem
{
    private static Logger logger = Logger.getLogger(MessageItem.class);
    private String messageId;
    private String content;
    private String serviceId;
    private String nodeId;
    private boolean isSent;

    public MessageItem(final String nodeId, final String messageId, final String content,
                       final String serviceNodeId, final boolean sent)
    {
        this.messageId = messageId;
        this.content = content;
        this.serviceId = serviceNodeId;
        this.isSent = sent;
        this.nodeId = nodeId;
    }

    public static Logger getLogger()
    {
        return logger;
    }

    public static void setLogger(Logger logger)
    {
        MessageItem.logger = logger;
    }

    public String getMessageId()
    {
        return messageId;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }

    public boolean isSent()
    {
        return isSent;
    }

    public void setSent(boolean sent)
    {
        isSent = sent;
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MessageItem that = (MessageItem) o;

        if (isSent != that.isSent) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;
        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;

        return true;
    }

    public int hashCode()
    {
        int result;
        result = (messageId != null ? messageId.hashCode() : 0);
        result = 29 * result + (content != null ? content.hashCode() : 0);
        result = 29 * result + (serviceId != null ? serviceId.hashCode() : 0);
        result = 29 * result + (nodeId != null ? nodeId.hashCode() : 0);
        result = 29 * result + (isSent ? 1 : 0);
        return result;
    }

}
