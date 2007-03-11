package org.grouter.core.config;

import junit.framework.TestCase;
import org.grouter.config.GrouterDocument;

/**
 * Unit test.
 *
 * @author Georges Polyzois
 */
public class ConfigFactoryTest extends TestCase
{
    /**
     * Test null values.
     */
    public void testOutFOlder()
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

}
