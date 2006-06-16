package org.siri.nodeviewer.swing.events;


import org.apache.log4j.Logger;


public class ApplicationStateEventDispatcher implements ApplicationStateEventListener
{
    private static Logger logger = Logger.getLogger(ApplicationStateEventDispatcher.class);

    public ApplicationStateEventDispatcher()
    {
        ApplicationEventHandler.getInstance().addApplicationStateEventListener(this);
    }

    /**
     * Handles all operations
     *
     * @param e
     */
    public void dataChanged(ApplicationStateEvent e)
    {

    }


}

