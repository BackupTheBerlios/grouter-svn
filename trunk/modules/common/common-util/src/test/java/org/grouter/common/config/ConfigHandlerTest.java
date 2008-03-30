package org.grouter.common.config;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.core.io.ClassPathResource;


/**
 * Test the confighandler class {@link XmlConfigHandler}
 *
 * @author Georges Polyzois
 */
public class ConfigHandlerTest extends TestCase
{
    private static Logger logger = Logger.getLogger(ConfigHandlerTest.class);
    private static final String GROUTERCONFIG_XML = "grouterconfig.xml";
    ClassPathResource classPathResource;

    /**
     * Setup for every testXyz method.
     */
    public void setUp()
    {
        classPathResource = new ClassPathResource(GROUTERCONFIG_XML, this.getClass().getClassLoader());
    }

    /**
     * Basic load test to see if we get expected values from xml file.
     *
     * @throws Exception
     */
 /*   public void testLoadConfigFile() throws Exception
    {
        logger.debug("Loading resource");
        configHandler = new ConfigHandler( classPathResource.getInputStream() , null);
        String name = configHandler.getGrouterConfigDocument().getGrouter().getName();
        assertEquals("grouter", name);

        //String cronjob = configHandler.getGrouterConfigDocument().getGrouter().getGlobal().getArchiveHandler().getCronJob();
        //assertEquals("Every 10 minutes@0 0 0-23 * * ?", cronjob);
    }
   */
    /**
     * Basic load test to see if we get expected values from INVALID xml file.
     *
     */
    public void testLoadInvalidConfigFile()
    {
        logger.debug("Loading resource");
        try
        {
            new XmlConfigHandler(classPathResource.getInputStream(), new XmlOptions());
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
   /*HibernateUtilContexAwareTest public void testLoadStoreLoadConfigFile() throws Exception
    {
        logger.debug("Loading resource");
        assertTrue( classPathResource.getFile().isFile() );
        
        configHandler = new ConfigHandler( classPathResource.getInputStream() , null);
        String name = configHandler.getGrouterConfigDocument().getGrouter().getName();
        assertEquals("grouter", name);

        String newName = "Stored new name";
        configHandler.getGrouterConfigDocument().getGrouter().setName(newName);

        String fileName = System.getProperty("java.io.tempdir") + "grouter_test.xml";
        configHandler.getGrouterConfigDocument().getGrouter().save(new File(fileName));
    }
    */
}
