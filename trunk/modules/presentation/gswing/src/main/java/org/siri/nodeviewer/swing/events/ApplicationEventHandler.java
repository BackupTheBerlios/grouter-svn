package org.siri.nodeviewer.swing.events;


import org.apache.log4j.Logger;

import java.util.Vector;

/**
 * Clients wanting to receive application events must register with this class.
 *
 * @author Georges Polyzois
 */

public class ApplicationEventHandler
{
    private static Logger logger = Logger.getLogger(ApplicationEventHandler.class);
    private static ApplicationEventHandler myInstance;
    private transient Vector applicationStateEventListeners;

    private ApplicationEventHandler()
    {
    }

    public static ApplicationEventHandler getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new ApplicationEventHandler();
        }
        return myInstance;
    }

    /**
     * Add a listener for events fired from this ApplicationEventHandler.
     *
     * @param listener
     */
    public synchronized void addApplicationStateEventListener(ApplicationStateEventListener listener)
    {
        logger.info("Added new listener : " + listener.getListenerName());
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
     * @param listener
     */
    public synchronized void removeApplicationStateEventListener(ApplicationStateEventListener listener)
    {
        logger.info("Removed new listener :" + listener.getListenerName());
        if (applicationStateEventListeners != null && applicationStateEventListeners.contains(listener))
        {
            Vector v = (Vector) applicationStateEventListeners.clone();
            v.removeElement(listener);
            applicationStateEventListeners = v;
        }
    }

    public void fireDataChanged(ApplicationStateEvent e)
    {
        logger.info("Firing new event :" + e.getMetaInfo());
        if (applicationStateEventListeners != null)
        {
            Vector listeners = applicationStateEventListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++)
            {
                ((ApplicationStateEventListener) listeners.elementAt(i)).dataChanged(e);
            }
        }
    }
}
