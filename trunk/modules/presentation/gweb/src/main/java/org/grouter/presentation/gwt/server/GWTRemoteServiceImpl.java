package org.grouter.presentation.gwt.server;

import org.grouter.domain.entities.Message;
import org.grouter.presentation.gwt.client.GWTRemoteService;
import org.gwtwidgets.server.spring.GWTSpringController;

import java.util.List;
import java.util.ArrayList;


/**
 * Server side implementation.
 *
 * @author Georges Polyzois
 */
//public class GWTRemoteServiceImpl     extends RemoteServiceServlet implements GWTRemoteService
public class GWTRemoteServiceImpl extends GWTSpringController implements GWTRemoteService
{
    public String getMessages(Integer nodeId)
    {
        List<Message> messages = new ArrayList<Message>();

        Message entity = new Message("A messae");
        Message entity2 = new Message("A messae");

        messages.add( entity );
        messages.add( entity2 );

        return "messages";
    }

    public void setRouterService(String routerService)
    {
    }
}