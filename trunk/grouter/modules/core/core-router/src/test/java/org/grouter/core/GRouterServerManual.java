package org.grouter.core;

import junit.framework.TestCase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.Map;
import java.io.IOException;

/**
 * @author Georges Polyzois
 */
public class GRouterServerManual extends TestCase //extends AbstractGrouterTests
{
    // use cleanup script - build.xml - to recreate db after running this...
    public void manualStartUpGrouterWithValidConfig() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/grouterconfig_file_file.xml");
        GRouterServer grouter = new GRouterServer(classPathResource.getFile().toString());
        grouter.startGrouter( );
    }

    public void testStartUpGrouterThread() throws Exception
    {
        // GRouterServer grouter = new GRouterServer( router );
        // setComplete();
        // grouter.startGrouter( );
    }

    public void doSetup()
    {

    }


    // recreate db before running this
    public void manualTestStartGrouterWithValidConfig() throws Exception
    {
        ClassPathResource classPathResource = new ClassPathResource("routerconfig/grouterconfig_file_file.xml");
        GRouterServer grouter = new GRouterServer(classPathResource.getFile().toString());

        ApplicationContext context = new ClassPathXmlApplicationContext(getConfigLocations());
        DataSource  dataSource = (DataSource) context.getBean( "dataSource" );

        // do some verifications of data in db
        JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
        
        assertEquals( 1, jdbcTemplate.queryForInt( "select count(*) from router" ) );
        assertEquals( 2, jdbcTemplate.queryForInt( "select count(*) from node" ) );
        Map mp = jdbcTemplate.queryForMap( "select * from node where id = 1" );
        assertEquals( "File to file", mp.get("name") );
        assertEquals( "grouter_id_2", mp.get("router_fk") );
    }


    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        "context-domain-datasource.xml"
                };
    }

}
