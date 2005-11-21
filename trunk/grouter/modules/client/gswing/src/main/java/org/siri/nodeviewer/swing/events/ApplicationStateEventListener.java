package org.siri.nodeviewer.swing.events;

import java.util.EventListener;


/**
 * Interface implemented by all classes wanting to act upon application state
 * events like EXIT etc.
 *
 * @author Georges Polyzois
 * @version 1.0
 */


public interface ApplicationStateEventListener
        extends EventListener
{
    public void dataChanged(ApplicationStateEvent e);
}
