package org.grouter.ws.grouterservice;

import org.grouter.ws.grouterservice.DomainException;
import org.grouter.ws.grouterservice.GRouterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Implementation of the GRouter service.
 *
 * @author Georges Polyzois
 * @version
 *
 */
public class GRouterServiceImpl implements GRouterService
{
    private static Log log = LogFactory.getLog(GRouterService.class);


    public String getServices() throws ServicesException, DomainException
    {
        log.debug("In GRouterServiceImpl");
        double random = Math.random();
        
        if(random < 0.5)
        {
            throw new ServicesException("Random was under 0.5 - bad luck", "ERRORCODE_1");
        }
        else
        {
            throw new DomainException("Random was above 0.5 - bad luck", "ERRORCODE_2");
        }
    }

    public String getServicesNoException(String in)
    {
        log.debug("In getServicesNoException");

        return "donald";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
