package org.grouter.common.jndi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import junit.framework.TestCase;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-mar-15
 * Time: 16:46:16
 * To change this template use File | Settings | File Templates.
 */
public class JNDIUtilsTest extends TestCase
{
    private static Logger logger = Logger.getLogger(JNDIUtilsTest.class);

    public void testCreateInMemoryIndiProviderNegative()
    {
        try
        {
            JNDIUtils.createInMemoryJndiProvider(null);
        } catch (Exception e)
        {
            assertTrue(true);
        }

        try
        {
            new BindingItem("jndiname1", "apache", new String[]{"","",""});
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }

    public void testCreateInMemoryIndiProviderPositiv()
    {
        BindingItem bindingItem = new BindingItem("jndiname1", null, new String[]{"java:comp","mbeans"});
        List<BindingItem> bindingItems = new ArrayList<BindingItem>();
        bindingItems.add(bindingItem);
        JNDIUtils.createInMemoryJndiProvider(bindingItems);

        try
        {
            // Do a lookup to test our inmemory jndi provider...
            Context context = new InitialContext();
            logger.info("Looking up : " + bindingItem.getFullJnidPath());
            context.lookup( bindingItem.getFullJnidPath());
        } catch (NamingException e)
        {
            logger.error(e,e);
            assertFalse("Failed doing lookup",true);
        }
    }

}
