/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.grouter.common.jndi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Hashtable;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import javax.naming.NamingException;
import javax.naming.Context;
import org.apache.log4j.Logger;

/**
 * Class for accessing ejbs, datasources and queues/topics. Provides caching of
 * looked up references to resouces for faster access without remote JNDI lookups
 * every time.
 *
 * Keys in cache will use jndiname, context hashcode and provider url (i.e. a distinction
 * between 127.0.0.1 and localhost will not be made).
 *
 * @author
 * @version
 */
public class ServiceLocatorContextAware
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(ServiceLocatorContextAware.class);
    /** Map with cached lookups. */
    private Map cache;
    /** Singelton instanceUnderTest. */
    private static ServiceLocatorContextAware instance;

    /**
     * Private constructor.
     *
     * @throws ServiceLocatorException
     */
    private ServiceLocatorContextAware() throws ServiceLocatorException
    {
        try
        {
            cache = Collections.synchronizedMap(new HashMap());
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * Produce a unique key for this jndi name and context.
     * @param jndiName String
     * @param context Context
     * @return String
     * @throws NamingException
     */
    private synchronized String getKey(String jndiName, Context context) throws
            NamingException
    {
        if (context == null) throw new IllegalArgumentException("Null context provided");

        StringBuilder builder = new StringBuilder();
        String providerurl = "nourl";
        int hashcode;
        Hashtable hashtable = context.getEnvironment();
        providerurl = (String)hashtable.get(Context.PROVIDER_URL);
        hashcode = context.hashCode();
        builder.append(providerurl).append("_");
        builder.append(jndiName).append("_");
        builder.append(hashcode);
        String key = builder.toString();
        return key;
    }

    /**
     * Access the ServiceLocator as a singelton
     *
     * @throws ServiceLocatorException
     * @return ServiceLocator
     */
    public static ServiceLocatorContextAware getInstance() throws ServiceLocatorException
    {
        if (instance == null)
        {
            instance = new ServiceLocatorContextAware();
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
    public EJBLocalHome getLocalHome(String jndiName, Context context) throws
            ServiceLocatorException
    {
        EJBLocalHome result = null;
        try
        {
            String key = getKey(jndiName, context);
            if (cache.containsKey(key))
            {
                result = (EJBLocalHome) cache.get(key);
            } else
            {
                Object object = context.lookup(jndiName);
                result = (EJBLocalHome) object;
                cache.put(key, result);
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
    public EJBHome getHome(String jndiName, Context context) throws ServiceLocatorException
    {
        EJBHome result = null;
        try
        {
            String key = getKey(jndiName, context);
            if (cache.containsKey(key))
            {
                result = (EJBHome) cache.get(key);
            } else
            {
                Object ref = context.lookup(jndiName);
                result = (EJBHome) PortableRemoteObject.narrow(ref, EJBHome.class);
                cache.put(key, result);
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
    public DataSource getDataSource(String jndiName, Context context) throws
            ServiceLocatorException
    {
        DataSource result = null;
        try
        {
            String key = getKey(jndiName, context);
            if (cache.containsKey(key))
            {
                result = (DataSource) cache.get(key);
            } else
            {
                result = (DataSource) context.lookup(jndiName);
                cache.put(key, result);
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
    public TopicConnectionFactory getTopicConnectionFactory(String jndiName, Context context) throws
            ServiceLocatorException
    {
        TopicConnectionFactory result = null;
        try
        {
            String key = getKey(jndiName, context);
            if (cache.containsKey(key))
            {
                result = (TopicConnectionFactory) cache.get(key);
            } else
            {
                result = (TopicConnectionFactory) context.lookup(
                        jndiName);
                cache.put(key, result);
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
    public QueueConnectionFactory getQueueConnectionFactory(String jndiName, Context context) throws
            ServiceLocatorException
    {
        QueueConnectionFactory result = null;
        try
        {
            String key = getKey(jndiName, context);
            if (cache.containsKey(key))
            {
                result = (QueueConnectionFactory) cache.get(key);
            } else
            {
                result = (QueueConnectionFactory) context.lookup(
                        jndiName);
                cache.put(key, result);
            }
        }
        catch (NamingException e)
        {
            throw new ServiceLocatorException(e);
        }
        catch (Exception e)
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
    public Queue getQueue(String jndiName, Context context) throws ServiceLocatorException
    {
        Queue result = null;
        try
        {
            String key = getKey(jndiName, context);
            if (cache.containsKey(key))
            {
                result = (Queue) cache.get(key);
            } else
            {
                result = (Queue) context.lookup(jndiName);
                cache.put(key, result);
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
    public Topic getTopic(String jndiName, Context context) throws ServiceLocatorException
    {
        Topic result = null;
        try
        {
            String key = jndiName+context;
            if (cache.containsKey(key))
            {
                result = (Topic) cache.get(key);
            } else
            {
                result = (Topic) context.lookup(jndiName);
                cache.put(key, result);
            }
        } catch (Exception e)
        {
            throw new ServiceLocatorException(e);
        }
        return result;
    }





    /**
     * Return size of the current cache.
     * @return int
     */
    public int getCacheSize()
    {
        return cache.size();
    }

}
