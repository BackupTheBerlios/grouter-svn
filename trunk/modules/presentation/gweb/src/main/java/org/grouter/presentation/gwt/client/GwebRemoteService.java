package org.grouter.presentation.gwt.client;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.core.client.GWT;

import java.util.List;

public interface GwebRemoteService extends RemoteService
{
    public String getMessages( Integer nodeId );

    /**
     * Utility/Convenience class.
     * Use GwebRemoteService.App.getInstance() to access static instance of GwebRemoteServiceAsync
     */
    public static class App
    {
        private static GwebRemoteServiceAsync ourInstance = null;

        public static synchronized GwebRemoteServiceAsync getInstance()
        {
            if (ourInstance == null)
            {
                ourInstance = (GwebRemoteServiceAsync) GWT.create(GwebRemoteService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL()
                        + "org.grouter.presentation.gwt.server.GwebRemoteService");
            }
            return ourInstance;
        }
    }
}
