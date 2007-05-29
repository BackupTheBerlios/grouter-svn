package org.grouter.core.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.grouter.core.GrouterServer;
import org.grouter.core.GrouterServerImpl;

import java.rmi.RemoteException;

/**
 * A {@link RmiServiceExporter} configuration is static - so we have this delegator which implment a factory
 * interface in spring to be able to init ports before calling perpare() on RmiServiceExporter.
 *
 * Another option would be to extend RmiServiceExporter and override afterPropertiesSet so that prepare is not
 * called.
 *
 * @author Georges Polyzois
 */
public class RmiServiceExporterFactoryBean implements FactoryBean
{
    GrouterServerImpl grouterServer;
    RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
    private static final String GROUTER_SERVICE = "GrouterService";


    public Object getObject() throws Exception
    {
        rmiServiceExporter.setServiceName(GROUTER_SERVICE);
        rmiServiceExporter.setServiceInterface(GrouterServer.class);
        rmiServiceExporter.setService(grouterServer);
        return rmiServiceExporter;
    }

    public Class getObjectType()
    {
        return RmiServiceExporter.class;
    }

    public boolean isSingleton()
    {
        return true;
    }

    /**
     * Injected.
     * @param grouterServer
     */
    public void setGrouterServer(GrouterServerImpl grouterServer)
    {
        this.grouterServer = grouterServer;
    }



}
