package org.siri.nodeviewer.swing.events;


import java.util.Vector;

/**
 * Clients wanting to receive application events must register with this class.
 *
 * @author Georges Polyzois
 */

public class ApplicationEventHandler
{
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

    public synchronized void addApplicationStateEventListener(ApplicationStateEventListener l)
    {
        Vector v = applicationStateEventListeners == null ? new Vector(2) :
                (Vector) applicationStateEventListeners.clone();
        if (!v.contains(l))
        {
            v.addElement(l);
            applicationStateEventListeners = v;
        }
    }

    /**
     * 
     * @param l
     */
    public synchronized void removeApplicationStateEventListener(ApplicationStateEventListener l)
    {
        if (applicationStateEventListeners != null && applicationStateEventListeners.contains(l))
        {
            Vector v = (Vector) applicationStateEventListeners.clone();
            v.removeElement(l);
            applicationStateEventListeners = v;
        }
    }

    public void fireDataChanged(ApplicationStateEvent e)
    {
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
