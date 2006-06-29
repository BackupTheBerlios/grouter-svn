package org.grouter.common.guid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-mar-13
 * Time: 23:20:47
 * To change this template use File | Settings | File Templates.
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
