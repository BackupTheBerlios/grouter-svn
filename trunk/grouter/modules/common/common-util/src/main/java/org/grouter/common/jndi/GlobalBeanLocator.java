/**
 * GlobalBeanLocator.java
 */
package org.grouter.common.jndi;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.log4j.Logger;

/**
 * Holds the beanfactory for global use.
 *
 * @author Georges Polyzois
 */
public class  GlobalBeanLocator
{
    private static Logger logger = Logger.getLogger(GlobalBeanLocator.class);
    private static ApplicationContext context;
    private static final String APPLICATION_CONTEXT_XML = "applicationContext.xml";
    private static GlobalBeanLocator globalBeanLocator;

    public static GlobalBeanLocator getInstance()
    {
        if(globalBeanLocator == null)
        {
            globalBeanLocator = new GlobalBeanLocator();
        }
        return globalBeanLocator;

    }

    // do not return beanfactory
    public  ApplicationContext getApplicationContext()
    {
        if (context == null)
        {
            throw new IllegalStateException("Beanfactory has not been set, context from " + APPLICATION_CONTEXT_XML + " not set");
        }
        return context;//.getParentBeanFactory();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        logger.info("Setting application context");
        context = applicationContext;
    }


    protected String[] getConfigLocations()
    {
        return new String[]{
                "ac-dao.xml",
                "ac-datasource.xml",
                "ac-service.xml",
                "ac-sessionfactory.xml"

        };
    }
}
