package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.servicelayer.GRouter;
import org.grouter.domain.servicelayer.jms.GRouterPublishEventDTO;

import javax.ejb.Remote;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 25, 2006
 * Time: 5:39:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Remote
public interface GRouterRemote extends GRouter
{

    /**
     * Persist into domain and broadcast to clients on a topic to inform of new event.
     * @param dto
     */
    void persistMessageAndBroadcastEvent(GRouterPublishEventDTO dto);
}
