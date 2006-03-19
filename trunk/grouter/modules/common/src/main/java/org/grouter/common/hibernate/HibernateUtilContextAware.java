package org.grouter.common.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Hashtable;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.apache.commons.logging.*;
import org.apache.log4j.Logger;
import org.grouter.common.jndi.JNDIUtils;

import javax.naming.*;

/**
 * Basic Hibernate helper class for Hibernate configuration and startup.
 *
 * There must be a hibernate.cfg.xml file present. Configuration coming from that
 * file will be set to default.
 *
 * Other abc.cfg.xml files can also be loaded to provide support for multiple
 * session factoreis - however they must be referenced by a key / name.
 * In runtime fi 
 * Before doing an insert you need to call shutdown()
 */
public class HibernateUtilContextAware
{
    private static Log log = LogFactory.getLog(HibernateUtilContextAware.class);
    //private static final String INTERCEPTOR_CLASS = "hibernate.util.interceptor_class";
    // Holds config for multiple config settings and the sessionfactory for that setting
    private static Map hibernateConfigMap = new HashMap();
    // This would be the default one - usaully derived from hibernate.cfg.xml - but may
    // be overridden by another config setting
    private static HibernateConfigItem hibernateConfigItemDefault;

    // States indicate in what state we are currenlty in - some operations are prohibited
    // depending on state.
    public enum STATE {NOTINITIALISED,INITIALISED,SHUTDOWN};
    private static STATE currentState = STATE.NOTINITIALISED;

    /**
     * Bootstrap hibernate. We will always load hibernate.cfg.xml and set that to the
     * default configuration - any other sessionfactories/configurations need to override
     * and set the default flag to true.
     */
    static
    {
        //always put in this as default - any other config must override default explicitly!
        insertNewHibernateConfigItemIntoMap("default","hibernate.cfg.xml",true);
        createSessionFactoriesFromConfigMap();
    }

    /**
     * Set a new config item the config Map. If setToDefault is true then we remove any prior default
     * sessionfactory from the config map - last default set will win.
     *
     * @param sessionFactoryName
     * @param hibernateCfgFile
     * @param setToDefault
     */
    public static void insertNewHibernateConfigItemIntoMap(String sessionFactoryName, String hibernateCfgFile, boolean setToDefault)
    {
        if(currentState == STATE.INITIALISED)
        {
            throw new IllegalStateException("You need to issue a shutdown before entering new configurations!!");
        }
        HibernateConfigItem hibernateConfigItem = new HibernateConfigItem(sessionFactoryName, hibernateCfgFile, setToDefault);
        hibernateConfigMap.put(hibernateConfigItem.getId(), hibernateConfigItem);
    }

    /**
     * Set a new config item the config Map. If setToDefault is true then we remove any prior default
     * sessionfactory from the config map - last default set will win.
     *
     * @param sessionFactoryName
     * @param hibernateConfig
     * @param setToDefault
     */
    public static void insertNewHibernateConfigItemIntoMap(String sessionFactoryName, Configuration hibernateConfig, boolean setToDefault)
    {
        if(currentState == STATE.INITIALISED)
        {
            throw new IllegalStateException("You need to issue a shutdown before entering new configurations!!");
        }
        HibernateConfigItem hibernateConfigItem = new HibernateConfigItem(sessionFactoryName, hibernateConfig, setToDefault);
        hibernateConfigMap.put(hibernateConfigItem.getId(), hibernateConfigItem);
    }



    /**
     * Set default sessionfactory in config map to false.
     */
    /*private static void removeDefault()
    {
        Iterator iter = hibernateConfigMap.keySet().iterator();
        while (iter.hasNext())
        {
            String key = (String)iter.next();
            HibernateConfigItem hibernateConfigItem = ((HibernateConfigItem)hibernateConfigMap.get(key));
            boolean alreadySetDefaultItem = hibernateConfigItem.isDeafult();
            if(alreadySetDefaultItem)
            {
                hibernateConfigItem.setDeafult(false);
            }
        }
        log.info("Removed any default session factory.");
    } */


