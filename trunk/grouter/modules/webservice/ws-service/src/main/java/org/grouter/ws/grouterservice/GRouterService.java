package org.grouter.ws.grouterservice;

import org.grouter.ws.grouterservice.DomainException;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2005-dec-28
 * Time: 13:10:16
 * To change this template use File | Settings | File Templates.
 */
public interface GRouterService
{
    public String getServices() throws ServicesException, DomainException;
    public String getServicesNoException(String in);
}
