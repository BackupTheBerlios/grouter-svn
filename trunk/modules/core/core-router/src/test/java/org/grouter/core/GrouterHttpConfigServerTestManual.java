package org.grouter.core;

import junit.framework.TestCase;
import org.springframework.core.io.ClassPathResource;

/**
 * Use this class to manually test different configurations with the grouter and asserting against database.
 * <p/>
 * The main method can be used to startup a Grouter with all services running - this can be combined
 * with manual client test against this running instance ( {@link GRouterClientManual} ).
 *
 * @author Georges Polyzois
 */
public class GrouterHttpConfigServerTestManual extends TestCase //extends AbstractGrouterTests
{
    private static final String DATA_SOURCE_BEAN_NAME = "dataSource";

    /**
     * use cleanup script - build.xml - to recreate db after running this...
     *
     * @throws Exception if failure to startup
     */
    public static void useHttpConfig() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/config-http-node.xml");
        RouterServerImpl router = new RouterServerImpl(classPathResource.getFile().toString());
        router.start();
    }


    public static void main(String[] args)
    {
        try
        {
            useHttpConfig();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Configuration for datasouce.
     *
     * @return config array
     */
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        "context-domain-datasource.xml"
                };
    }

}