package org.grouter.core.config;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 12, 2006
 * Time: 8:13:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigTest extends TestCase
{
    /**
     * Test null values.
     */
    public void testNodeConfig()
    {
        try
        {
            NodeConfigFactory.getNode(null, null);
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }

    /**
     * Test null values.
     */
    public void testOutFOlder()
    {
        try
        {
            OutFolderConfig outFolderConfig = new OutFolderConfig(null);
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }



    /**
     * Test null values.
     */
    public void testBackup()
    {
        try
        {
            LocalStoreConfig localStoreConfig = new LocalStoreConfig(null);
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }


}
