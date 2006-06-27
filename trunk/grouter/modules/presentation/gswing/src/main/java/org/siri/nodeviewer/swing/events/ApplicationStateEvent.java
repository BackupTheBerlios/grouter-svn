package org.siri.nodeviewer.swing.events;

import java.util.EventObject;

/**
 * Application events are decorateors for source events.
 *
 * @author not attributable
 * @version 1.0
 */
public class ApplicationStateEvent extends EventObject
{
    public static enum ApplicationEventType{UPDATEMESSAGEMODEL,UPDATELOGPANEL}
    private ApplicationEventType applicationEventType;
    private String metaInfo;

    public ApplicationStateEvent(Object source, String metaInfo, ApplicationEventType applicationEventType)
    {
        super(source);
        this.metaInfo = metaInfo;
        this.applicationEventType = applicationEventType;
    }

    /**
     * Metainfo holds information used to display information for this ApplicationStateEvent - used
     * by the {@link ApplicationEventHandler}.
     * @return metainfo about this event
     */
    public String getMetaInfo()
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
