package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.servicelayer.GRouterService;

import javax.ejb.Local;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 25, 2006
 * Time: 5:37:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface GRouterLocalService extends GRouterService
{
    public static final String DOMAIN_GROUTER_BEAN_LOCAL = "domain/GRouterBeanService/local";
    
    /**
     * Persist into domain and broadcast to clients on a topic to inform of new event.
     * @param message
     */          
}
