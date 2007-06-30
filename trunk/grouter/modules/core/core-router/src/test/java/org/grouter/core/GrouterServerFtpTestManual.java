package org.grouter.core;

import junit.framework.TestCase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

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
public class GrouterServerFtpTestManual extends TestCase //extends AbstractGrouterTests
{
    private static final String DATA_SOURCE_BEAN_NAME = "dataSource";

    /**
     * use cleanup script - build.xml - to recreate db after running this...
     *
     * @throws Exception if failure to startup
     */
    public static void manualStartUpGrouterWithValidConfig() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/grouterconfig_ftp.xml");
        GrouterServerImpl grouter = new GrouterServerImpl(classPathResource.getFile().toString());
        grouter.start();
    }

    public static void main(String[] args)
    {
        try
        {
            manualStartUpGrouterWithValidConfig();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void testStartUpGrouterThread() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/grouterconfig_file.xml");
        GrouterServerImpl grouter = new GrouterServerImpl(classPathResource.getFile().toString());
        grouter.start();
    }

    public void doSetup()
    {

    }


    /**
     * Recreate db before running this test using Ant build.xml in root
     *
     * @throws Exception if failure to init Grouter.
     */
    public void testStartGrouterWithValidConfig() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/grouterconfig_file.xml");
        GrouterServerImpl grouter = new GrouterServerImpl(classPathResource.getFile().toString());

        assertNotNull(grouter);

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