    /**
     * Create session factories and configurations and store in hibernateConfigMap. On
     * completion we enter INITIALISED state.
     */
    private static void createSessionFactoriesFromConfigMap()
    {
        // read in all config and create session factories
        Iterator iter = hibernateConfigMap.keySet().iterator();
        while (iter.hasNext())
        {
            SessionFactory sessionFactory = null;
            String key = (String)iter.next();
            HibernateConfigItem hibernateConfigItem = (HibernateConfigItem)hibernateConfigMap.get(key);
            String file = ((HibernateConfigItem)hibernateConfigMap.get(key)).getConfigFile();
            try
            {
                log.info("Loading properties from : " + file);
                Configuration configuration = new Configuration();
                configuration = configuration.configure(file);
                String sessionFactoryName = configuration.getProperty(Environment.SESSION_FACTORY_NAME);
                if (sessionFactoryName != null)
                {
                    log.debug("Looking up SessionFactory in JNDI with name : " + sessionFactoryName);
                    try
                    {
                        Hashtable env = new Hashtable();
                        env.put(Context.INITIAL_CONTEXT_FACTORY,configuration.getProperty(Environment.JNDI_CLASS));
                        env.put(Context.URL_PKG_PREFIXES,configuration.getProperty(Environment.JNDI_PREFIX));
                        env.put(Context.PROVIDER_URL,configuration.getProperty(Environment.JNDI_URL));
                        Context context = new InitialContext(env);
                        JNDIUtils.printJNDI(context,log);
                        sessionFactory = (SessionFactory) context.lookup(sessionFactoryName);
                        if (sessionFactory == null)
                        {
                            throw new IllegalStateException("SessionFactory from JNDI lookup returned a null implemenation  using file : " + file);
                        }
                    }
                    catch (NamingException ex)
                    {
                        log.error("Failed looking up sessinfactory : " + sessionFactoryName,ex);
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    sessionFactory = configuration.buildSessionFactory();
                    if (sessionFactory == null )
                    {
                        throw new IllegalStateException("SessionFactory could not be createed from the configuration using file : " + file);
                    }
                }
                hibernateConfigItem.setConfiguration(configuration);
                hibernateConfigItem.setSessionFactory(sessionFactory);
                // We need to have a default sessionfactory / configuration
                if(hibernateConfigItem.isDeafult())
                {
                    hibernateConfigItemDefault = hibernateConfigItem;
                }
                // setInterceptor(configuration, null);
                // hibernateConfigMap.put(key)
            }
            catch (Throwable ex)
            {
                log.error("Failed initializing from configuration.", ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        currentState = STATE.INITIALISED;
        log.info("Entered state : " + currentState);
    }

    /**
     * Returns the Hibernate configuration for a session factory.
     *
     * @return Configuration
     */
    public static Configuration getConfiguration(String sessionFactoryName)
    {
        HibernateConfigItem hibernateConfigItem = getConfigItemFromMap(sessionFactoryName);
        return hibernateConfigItem.getConfiguration();
    }


    /**
     * Returns the default Hibernate configuration.
     *
     * @return Configuration
     */
    public static Configuration getConfiguration()
    {
        return hibernateConfigItemDefault.getConfiguration();
    }

    /**
     * Pull out the configitem or throw runtime exception if not found.
     * @param sessionFactoryName
     * @return HibernateConfigItem
     */
    private static HibernateConfigItem getConfigItemFromMap(String sessionFactoryName)
    {
        HibernateConfigItem hibernateConfigItem = null;
        if (hibernateConfigMap.containsKey(sessionFactoryName))
        {
            hibernateConfigItem = (HibernateConfigItem)hibernateConfigMap.get(sessionFactoryName);
        }
        else
        {
            throw new IllegalStateException("Configuration not available for : " + sessionFactoryName);
        }
        return hibernateConfigItem;
    }

    /**
     * Returns the global SessionFactory.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory(String sessionFactoryName)
    {
        HibernateConfigItem hibernateConfigItem = getConfigItemFromMap(sessionFactoryName);
        SessionFactory sf = hibernateConfigItem.getSessionFactory();
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
    public static SessionFactory getSessionFactory()
    {
        if(currentState == STATE.SHUTDOWN)
        {
            throw new IllegalStateException("Current state : " + currentState + ". You need to call rebuildSessionFactory before calling this method!!");
        }
        return hibernateConfigItemDefault.getSessionFactory();
    }

    /**
     * Give an indication of how many configuraations we have set up.
     * @return int
     */
    public static int getHibernateConfigMapSize()
    {
        return hibernateConfigMap.size();
    }

    /**
     * Closes the current SessionFactory and releases all resources.
     *
     * The only other method that can be called on HibernateUtil after this one is
     * rebuildSessionFactory(Configuration).
     */

    public static void shutdown()
    {
        log.debug("Shutting down Hibernate.");
        Iterator iter = hibernateConfigMap.values().iterator();
        while (iter.hasNext())
        {
            HibernateConfigItem hibernateConfigItem = (HibernateConfigItem) iter.next();
            log.debug("Closing sessionfactory : " + hibernateConfigItem.getId());
            SessionFactory sf = hibernateConfigItem.getSessionFactory();
            sf.close();
            sf = null;
            log.debug("Closed sessionfactory : " + hibernateConfigItem.getId());
        }
        hibernateConfigItemDefault = null;
        currentState = STATE.SHUTDOWN;
        log.info("Entered state : " + currentState);
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
        synchronized (hibernateConfigMap)
        {
            createSessionFactoriesFromConfigMap();
        }
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

    /**
     * Return current state.
     * @return the current state of HibernateUtil
     */
    public static STATE getCurrentState()
    {
        return currentState;
    }


    public static class HibernateConfigItem
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

        public HibernateConfigItem(String id, String configFile, boolean isDeafult)
        {
            this.id = id;
            this.configFile = configFile;
            this.isDeafult = isDeafult;
        }

        public HibernateConfigItem(String id, Configuration configuration, boolean isDeafult)
        {
            this.id = id;
            this.isDeafult = isDeafult;
            this.configuration = configuration;
        }

        public void setDeafult(boolean deafult)
        {
            isDeafult = deafult;
        }

        /**
         * @return Returns the configFile.
         */
        public String getConfigFile()
        {
            return configFile;
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

    }
}
