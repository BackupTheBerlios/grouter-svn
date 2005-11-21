package org.siri.nodeviewer.swing.events;

import java.util.EventObject;

/**
 * All events for application
 *
 * @author not attributable
 * @version 1.0
 */


public class ApplicationStateEvent
        extends EventObject
{

    ApplicationEventType applicationEventType;
    private String metaInfo;

    public ApplicationStateEvent(Object source, String metaInfo, ApplicationEventType applicationEventType)
    {
        super(source);
        this.metaInfo = metaInfo;
        this.applicationEventType = applicationEventType;
    }

    public void setText(String toolbarValue)
    {
        this.metaInfo = toolbarValue;
    }

    public String getText()
    {
        return metaInfo;
    }

    public ApplicationEventType getApplicationEventType()
    {
        return applicationEventType;
    }

    public void setApplicationEventType(ApplicationEventType applicationEventType)
    {
        this.applicationEventType = applicationEventType;
    }

}
