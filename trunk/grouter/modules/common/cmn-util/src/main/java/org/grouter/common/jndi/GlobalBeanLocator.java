/**
 * GlobalBeanLocator.java
 */
package org.grouter.common.jndi;

import org.springframework.beans.factory.BeanFactory;

/**
 * Holds the beanfactory for global use.
 *
 * @author Georges Polyzois
 * @version
 */
public class GlobalBeanLocator
{
    /** The beanfactory. */
    private static BeanFactory beanFactory;

    /**
     * After initialization
     * @param beanFactory
     */
    public static void setBeanFactory(BeanFactory beanFactory)
    {
        if(beanFactory == null)
        {
            throw new IllegalArgumentException("Beanfactory was null");
        }
        GlobalBeanLocator.beanFactory = beanFactory;
    }

    public static BeanFactory getBeanBeanFactory()
    {
        if (beanFactory == null)
        {
            throw new IllegalStateException("Beanfactory has not been set");
        }
        return beanFactory;
    }
}
