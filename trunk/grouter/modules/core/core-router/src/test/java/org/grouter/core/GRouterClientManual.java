package org.grouter.core;

import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Router;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Georges Polyzois
 */
public class GRouterClientManual
{
    private static Logger logger = Logger.getLogger(GRouterClientManual.class);
    RouterService routerService;
    ApplicationContext ctx;


    public GRouterClientManual()
    {
        ctx = new FileSystemXmlApplicationContext(getConfigLocations());
    }

    public void testCallGrouterUsingRMI()
    {
        routerService = (RouterService) ctx.getBean("routerServiceClient");

        List<Router> routers = routerService.findAll();

        logger.debug("Number  of routers" + routers.size());

    }

    public static void main(String[] args)
    {
        GRouterClientManual client = new GRouterClientManual();
        client.testCallGrouterUsingRMI();
    }


    protected String[] getConfigLocations()
    {
        return new String[]
                {
                   //     "classpath:/context-router.xml", "classpath:/context-domain-datasource.xml", "classpath:/context-domain-dao.xml",
                     //   "classpath:/context-domain-sessionfactory.xml", "classpath:/context-domain-service.xml",
                        "classpath:/context-domain-service-rmi-client.xml"
                };
    }

    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }
}
