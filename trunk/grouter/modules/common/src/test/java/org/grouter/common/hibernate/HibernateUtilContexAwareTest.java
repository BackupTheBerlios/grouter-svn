package org.grouter.common.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 *
 * @author georges.polyzois
 */
public class HibernateUtilContexAwareTest extends TestCase
{
    /**
     * Some simple tests to see it loads properly.
     */
    public void testHibernateBootstrap()
    {
        defaultSessionFactoryOk();

        //at this point we should only have 1 config setup
        assertEquals(1,HibernateUtilContextAware.getHibernateConfigMapSize() );

        defaultConfigParamOk();

        //should always be a default config
        assertNotNull(HibernateUtilContextAware.getConfiguration("default"));
    }


    /**
     * HibernateUtilContextAware should be
     */
    public void testMulitpleSessionFactories()
    {
        defaultSessionFactoryOk();

        // we should not put in config items while we are in state STATE.INITIALISED
        try
        {
            HibernateUtilContextAware.insertNewHibernateConfigItemIntoMap("nondefault","nondefault_hibernate.cfg.xml",false);
        } catch (Exception e)
        {
            assertTrue(true);
        }
        //at this point we should only have 1 config setup - the previous attempt should have failed
        assertEquals(1,HibernateUtilContextAware.getHibernateConfigMapSize() );

        // Now shutdown and rebuild
        HibernateUtilContextAware.shutdown();
        assertEquals(HibernateUtilContextAware.STATE.SHUTDOWN, HibernateUtilContextAware.getCurrentState());
        // this should work
        HibernateUtilContextAware.insertNewHibernateConfigItemIntoMap("nondefault","nondefault_hibernate.cfg.xml",false);
        //at this point we should have 2 config setup
        assertEquals(2,HibernateUtilContextAware.getHibernateConfigMapSize());
        // rebuild and verify our session factories are there
        HibernateUtilContextAware.rebuildSessionFactory();
        assertNotNull(HibernateUtilContextAware.getDefaultConfiguration());
        // new state entered
        assertEquals(HibernateUtilContextAware.STATE.INITIALISED, HibernateUtilContextAware.getCurrentState());
        //should be a default sessionfactory
        assertNotNull(HibernateUtilContextAware.getSessionFactory("default"));
        assertNotNull(HibernateUtilContextAware.getSessionFactory("nondefault"));

        defaultConfigParamOk();
        nonDefaultconfigParamOk();
    }

    public void testMulitpleSessionFactoriesWithJNDI()
    {
        defaultSessionFactoryOk();
        //at this point we should only have 1 config setup - the previous attempt should have failed
        assertEquals(1,HibernateUtilContextAware.getHibernateConfigMapSize() );

        // Now shutdown and rebuild
        HibernateUtilContextAware.shutdown();
        assertEquals(HibernateUtilContextAware.STATE.SHUTDOWN, HibernateUtilContextAware.getCurrentState());
        // this should work
        HibernateUtilContextAware.insertNewHibernateConfigItemIntoMap("jndi","jndi_hibernate.cfg.xml",false);
        //at this point we should have 2 config setup
        assertEquals(2,HibernateUtilContextAware.getHibernateConfigMapSize());
        // rebuild and verify our session factories are there
        HibernateUtilContextAware.rebuildSessionFactory();
        assertNotNull(HibernateUtilContextAware.getDefaultConfiguration());
        // new state entered
        assertEquals(HibernateUtilContextAware.STATE.INITIALISED, HibernateUtilContextAware.getCurrentState());
        //should be a default sessionfactory
        assertNotNull(HibernateUtilContextAware.getSessionFactory("default"));
        //assertNotNull(HibernateUtilContextAware.getSessionFactory("jndi"));

        defaultConfigParamOk();
        //nonDefaultconfigParamOk();
    }


    public void testHibernateStateTransitions()
    {
        defaultSessionFactoryOk();
        // shutdown and move to SHUTDOWN state
        HibernateUtilContextAware.shutdown();
        assertEquals(HibernateUtilContextAware.STATE.SHUTDOWN, HibernateUtilContextAware.getCurrentState());
        try
        {
            HibernateUtilContextAware.getDefaultSessionFactory();
        } catch (Exception e)
        {
            assertTrue(true);
        }
        HibernateUtilContextAware.rebuildSessionFactory();
        //should be a default sessionfactory
        assertNotNull(HibernateUtilContextAware.getSessionFactory("default"));
        assertEquals(HibernateUtilContextAware.STATE.INITIALISED, HibernateUtilContextAware.getCurrentState());

        defaultConfigParamOk();
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
        Configuration defaultConfiguration = HibernateUtilContextAware.getDefaultConfiguration();
        String expected = defaultConfiguration.getProperty(Environment.DIALECT);
        assertEquals("org.hibernate.dialect.HSQLDialect",expected);
    }

    private void defaultSessionFactoryOk()
    {
        //a default sessionfactory should always be present
        SessionFactory  sessionFactory = HibernateUtilContextAware.getDefaultSessionFactory();
        assertNotNull(sessionFactory);
        //should always be a default sessionfactory
        assertNotNull(HibernateUtilContextAware.getSessionFactory("default"));
    }
}
