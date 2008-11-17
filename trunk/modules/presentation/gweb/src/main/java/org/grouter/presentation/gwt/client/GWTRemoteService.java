package org.grouter.presentation.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


public interface GWTRemoteService extends RemoteService
{
    public String getMessages( Integer nodeId );

//    public Router getRouters( );

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

                // we're running at http://localhost:8080/gweb/org.grouter.presentation.gwt.MainApp/MainApp.html
                // but the service is at http://localhost:8080/gweb/router.rpc


                //((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL()+ "org.grouter.presentation.gwt.server.GWTRemoteService");
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL()+
                        "../router.rpc");
            }
            return ourInstance;
        }
    }
}
