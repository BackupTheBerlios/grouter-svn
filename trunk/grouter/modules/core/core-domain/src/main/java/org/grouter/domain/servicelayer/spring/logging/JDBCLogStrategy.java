package org.grouter.domain.servicelayer.spring.logging;

import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Message;

/**
 * @author Georges Polyzois
 */
public class JDBCLogStrategy  implements LogStrategy
{
    RouterService routerService;


    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }

    public void log( Message message )
    {
//        routerService.saveMessage( message );

    }
}
