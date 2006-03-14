package org.grouter.common.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.apache.commons.logging.*;

import javax.naming.*;

/**
 * Basic Hibernate helper class for Hibernate configuration and startup.
 */
public class HibernateUtilContextAware
{
    private static Log log = LogFactory.getLog(HibernateUtilContextAware.class);
    private static final String INTERCEPTOR_CLASS = "hibernate.util.interceptor_class";
    private static Map configurationMap = new HashMap();
    private static ConfigItem configItemDefault;


    /**
     * Set a new config item the config Map. If setToDefault is true then we remove any prior default
     * sessionfactory form the config map - last default wins.
     *
     * @param sessionFactoryName
     * @param hibernateCfgFile
     * @param setToDefault
     */
    public static void insertNewConfigIntoMap(String sessionFactoryName, String hibernateCfgFile, boolean setToDefault)
    {
        if(setToDefault)
        {
            removeDefault();
        }

        ConfigItem configItem = new ConfigItem(sessionFactoryName, hibernateCfgFile, setToDefault);
        configurationMap.put(configItem.getId(), configItem);
        if(setToDefault)
        {
            configItemDefault = configItem;
        }
    }

    /**
     * Set default sessionfactory in config map to false.
     */
    private static void removeDefault()
    {
        Iterator iter = configurationMap.keySet().iterator();
        while (iter.hasNext())
        {
            String key = (String)iter.next();
            ConfigItem configItem = ((ConfigItem)configurationMap.get(key));
            boolean alreadySetDefaultItem = configItem.isDeafult();
            if(alreadySetDefaultItem)
            {
                configItem.setDeafult(false);
            }
        }
        log.info("Removed any default session factory.");
    }


    static
    {
        insertNewConfigIntoMap("default","hibernate.cfg.xml",true);

        createSessionFactoriesFromConfigMap();
    }

