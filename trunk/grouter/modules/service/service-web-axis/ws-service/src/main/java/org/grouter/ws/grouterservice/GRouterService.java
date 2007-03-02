package org.grouter.ws.grouterservice;


/**
 * Interface defintion for this web service. We are using a java interface and use
 * Axis tools to generate a wsdl from this interface - java2wsdl.
 *
 * @author Georges Polyzois
 */
public interface GRouterService
{
    public String getServices() throws ServicesException, DomainException;
    public String getServicesNoException(String in);
}
