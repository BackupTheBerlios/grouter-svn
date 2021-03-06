package org.grouter.core;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Use this class to manually test different configurations with the grouter and asserting against database.
 * <p/>
 * The main method can be used to startup a Grouter with all services running - this can be combined
 * with manual client test against this running instance ( {@link org.grouter.core.GRouterClientManual} ).
 *
 * @author Georges Polyzois
 */
public class GrouterFileConfigServerTestManual extends TestCase //extends AbstractGrouterTests
{
    private static final String DATA_SOURCE_BEAN_NAME = "dataSource";

    /**
     * use cleanup script - build.xml - to recreate db after running this...
     *
     * @throws Exception if failure to startup
     */
    public static void useFileConfig() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/config-file-nodes.xml");
        RouterServerImpl router = new RouterServerImpl(classPathResource.getFile().toString());
        router.start();
    }

    public static void main(String[] args)
    {
        try
        {
            useFileConfig();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void testStartUpGrouterThread() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/config-file-nodes.xml");
        RouterServerImpl router = new RouterServerImpl(classPathResource.getFile().toString());
        router.start();
    }


    /**
     * Recreate db before running this test using Ant build.xml in root
     *
     * @throws Exception if failure to init Grouter.
     */
    public void testStartGrouterWithValidConfig() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/config-file-nodes.xml");
        RouterServerImpl router = new RouterServerImpl(classPathResource.getFile().toString());

        assertNotNull(router);

        ApplicationContext context = new ClassPathXmlApplicationContext(getConfigLocations());
        DataSource dataSource = (DataSource) context.getBean(DATA_SOURCE_BEAN_NAME);

        // do some verifications of data in db
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        assertEquals(1, jdbcTemplate.queryForInt("select count(*) from router"));
        assertEquals(2, jdbcTemplate.queryForInt("select count(*) from node"));
        Map mp = jdbcTemplate.queryForMap("select * from node where id = 1");
        assertEquals("File to file", mp.get("name"));
        assertEquals("grouter_id_2", mp.get("router_fk"));
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