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

    public void testSomething() throws Exception
    {
    }


    public void testInitLog4j()
    {
        System.setProperty("log4j.configuration","");
    }


    public void testInitLog4j2()
    {
        System.setProperty("log4j.configuration","dsfsdfs");
        try
        {
            new Log4JInit();
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }
}