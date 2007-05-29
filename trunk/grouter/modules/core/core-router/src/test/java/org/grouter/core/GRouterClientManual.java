package org.grouter.core;

import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Router;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.apache.log4j.Logger;

import java.util.List;

import junit.framework.TestCase;

/**
 * @author Georges Polyzois
 */
public class GRouterClientManual extends TestCase
{
    private static Logger logger = Logger.getLogger(GRouterClientManual.class);
    RouterService routerService;
    ApplicationContext ctx;


    public GRouterClientManual()
    {
        ctx = new FileSystemXmlApplicationContext(getConfigLocations());
    }

    public void testCallGrouterUsingRMI() throws Exception
    {
        ApplicationContext ctx = new FileSystemXmlApplicationContext(getConfigLocations());
        //GrouterServerAdapter grouterServerAdapter = (GrouterServerAdapter) ctx.getBean("routerServiceClient");
        GrouterServerAdapter grouterServerAdapter = (GrouterServerAdapter) ctx.getBean("grouterServerClient");
        grouterServerAdapter.stopNode("n2");
        //logger.info("Number of routers:" + routers.size());
    }



    /*public static void main(String[] args)
  {
      GRouterServerAdapter adapter = new GRouterServerAdapter();
      ApplicationContext ctx = new FileSystemXmlApplicationContext(getConfigLocations());
      GRouterServerAdapter gRouterServerAdapter = (GRouterServerAdapter) ctx.getBean("routerServiceClient");

      List<Router>  routers = gRouterServerAdapter.findAllRouters();

      logger.info( "Number of routers:" + routers.size() );

  }
    */
    protected static String[] getConfigLocations()
    {
        return new String[]
                {
                        //     "classpath:/context-router.xml", "classpath:/context-domain-datasource.xml", "classpath:/context-domain-dao.xml",
                        //   "classpath:/context-domain-sessionfactory.xml", "classpath:/context-domain-service.xml",
                        "classpath:/context-router-rmi-client.xml"
                };
    }

    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }
}
