package org.grouter.presentation.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.grouter.presentation.gwt.client.GwebRemoteService;
import org.grouter.domain.entities.Message;

import java.util.List;
import java.util.ArrayList;


/**
 * Server side implementation.
 *
 * @author Georges Polyzois
 */
public class GwebRemoteServiceImpl extends RemoteServiceServlet implements GwebRemoteService 
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
}