    private static void createSessionFactoriesFromConfigMap()
    {
        // read in all config and create session factories
        Iterator iter = configurationMap.keySet().iterator();
        while (iter.hasNext())
        {
            SessionFactory sessionFactory = null;
            String key = (String)iter.next();
            ConfigItem configItem = (ConfigItem)configurationMap.get(key);
            String file = ((ConfigItem)configurationMap.get(key)).getConfigFile();
            try
            {
                log.info("Loading properties from " + file);
                Configuration configuration = new Configuration();
                configuration = configuration.configure(file);
                String jndiName = configuration.getProperty(Environment.SESSION_FACTORY_NAME);
                if (jndiName != null)
                {
                    log.debug("Looking up SessionFactory in JNDI with name : " + jndiName);
                    try
                    {
                        sessionFactory = (SessionFactory) new InitialContext().lookup(jndiName);
                        // not doing below since config can be altered for sessionfactory in runtime for the jndi instance
                        // sessionFactoryMap.put(name,sf);
                    }
                    catch (NamingException ex)
                    {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    sessionFactory = configuration.buildSessionFactory();
                }
                if (sessionFactory == null)
                {
                    throw new IllegalStateException("SessionFactory not available.");
                }
                configItem.setSessionFactory(sessionFactory);
        // setInterceptor(configuration, null);
        // configurationMap.put(key)
            }
            catch (Throwable ex)
            {
                log.error("Failed initializing from configuration.", ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

    /**
     * Returns the Hibernate configuration for a session factory.
     *
     * @return Configuration
     */
    public static Configuration getConfiguration(String sessionFactoryName)
    {
        ConfigItem configItem = getConfigItemFromMap(sessionFactoryName);
        return configItem.getConfiguration();
    }


    /**
     * Returns the default Hibernate configuration.
     *
     * @return Configuration
     */
    public static Configuration getDefaultConfiguration()
    {
        return configItemDefault.getConfiguration();
    }

    /**
     * Pull out the configitem or throw runtime exception if not found.
     * @param sessionFactoryName
     * @return ConfigItem
     */
    private static ConfigItem getConfigItemFromMap(String sessionFactoryName)
    {
        ConfigItem configItem = null;
        if (configurationMap.containsKey(sessionFactoryName))
        {
            configItem = (ConfigItem)configurationMap.get(sessionFactoryName);
        }
        else
        {
            throw new IllegalStateException("Configuration not available for : " + sessionFactoryName);
        }
        return configItem;
    }

    /**
     * Returns the global SessionFactory.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory(String sessionFactoryName)
    {
        SessionFactory sf = null;
        ConfigItem configItem = getConfigItemFromMap(sessionFactoryName);
        sf = configItem.getSessionFactory();
        if (sf == null)
        {
            throw new IllegalStateException("No sessionfactory found for : " + sessionFactoryName);
        }
        return sf;
    }

    /**
     * Returns the default SessionFactory.
     *
     * @return SessionFactory
     */
    public static SessionFactory getDefaultSessionFactory()
    {
        SessionFactory sf = configItemDefault.getSessionFactory();
        if (sf == null)
        {
            throw new IllegalStateException("No sessionfactory found for : " + configItemDefault.getId());
        }
        return sf;
    }



    /**
     * Closes the current SessionFactory and releases all resources.
     * <p>
     * The only other method that can be called on HibernateUtil after this one is rebuildSessionFactory(Configuration).
     */

    public static void shutdown()
    {
        log.debug("Shutting down Hibernate.");
        Iterator iter = configurationMap.values().iterator();
        while (iter.hasNext()) {
            ConfigItem configItem = (ConfigItem) iter.next();
            SessionFactory sf = configItem.getSessionFactory();
            sf.close();
            sf = null;
            log.debug("Closed sessionfactory : " + configItem.getId());
        }
        configItemDefault = null;
    }

    /**
     * Rebuild the SessionFactory with the static Configuration.
     * <p>
     * This method also closes the old SessionFactory before, if still open. Note that this method should only be used
     * with static SessionFactory management, not with JNDI or any other external registry.
     */
    public static void rebuildSessionFactory()
    {
        log.debug("Using current Configuration for rebuild.");
// rebuildSessionFactory(configuration);
    }

/**
 * Rebuild the SessionFactory with the given Hibernate Configuration.
 * <p>
 * HibernateUtil does not configure() the given Configuration object, it directly calls buildSessionFactory(). This
 * method also closes the old SessionFactory before, if still open.
 *
 * @param cfg
 */
/*	public static void rebuildSessionFactory(Configuration cfg)
	{
		log.debug("Rebuilding the SessionFactory from given Configuration.");

		synchronized (sessionFactory) {
			if (sessionFactory != null && !sessionFactory.isClosed())
				sessionFactory.close();
			if (cfg.getProperty(Environment.SESSION_FACTORY_NAME) != null)
				cfg.buildSessionFactory();
			else
				sessionFactory = cfg.buildSessionFactory();
			configuration = cfg;
		}

	}
*/
/**
 * Register a Hibernate interceptor with the current SessionFactory.
 * <p>
 * Every Session opened is opened with this interceptor after registration. Has no effect if the current Session of
 * the thread is already open, effective on next close()/getCurrentSession().
 * <p>
 * Attention: This method effectively restarts Hibernate. If you need an interceptor active on static startup of
 * HibernateUtil, set the <tt>hibernateutil.interceptor</tt> system property to its fully qualified class name.
 */
/*
* public static SessionFactory registerInterceptorAndRebuild(Interceptor interceptor, String sessionFactoryName) {
* log.debug("Setting new global Hibernate interceptor and restarting."); setInterceptor(configuration,
* interceptor); rebuildSessionFactory(); return getSessionFactory(sessionFactoryName); }
*/
/*
* public static Interceptor getInterceptor() { return configuration.getInterceptor(); }
*/
/**
 * Resets global interceptor to default state.
 */
/*
* public static void resetInterceptor() { log.debug("Resetting global interceptor to configuration setting");
* setInterceptor(configuration, null); }
*/
    /**
     * Either sets the given interceptor on the configuration or looks it up from configuration if null.
     */
/*private static void setInterceptor(Configuration configuration, Interceptor interceptor)
    {
        String interceptorName = configuration.getProperty(INTERCEPTOR_CLASS);
        if (interceptor == null && interceptorName != null) {
            try {
                Class interceptorClass = HibernateUtil.class.getClassLoader().loadClass(interceptorName);
                interceptor = (Interceptor) interceptorClass.newInstance();
            }
            catch (Exception ex) {
                throw new RuntimeException("Could not configure interceptor: " + interceptorName, ex);
            }
        }
        if (interceptor != null) {
            configuration.setInterceptor(interceptor);
        }
        else {
            configuration.setInterceptor(EmptyInterceptor.INSTANCE);
        }
    }
*/



    public static class ConfigItem
    {
        private String id;
        private String configFile;
        private Configuration configuration;
        private SessionFactory sessionFactory;
        private boolean isDeafult;

        public boolean isDeafult()
        {
            return isDeafult;
        }

        public ConfigItem(String id, String configFile, boolean isDeafult)
        {
            this.id = id;
            this.configFile = configFile;
            this.isDeafult = isDeafult;
        }

        /**
         * @return Returns the configFile.
         */
        public String getConfigFile()
        {
            return configFile;
        }

        /**
         * @param configFile The configFile to set.
         */
        public void setConfigFile(String configFile)
        {
            this.configFile = configFile;
        }

        public void setDeafult(boolean deafult)
        {
            isDeafult = deafult;
        }

        /**
         * @return Returns the configuration.
         */
        public Configuration getConfiguration()
        {
            return configuration;
        }

        /**
         * @param configuration The configuration to set.
         */
        public void setConfiguration(Configuration configuration)
        {
            this.configuration = configuration;
        }

        /**
         * @return Returns the sessionFactory.
         */
        public SessionFactory getSessionFactory()
        {
            return sessionFactory;
        }

        /**
         * @param sessionFactory The sessionFactory to set.
         */
        public void setSessionFactory(SessionFactory sessionFactory)
        {
            this.sessionFactory = sessionFactory;
        }

        /**
         * @return Returns the id.
         */
        public String getId()
        {
            return id;
        }

        /**
         * @param id The id to set.
         */
        public void setId(String id)
        {
            this.id = id;
        }
    }
}
