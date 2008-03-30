package org.grouter.presentation.gwt.client;

import java.util.Vector;

/**
 *
 *
 * @author Georges Polyzois
 */
public class ApplicationEventHandler
{
    private static ApplicationEventHandler instance;
    private transient Vector applicationStateEventListeners;

    private ApplicationEventHandler()
    {
    }

    public static ApplicationEventHandler getInstance()
    {
        if (instance == null) {
            instance = new ApplicationEventHandler();
        }
        return instance;
    }

    /**
     * Add a listener for events fired from this ApplicationEventHandler.
     *
     * @param listener
     */
    public synchronized void addApplicationStateEventListener(ApplicationStateEventListener listener)
    {
        Vector v = applicationStateEventListeners == null ? new Vector(2) :
                (Vector) applicationStateEventListeners.clone();
        if (!v.contains(listener))
        {
            v.addElement(listener);
            applicationStateEventListeners = v;
        }
    }

    /**
     * Remove a listener
     *
     * @param listener
     */
    public synchronized void removeApplicationStateEventListener(ApplicationStateEventListener listener)
    {
        //logger.info("Removed new listener :" + listener.getListenerName());
        if (applicationStateEventListeners != null && applicationStateEventListeners.contains(listener)) {
            Vector v = (Vector) applicationStateEventListeners.clone();
            v.removeElement(listener);
            applicationStateEventListeners = v;
        }
    }

    /**
     * Fire event to listeners.
     *
     * @param applicationStateEvent
     */
    public void fireDataChanged(ApplicationStateEvent applicationStateEvent)
    {
        //logger.info("Firing new event :" + applicationStateEvent.getMetaInfo());
        if (applicationStateEventListeners != null) {
            Vector listeners = applicationStateEventListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ApplicationStateEventListener) listeners.elementAt(i)).dataChanged(applicationStateEvent);
            }
        }
    }
}
