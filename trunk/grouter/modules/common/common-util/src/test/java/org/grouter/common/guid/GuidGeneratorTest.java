package org.grouter.common.guid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

/**
 * Test guid
 *
 * @author Georges Polyzois
 */
public class GuidGeneratorTest  extends TestCase
{
    GuidGenerator guidGenerator = GuidGenerator.getInstance();
    public void testGuid()
    {
        String guid = guidGenerator.getGUID();
        assertEquals("",32,guid.length());

        guid = guidGenerator.getGUID(true);
                assertEquals("",35,guid.length());

    }

}
