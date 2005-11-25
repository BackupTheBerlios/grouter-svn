/**
 * GlobalBeanLocator.java
 */
package org.grouter.common.jndi;

import org.springframework.beans.factory.BeanFactory;

/**
 * Holds the beanfactory and can be used to locate beans (Spring).
 *
 * @author Georges Polyzois
 * @version
 */
public class GlobalBeanLocator
{
    /** The context. */
    private static BeanFactory beanFactory;

    public static void setBeanFactory(BeanFactory beanFactory)
    {
        GlobalBeanLocator.beanFactory = beanFactory;
    }

    /**
     * Wraps the
     * @param name
     * @return
     */
    public static Object getBean(String name)
    {
        if (beanFactory == null)
        {
            throw new IllegalStateException("Beanfactory has not been set");
        }
        return beanFactory.getBean(name);
    }
}
