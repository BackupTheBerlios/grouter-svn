package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.servicelayer.GRouter;
import org.grouter.domain.servicelayer.jms.GRouterPublishEventDTO;

import javax.ejb.Local;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 25, 2006
 * Time: 5:37:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface GRouterLocal  extends GRouter
{
    public static final String DOMAIN_GROUTER_BEAN_LOCAL = "domain/GRouterBean/local";
    
    /**
     * Persist into domain and broadcast to clients on a topic to inform of new event.
     * @param dto
     */
    void persistMessageAndBroadcastEvent(GRouterPublishEventDTO dto);
}
