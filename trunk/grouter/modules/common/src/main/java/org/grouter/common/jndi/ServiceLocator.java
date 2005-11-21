/**
 * ServiceLocator.java
 */
package org.grouter.common.jndi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

/**
 * Class for accessing ejbs, datasources and queues/topics. Provides caching of
 * lookuped up references to resouces for faster access without JNDI lookups.
 *
 * @author
 * @version
 */
public class ServiceLocator
{
    /** Map with cached lookups. */
    private Map cache;
    /** The context. */
    private InitialContext initialContext;
    /** Singelton instance. */
    private static ServiceLocator instance;

    /**
     * Private constructor.
     *
     * @throws ServiceLocatorException
     */
    private ServiceLocator() throws ServiceLocatorException
    {
        try
        {
            initialContext = new InitialContext();
            cache = Collections.synchronizedMap(new HashMap());
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
    }


    /**
     * Access the ServiceLocator as a singelton
     *
     * @throws ServiceLocatorException
     * @return ServiceLocator
     */
    public static ServiceLocator getInstance() throws ServiceLocatorException
    {
        if (instance == null)
        {
            instance = new ServiceLocator();
        }
        return instance;
    }

    /**
     * Use this to access local ejbs
     *
     * @param jndiName String
     * @throws ServiceLocatorException
     * @return EJBLocalHome
     */
    public EJBLocalHome getLocalHome(String jndiName) throws
            ServiceLocatorException
    {
        EJBLocalHome result = null;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (EJBLocalHome) cache.get(jndiName);
            } else
            {
                result = (EJBLocalHome) initialContext.lookup(jndiName);
                cache.put(jndiName, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;
    }

    /**
     * Use this to access remote ejbs.
     *
     * References are cached so that repeated client requests for this factory
     * avoids JNDI lookups.
     *
     * @param jndiName String
     * @throws ServiceLocatorException
     * @return EJBHome
     */
    public EJBHome getHome(String jndiName) throws ServiceLocatorException
    {
        EJBHome result = null;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (EJBHome) cache.get(jndiName);
            } else
            {
                Object ref = initialContext.lookup(jndiName);
                result = (EJBHome) PortableRemoteObject.narrow(ref, EJBHome.class);
                cache.put(jndiName, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;
    }

    /**
     * Use this to access datasources.
     *
     * References are cached so that repeated client requests for this factory
     * avoids JNDI lookups.
     *
     * @param jndiName String
     * @throws ServiceLocatorException
     * @return DataSource
     */
    public DataSource getDataSource(String jndiName) throws
            ServiceLocatorException
    {
        DataSource result = null;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (DataSource) cache.get(jndiName);
            } else
            {
                result = (DataSource) initialContext.lookup(jndiName);
                cache.put(jndiName, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;

    }

    /**
     * Use this to access topic factories and use factory to create
     * a TopicConnection and Topic.
     *
     * References are cached so that repeated client requests for this factory
     * avoids JNDI lookups.
     *
     * @param jndiName String
     * @throws ServiceLocatorException
     * @return TopicConnectionFactory
     */
    public TopicConnectionFactory getTopicConnectionFactory(String jndiName) throws
            ServiceLocatorException
    {
        TopicConnectionFactory result = null;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (TopicConnectionFactory) cache.get(jndiName);
            } else
            {
                result = (TopicConnectionFactory) initialContext.lookup(
                        jndiName);
                cache.put(jndiName, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;
    }

    /**
     * Use this to access queue factories and use factory to create
     * a QueueConnection and Queue.
     *
     * References are cached so that repeated client requests for this factory
     * avoids JNDI lookups.
     *
     * @param jndiName String
     * @throws ServiceLocatorException
     * @return QueueConnectionFactory
     */
    public QueueConnectionFactory getQueueConnectionFactory(String jndiName) throws
            ServiceLocatorException
    {
        QueueConnectionFactory result = null;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (QueueConnectionFactory) cache.get(jndiName);
            } else
            {
                result = (QueueConnectionFactory) initialContext.lookup(
                        jndiName);
                cache.put(jndiName, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;
    }

    /**
     * Use this to access queue factories and use factory to create
     * a QueueConnection and Queue.
     *
     * References are cached so that repeated client requests for this factory
     * avoids JNDI lookups.
     *
     * @param jndiName String
     * @throws ServiceLocatorException
     * @return QueueConnectionFactory
     */
    public Queue getQueue(String jndiName) throws ServiceLocatorException
    {
        Queue result = null;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (Queue) cache.get(jndiName);
            } else
            {
                result = (Queue) initialContext.lookup(jndiName);
                cache.put(jndiName, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;
    }

    /**
     * Use this to access queue factories and use factory to create
     * a TopicConnection and Topic.
     *
     * References are cached so that repeated client requests for this factory
     * avoids JNDI lookups.
     *
     * @param jndiName String
     * @throws ServiceLocatorException
     * @return Topic
     */
    public Topic getTopic(String jndiName) throws ServiceLocatorException
    {
        Topic result = null;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (Topic) cache.get(jndiName);
            } else
            {
                result = (Topic) initialContext.lookup(jndiName);
                cache.put(jndiName, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;
    }

    /**
     * If context needed use this.
     *
     * @return InitialContext
     */
    public InitialContext getInitialContext()
    {
        return initialContext;
    }
}
