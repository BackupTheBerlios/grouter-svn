/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-maj-23
 * Time: 07:49:03
 * To change this template use File | Settings | File Templates.
 */
package org.grouter.common.logging.test;

import junit.framework.TestCase;
import org.grouter.common.logging.Log4JInit;
import org.apache.log4j.Logger;

public class TestLog4JInit extends TestCase
{


    public TestLog4JInit(String test)
    {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception
    {
    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception
    {
    }


    public void testInitLog4j2()
    {
        System.setProperty("log4j.configuration","dsfsdfs");
        try
        {
            Log4JInit.initLog4J();
            Logger logger = Logger.getLogger(TestLog4JInit.class);
            logger.debug("THIS SHOULD NOT BE SEEN");
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }

    public void testInitLog4j()
    {
        try
        {
            Log4JInit.initLog4J();
            Logger logger = Logger.getLogger(TestLog4JInit.class);
            logger.debug("This should work");
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }
}