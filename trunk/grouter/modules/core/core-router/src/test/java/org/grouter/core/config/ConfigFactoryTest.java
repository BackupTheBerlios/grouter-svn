package org.grouter.core.config;

import junit.framework.TestCase;
import org.grouter.config.GrouterDocument;
import org.grouter.common.config.ConfigHandler;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Node;
import org.springframework.core.io.ClassPathResource;

/**
 * Unit test.
 *
 * @author Georges Polyzois
 */
public class ConfigFactoryTest extends TestCase
{
    public void testNull()
    {
        try
        {
            ConfigFactory.createRouter( null );
            fail();
        } catch (Exception e)
        {
            // expected
        }

    }

    public void testFile()  throws Exception
    {
        String configFile = "grouterconfig_file_file.xml";
        ClassPathResource classPathResource = new ClassPathResource( configFile );
        ConfigHandler configHandler = new ConfigHandler( classPathResource.getInputStream() , null);

        Router router = ConfigFactory.createRouter( configHandler.getGrouterConfigDocument().getGrouter() );

        assertNotNull( router );

        assertEquals( 1 , router.getNodes().size() );

        Node node = router.getNodes().iterator().next();
        assertEquals( "file://temp/in" , node.getInBound().getUri() );
        assertEquals( "file://temp/out" , node.getOutBound().getUri() );  

    }

}
