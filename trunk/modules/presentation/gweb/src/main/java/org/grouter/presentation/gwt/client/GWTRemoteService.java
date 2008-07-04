package org.grouter.presentation.gwt.client;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.core.client.GWT;


public interface GWTRemoteService extends RemoteService
{
    public String getMessages( Integer nodeId );

    /**
     * Utility/Convenience class.
     * Use GWTRemoteService.App.getInstance() to access static instance of GwebRemoteServiceAsync
     */
    public static class App
    {
        private static GWTRemoteServiceAsync ourInstance = null;

        public static synchronized GWTRemoteServiceAsync getInstance()
        {
            if (ourInstance == null)
            {
                ourInstance = (GWTRemoteServiceAsync) GWT.create(GWTRemoteService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL()
                        + "org.grouter.presentation.gwt.server.GWTRemoteService");
            }
            return ourInstance;
        }
    }
}
