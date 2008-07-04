package org.grouter.presentation.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GWTRemoteServiceAsync
{
    void getMessages(Integer nodeId, AsyncCallback async);
}
