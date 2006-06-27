package org.grouter.common.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.grouter.common.jndi.BindingItem;
import org.grouter.common.jndi.JNDIUtils;
import org.apache.log4j.Logger;
import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *
 * @author georges.polyzois
 */
public class HibernateUtilContexAwareTest extends TestCase
{
    private static Logger logger = Logger.getLogger(HibernateUtilContexAwareTest.class);

    /**
     * Some simple tests to see it loads properly.
     *
     * The bootStrap() must be called explicitly to init Hibernate form
     * from the hibernate.cfg.xml file - which serves a special place in Hibernates bootstrap
     * loading mechanism.
     */
    public void testHibernateBootstrap()
    {
        HibernateUtilContextAware.bootStrap(true);
        defaultSessionFactoryOk();

        //at this point we should only have 1 config setup
        assertEquals(1,HibernateUtilContextAware.getHibernateConfigMapSize() );

        defaultConfigParamOk();

        //should always be a default config
        assertNotNull(HibernateUtilContextAware.getConfiguration("default"));

        try
        {
            HibernateUtilContextAware.getConfiguration("null");
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }


    /**
     * HibernateUtilContextAware should be able to handle multiple sessionfactory configurations.
     * New session factory configurations should only be added when in state SHUTDWON. After new items
     * have been added you also need to do a rebuildSessionFactory().
     */
    public void testMulitpleSessionFactories()
    {
        HibernateUtilContextAware.bootStrap(true);
        defaultSessionFactoryOk();
        // we should not put in config items while we are in state STATE.INITIALISED
        try
        {
            HibernateUtilContextAware.addSessionFactoryToMap("nondefault","nondefault_hibernate.cfg.xml",false);
        } catch (Exception e)
        {
            assertTrue(true);
        }
        //at this point we should only have 1 config setup - the previous attempt should have failed
        assertEquals(1,HibernateUtilContextAware.getHibernateConfigMapSize() );

        // Now shutdown and rebuild - this will let us add new sessionfactories into the map
        HibernateUtilContextAware.shutdown(false);
        assertEquals(HibernateUtilContextAware.STATE.SHUTDOWN, HibernateUtilContextAware.getCurrentState());
        // this should work
        HibernateUtilContextAware.addSessionFactoryToMap("nondefault","nondefault_hibernate.cfg.xml",false);
        //at this point we should have 2 config setup
        assertEquals(2,HibernateUtilContextAware.getHibernateConfigMapSize());
        // rebuild and verify our session factories are there
        HibernateUtilContextAware.rebuildSessionFactory();
        assertNotNull(HibernateUtilContextAware.getConfiguration());
        // new state entered
        assertEquals(HibernateUtilContextAware.STATE.INITIALISED, HibernateUtilContextAware.getCurrentState());
        //should be a default sessionfactory
        assertNotNull(HibernateUtilContextAware.getSessionFactory("default"));
        assertNotNull(HibernateUtilContextAware.getSessionFactory("nondefault"));

        defaultConfigParamOk();
        nonDefaultconfigParamOk();
    }

    /**
     * Again multiple session factories are tested. This time we use a global inmemory jnid provider
     * from which we try to do a look up for our session factory. We first need to create a namespace
     * and bind a sessionfactory implementation to that.
     */
    public void testMulitpleSessionFactoriesWithJNDI()
    {
        HibernateUtilContextAware.bootStrap(true);

        int sizeBeforeTest = HibernateUtilContextAware.getHibernateConfigMapSize();
        BindingItem bindingItem = new BindingItem("jndi_name", null, new String[]{"jndi:hibernate","mbeans"});
        List<BindingItem> bindingItems = new ArrayList<BindingItem>();
        bindingItems.add(bindingItem);

        JNDIUtils.createInMemoryJndiProvider(bindingItems);

        defaultSessionFactoryOk();
        //at this point we should only have 1 config setup - the previous attempt should have failed
        assertEquals(sizeBeforeTest,HibernateUtilContextAware.getHibernateConfigMapSize());

        // Now shutdown and rebuild
        HibernateUtilContextAware.shutdown(false);
        assertEquals(HibernateUtilContextAware.STATE.SHUTDOWN, HibernateUtilContextAware.getCurrentState());
        // this should work
        HibernateUtilContextAware.addSessionFactoryToMap("jndi","jndi_hibernate.cfg.xml",false);
        //at this point we should have +1 config setup
        assertEquals(sizeBeforeTest+1,HibernateUtilContextAware.getHibernateConfigMapSize());
        try
        {
            // rebuild ... this should fails since we have added a null implementation of a session factorry
            // bound to jndi:hibernate/mbeans/jndi_name
            HibernateUtilContextAware.rebuildSessionFactory();
        } catch (Throwable e)
        {
            assertTrue(true);
        }
    }


    /**
     *
     */
    public void testHibernateStateTransitions()
    {
        // shutdown and move to SHUTDOWN state
        HibernateUtilContextAware.shutdown(true);
        assertEquals(HibernateUtilContextAware.STATE.SHUTDOWN, HibernateUtilContextAware.getCurrentState());
        try
        {
            HibernateUtilContextAware.getSessionFactory();
        } catch (Exception e)
        {
            assertTrue(true);
        }
        HibernateUtilContextAware.rebuildSessionFactory();
        //should be a default sessionfactory
        assertEquals(HibernateUtilContextAware.STATE.INITIALISED, HibernateUtilContextAware.getCurrentState());
    }


    /**
     * Initialize HibernateUtil from a given Configuration in runtime. Verify that the sessionfactory
     * map of HibernateUtilContextAware is increased by one.
     */
    public void testHibernateBootstrapFromConfiguration()
    {
        HibernateUtilContextAware.shutdown(true);
        assertEquals("",0,HibernateUtilContextAware.getHibernateConfigMapSize());
        Configuration config = new Configuration().
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test").
                setProperty("hibernate.connection.username", "sa").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.connection.pool_size", "1").
                setProperty("hibernate.connection.autocommit", "true").
                setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
                setProperty("hibernate.show_sql", "true").
                setProperty("hibernate.current_session_context_class", "thread").
                setProperty("hibernate.jdbc.batch_size", "0");
        HibernateUtilContextAware.addSessionFactoryToMap("testfromconfiguration",config,true);
        HibernateUtilContextAware.rebuildSessionFactory();
        assertEquals("",1,HibernateUtilContextAware.getHibernateConfigMapSize());
    }

    private void nonDefaultconfigParamOk()
    {
        // see if the configuration loaded matches the one we think we provided
        Configuration configuration = HibernateUtilContextAware.getConfiguration("nondefault");
        String expected = configuration.getProperty(Environment.USE_QUERY_CACHE);
        assertEquals(expected,"true");
    }

    private void defaultConfigParamOk()
    {
        // see if the configuration loaded matches the one we think we provided
        Configuration defaultConfiguration = HibernateUtilContextAware.getConfiguration();
        String expected = defaultConfiguration.getProperty(Environment.DIALECT);
        assertEquals("org.hibernate.dialect.HSQLDialect",expected);
    }

    private void defaultSessionFactoryOk()
    {
        //a default sessionfactory should always be present
        SessionFactory  sessionFactory = HibernateUtilContextAware.getSessionFactory();
        assertNotNull(sessionFactory);
        //should always be a default sessionfactory
        assertNotNull(HibernateUtilContextAware.getSessionFactory("default"));
    }
}
