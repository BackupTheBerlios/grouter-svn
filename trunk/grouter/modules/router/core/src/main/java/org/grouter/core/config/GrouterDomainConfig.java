package org.grouter.core.config;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 19, 2006
 * Time: 4:55:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class GrouterDomainConfig
{
    String javaNamingFactoryInitial;
    String javaNamingProviderUrl;
    String javaNamingUrlPkgs;
    String destinationJndiName;


    /**
     * Construcotr.
     *
     * @param javaNamingFactoryInitial 
     * @param javaNamingProviderUrl
     * @param javaNamingUrlPkgs
     * @param destinationJndiName
     */
    public GrouterDomainConfig(String javaNamingFactoryInitial, String javaNamingProviderUrl, String javaNamingUrlPkgs, String destinationJndiName)
    {
        this.javaNamingFactoryInitial = javaNamingFactoryInitial;
        this.javaNamingProviderUrl = javaNamingProviderUrl;
        this.javaNamingUrlPkgs = javaNamingUrlPkgs;
        this.destinationJndiName = destinationJndiName;
    }


    public String getJavaNamingFactoryInitial()
    {
        return javaNamingFactoryInitial;
    }

    public String getJavaNamingProviderUrl()
    {
        return javaNamingProviderUrl;
    }

    public String getJavaNamingUrlPkgs()
    {
        return javaNamingUrlPkgs;
    }

    public String getDestinationJndiName()
    {
        return destinationJndiName;
    }
}
