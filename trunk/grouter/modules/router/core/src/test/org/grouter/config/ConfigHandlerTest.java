package org.grouter.config;

import junit.framework.TestCase;
import org.apache.xmlbeans.XmlOptions;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-feb-27
 * Time: 18:35:06
 * To change this template use File | Settings | File Templates.
 */
public class ConfigHandlerTest extends TestCase
{
    private static Logger logger = Logger.getLogger(ConfigHandlerTest.class);

    private ConfigHandler configHandler;

    public void setUp()
    {
    }

    public void testLoadConfigFile() throws Exception
    {
        logger.debug("Loading resource");
        configHandler = new ConfigHandler(this.getClass().getResourceAsStream("grouterconfig.xml"),null);

    //    String name = configHandler.getgRouterConfigDocument().getGRouterConfig().getName();
      //  assertEquals("test",name);
    }
}
