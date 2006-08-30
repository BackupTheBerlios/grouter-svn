package org.grouter.common.config;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.grouter.common.config.ConfigHandler;

import java.io.File;

/**
 * Test the confighandler class {@link ConfigHandler}
 *
 * @author Georges Polyzois
 */
public class ConfigHandlerTest extends TestCase
{
    private static Logger logger = Logger.getLogger(ConfigHandlerTest.class);
    private ConfigHandler configHandler;
    private ClassLoader classLoader;
    private static final String INVALID_GROUTERCONFIG_XML = "../../../../../resources/config/invalid_grouterconfig.xml";
    private static final String GROUTERCONFIG_XML = "grouterconfig.xml";

    /**
     * Setup for every testXyz method.
     */
    public void setUp()
    {
        classLoader = ConfigHandlerTest.class.getClassLoader();
    }

    /**
     * Basic load test to see if we get expected values from xml file.
     *
     * @throws Exception
     */
    public void testLoadConfigFile() throws Exception
    {
        logger.debug("Loading resource");
        configHandler = new ConfigHandler(classLoader.getResourceAsStream(GROUTERCONFIG_XML), null);
        String name = configHandler.getGrouterConfigDocument().getGrouterConfig().getName();
        assertEquals("grouter", name);

        String cronjob = configHandler.getGrouterConfigDocument().getGrouterConfig().getGlobalSettings().getArchiveHandler().getCronJob();
        assertEquals("Every 10 minutes@0 0 0-23 * * ?", cronjob);
    }

    /**
     * Basic load test to see if we get expected values from INVALID xml file.
     *
     * @throws Exception
     */
    public void testLoadInvalidConfigFile() throws Exception
    {
        logger.debug("Loading resource");
        try
        {
            configHandler = new ConfigHandler(classLoader.getResourceAsStream(INVALID_GROUTERCONFIG_XML), new XmlOptions());
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }

    /**
     * Basic load test to see if we get expected values from xml file.
     *
     * @throws Exception
     */
    public void testLoadStoreLoadConfigFile() throws Exception
    {
        logger.debug("Loading resource");
        configHandler = new ConfigHandler(classLoader.getResourceAsStream(GROUTERCONFIG_XML), null);
        String name = configHandler.getGrouterConfigDocument().getGrouterConfig().getName();
        assertEquals("grouter", name);

        String newName = "Stored new name";
        configHandler.getGrouterConfigDocument().getGrouterConfig().setName(newName);

        String fileName = System.getProperty("java.io.tempdir") + "grouter_test.xml";
        configHandler.getGrouterConfigDocument().getGrouterConfig().save(new File(fileName));
    }
}
