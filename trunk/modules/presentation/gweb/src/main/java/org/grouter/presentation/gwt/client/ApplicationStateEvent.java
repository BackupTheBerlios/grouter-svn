package org.grouter.presentation.gwt.client;

import java.util.EventObject;

/**
 * Application events are decorators for source events.
 *
 * @author Georges Polyzois
 */
public class ApplicationStateEvent extends EventObject
{
    //private ApplicationEventType applicationEventType;
    private String metaInfo;

    public ApplicationStateEvent(Object source, String metaInfo)
    {
        super(source);
        this.metaInfo = metaInfo;
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
}

