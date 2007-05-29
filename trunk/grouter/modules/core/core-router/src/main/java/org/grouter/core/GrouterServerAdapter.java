package org.grouter.core;

import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Router;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

/**
 * @author Georges
 */
public class GRouterServerAdapter
{
    private static Logger logger = Logger.getLogger(GRouterServerAdapter.class);
    RouterService routerServiceProxy;

    public GRouterServerAdapter()
    {

    }

    public List<Router> findAllRouters()
    {
        List<Router> routers = routerServiceProxy.findAll();
        logger.debug("Number  of routers" + routers.size());
        return routers;
    }

    public void stopNode( String nodeId )
    {
        //routerServiceProxy.
        logger.debug("Stop node:" + nodeId);

    }


    /**
     * Injected.
     * @param routerServiceProxy proxy.
     */
    public void setRouterServiceProxy(RouterService routerServiceProxy)
    {
        this.routerServiceProxy = routerServiceProxy;
    }
}
