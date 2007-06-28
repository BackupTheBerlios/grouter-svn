package org.grouter.domain.servicelayer;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.grouter.domain.servicelayer.spring.logging.LogStrategy;

/**
 * @author Georges Polyzois
 */
public class ServiceFactory implements ApplicationContextAware
{
    public final static String JDBCLOGSTRATEGY_BEAN = "jDBCLogStrategy";
    
    protected static ApplicationContext applicationContext;

    public void setApplicationContext( ApplicationContext applicationContext )
            throws BeansException
    {
        this.applicationContext = applicationContext;
    }


    public static RouterService getRouterService()
    {
        return (RouterService) applicationContext.getBean( RouterService.BEANNAME );
    }


    public static LogStrategy getLogStrategy( String name )
    {
        return (LogStrategy) applicationContext.getBean( name );
    }
}



