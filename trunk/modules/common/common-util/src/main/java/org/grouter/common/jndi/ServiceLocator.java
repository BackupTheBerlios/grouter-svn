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

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

/**
 * Class for accessing ejbs, datasources and queues/topics. Provides caching of
 * lookuped up references to resouces for faster access without remote JNDI lookups.
 *
 * @author  Georges Polyzois
 */
public class ServiceLocator
{
    /** Map with cached lookups. */
    private Map cache;
    /** The context. */
    private Context initialContext;
    /** Singelton instanceUnderTest. */
    private static ServiceLocator instance;

    /**
     * Private constructor.
     *
     * @throws ServiceLocatorException  if failure to create initialcontext
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
     * @return ServiceLocator
     * @throws ServiceLocatorException  if failure to create ServiceLocator
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
     * @return EJBLocalHome
     * @throws ServiceLocatorException if failure to lookup object in JNDI
     */
    @SuppressWarnings({"SameParameterValue"})
    public EJBLocalHome getLocalHome(String jndiName) throws
            ServiceLocatorException
    {
        EJBLocalHome result;
        try
        {
            if (cache.containsKey(jndiName))
            {
                result = (EJBLocalHome) cache.get(jndiName);
            }
            else
            {
                Object object = initialContext.lookup(jndiName);
                result = (EJBLocalHome) object;
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
     * @throws ServiceLocatorException if failure to lookup object in JNDI
     * @return EJBHome
     */
    @SuppressWarnings({"SameParameterValue"})
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
     * @throws ServiceLocatorException if failure to lookup object in JNDI
     * @return DataSource
     */
    @SuppressWarnings({"SameParameterValue"})
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
     * @throws ServiceLocatorException  if failure to lookup object in JNDI
     * @return TopicConnectionFactory
     */
    @SuppressWarnings({"SameParameterValue"})
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
     * @throws ServiceLocatorException if failure to lookup object in JNDI
     * @return QueueConnectionFactory
     */
    @SuppressWarnings({"SameParameterValue"})
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
     * @throws ServiceLocatorException  if failure to lookup object in JNDI
     * @return QueueConnectionFactory
     */
    @SuppressWarnings({"SameParameterValue"})
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
     * @throws ServiceLocatorException  if failure to lookup object in JNDI
     * @return Topic
     */
    @SuppressWarnings({"SameParameterValue"})
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
     * Return size of the current cache.
     * @return int
     */
    public int getCacheSize()
    {
        return cache.size();
    }

    /**
     * Exists so that we can mock the context in unit tests.
     * @param initialContext the initialcontext touse
     */
    public void setInitialContext(Context initialContext)
    {
        this.initialContext = initialContext;
    }
}
