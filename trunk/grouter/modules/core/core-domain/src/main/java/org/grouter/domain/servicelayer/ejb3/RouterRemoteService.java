package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.servicelayer.RouterService;

import javax.ejb.Remote;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 25, 2006
 * Time: 5:39:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface RouterRemoteService extends RouterService
{
    //Deployed in ear with name daomin -> prepend ear name to jndi name
     public static final String DOMAIN_GROUTER_BEAN_REMOTE = "domain/GRouterBeanService/remote";


    /**
     * Persist into domain and broadcast to clients on a topic to inform of new event.
     * @param message
     */
    //void persistMessageAndBroadcastEvent(Message message);
}