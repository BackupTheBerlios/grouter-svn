package org.grouter.common.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Hashtable;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.apache.commons.logging.*;
import org.grouter.common.jndi.JNDIUtils;

import javax.naming.*;

/**
 * Basic Hibernate helper class for Hibernate configuration and bootstraping. It will hold multiple
 * sessionfactories in a map enabling switching between different datasources.
 *
 *
 * Use HibernateUtilContextAware.bootStrap(true) if you want to load the hibernate.cfg.xml file
 * AND any other configurations you have added using addSessionFactoryToMap(...). A bootstrap can only
 * happen once!
 * If you during runtime need to add or remove sessionfactories you must first see to that
 * HibernateUil is in a state of SHUTDOWN - by calling the shutdown() method. After that you can
 * addSessionFactoryToMap(...) and rebuildSessionFactory()
 *
 *
 * @see HibernateUtilContextAware for unit testing of this class.
 * @author Georges Polyzois
 */
public class HibernateUtilContextAware
{
    private static Log log = LogFactory.getLog(HibernateUtilContextAware.class);
    private static Map hibernateConfigMap = new HashMap();
    private static HibernateConfigItem hibernateConfigItemDefault;
    // States indicate in what state we are currenlty in - some operations are prohibited depending on state.
    public enum STATE {NOTINITIALISED,INITIALISED,SHUTDOWN};
    private static STATE currentState = STATE.NOTINITIALISED;
    private static boolean isBootstraped = false;

    /**
     * This method will load hibernate.cfg.xml if found in path and set that conifguration to the default configuration -
     * any other sessionfactories/configurations need to override and set the default flag to true
     * using addSessionFactoryToMap(...).
     * It will also load any other config settings for sessionfactories found in hibernateConfigMap.
     *
     *
     * @param useHibernateCfgXmlFileOnPath  if true load any found hibernate.cfg.xml 
     */
    public static void bootStrap(boolean useHibernateCfgXmlFileOnPath)
    {
        if(isBootstraped)
        {
            log.info("Already bootstraped Hibernate - it is a one time only bootstrapping!");
            return;
        }
        else if (useHibernateCfgXmlFileOnPath )
        {
            addSessionFactoryToMap("default","hibernate.cfg.xml",true);
            isBootstraped = true;
            createSessionFactoriesFromConfigMap();
        }


    }

    /**
     * Set a new config item in the config Map. If setToDefault is true then we remove any prior default
     * sessionfactory from the config map - last default set will win.
     *
     * If you want to use a default hibernate.cfg.xml file then init HibernateUtil with:
     * addSessionFactoryToMap("default","hibernate.cfg.xml",true);
     *
     * @param sessionFactoryName
     * @param hibernateCfgFile
     * @param setToDefault
     */
    public static void addSessionFactoryToMap(String sessionFactoryName, String hibernateCfgFile, boolean setToDefault)
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
    public static void addSessionFactoryToMap(String sessionFactoryName, Configuration hibernateConfig, boolean setToDefault)
    {
        if(currentState == STATE.INITIALISED)
        {
            throw new IllegalStateException("You need to issue a shutdown before entering new configurations!!");
        }
        HibernateConfigItem hibernateConfigItem = new HibernateConfigItem(sessionFactoryName, hibernateConfig, setToDefault);
        hibernateConfigMap.put(hibernateConfigItem.getId(), hibernateConfigItem);
    }

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
            String file = hibernateConfigItem.getConfigFile();
            Configuration configuration = null;
            if(file==null)
            {
                log.info("Loading properties config and not from file " );
                configuration = hibernateConfigItem.getConfiguration();
            }
            else
            {
                log.info("Loading properties from : " + file);
                configuration = new Configuration();
                configuration = configuration.configure(file);
            }
            try
            {
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
     * Returns the default SessionFactory. If in SHUTDOWN state a runtime exception is thrown.
     *
     * @return SessionFactory
     * @throws IllegalStateException if currentstate is SHUTDOWN
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
     * You need to call rebuildSessionFactory() to reinit the HibernateUtil's sessionfactories in the
     * internal map of sessionfactories.
     *
     * @param removeAllConfigFromInternalMap if true it will clear the internal map of sessionfactories and config
     */

    public static void shutdown(boolean removeAllConfigFromInternalMap)
    {
        log.debug("Shutting down Hibernate.");
        Iterator iter = hibernateConfigMap.values().iterator();
        while (iter.hasNext())
        {
            HibernateConfigItem hibernateConfigItem = (HibernateConfigItem) iter.next();
            log.debug("Closing sessionfactory : " + hibernateConfigItem.getId());
            SessionFactory sf = hibernateConfigItem.getSessionFactory();
            if (sf!=null)
            {
                sf.close();
            }
            sf = null;
            log.debug("Closed sessionfactory : " + hibernateConfigItem.getId());
        }
        if(removeAllConfigFromInternalMap)
        {
            log.info("Removing all configurations from the internal hibernate config map");
            hibernateConfigMap.clear();
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
     * Return current state.
     * @return the current state of HibernateUtil
     */
    public static STATE getCurrentState()
    {
        return currentState;
    }

    /**
     * These items are stored withing the HibernateUtil map of configurations with a sessionfactories.
     */
    public static class HibernateConfigItem
    {
        private String id;
        private String configFile;
        private Configuration configuration;
        private SessionFactory sessionFactory;
        private boolean isDeafult;

        /**
         * Constructor.
         * @param id   the key or id in the map holding the items
         * @param configFile  a name of a hibernate config file
         * @param isDeafult  wether to treat this as the default item in the internal map of HibernateUtil
         */
        public HibernateConfigItem(String id, String configFile, boolean isDeafult)
        {
            this.id = id;
            this.configFile = configFile;
            this.isDeafult = isDeafult;
        }

        /**
         *
         * @param id  the key or id in the map holding the items
         * @param configuration a Hibernate configuration instance
         * @param isDeafult wether to treat this as the default item in the internal map of HibernateUtil
         */
        public HibernateConfigItem(String id, Configuration configuration, boolean isDeafult)
        {
            this.id = id;
            this.isDeafult = isDeafult;
            this.configuration = configuration;
        }

        /**
         * Wether this is a default item or not.
         * @return wether this is the dafault or not.
         */
        public boolean isDeafult()
        {
            return isDeafult;
        }

        /**
         * Set as default.
         * @param deafult
         */
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
