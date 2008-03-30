package org.siri.nodeviewer.swing.events;

import java.util.EventListener;


/**
 * Interface implemented by all classes wanting to act upon application state
 * events like EXIT etc. A typical use case would be a panel registering for events
 * and implementing this interface to receive the ApplicationStateEvents.
 *
 * E.g. Client panel register for events and implements ApplicationStateEventListener
 * interface:
 * ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
 *
 * @author Georges Polyzois
 * @version 1.0
 */


public interface ApplicationStateEventListener
        extends EventListener
{
    public void dataChanged(ApplicationStateEvent e);
    public String getListenerName();
}
