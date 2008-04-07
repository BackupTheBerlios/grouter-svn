package org.grouter.core;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommand;
import org.grouter.domain.entities.Router;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Base class for running grouter tests. Creates a router, directories and populates
 * the inbound folder with some sample messages.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractRouterTests extends AbstractTransactionalDataSourceSpringContextTests
{
    private static Logger logger = Logger.getLogger(AbstractRouterTests.class);
    private static final String GROUTER_ID = "grouter_1";
    public Router router = new Router(GROUTER_ID, "aname", System.getProperty("java.io.tmpdir") + "/" + GROUTER_ID);
    public BlockingQueue<AbstractCommand> blockingQueue = new ArrayBlockingQueue<AbstractCommand>(10);
    public static final String BASE_FOLDER_FOR_TEST = System.getProperty("java.io.tmpdir") + File.separator + GROUTER_ID;
    private boolean cleanup = true;
    //    public Node fileToFileNode;
    SessionFactory sessionFactory;


    protected AbstractRouterTests()
    {
        setAutowireMode(AUTOWIRE_BY_NAME);
    }

    /**
     * Overide if we want to preserver directory and messages after a test run.
     */
    public void setDoNotCleanup()
    {
        cleanup = false;
    }

    /**
     * Cleaning up directoreis and files after a test run, unless setComplete() has not been called.
     * Corresponds to JUnit tearDown.
     *
     * @throws Exception
     */
    @Override
    public void onTearDownInTransaction() throws Exception
    {
        if (cleanup)
        {
            FileUtils.deleteDirectory(new File(BASE_FOLDER_FOR_TEST));
        } else
        {
            logger.info("Leaving file structure " + BASE_FOLDER_FOR_TEST + " and not cleaning up files after test run!!!");
        }
    }

    /**
     * Do a setup for every test so we have a clean router for every run. onSetup is final and
     * Spring provides us with this method to hook us into.
     * @throws Exception
     */
    /*
    @Override
    public void onSetUpBeforeTransaction() throws Exception
    {

        createRouter();

        createDirs();

        createData();
    }
      */

    /**
     * Setup up dirs for the router.
     * @throws Exception
     */

    /*
    private void createDirs() throws Exception
    {
        logger.debug("## Nodes");
        for (Node node : router.getNodes())
        {
            FileUtils.forceMkdir(new File( node.getInBound().getUri() ));
            FileUtils.forceMkdir(new File( node.getInBound().getUri() + "/emptyfolder"));
            logger.debug("Created dir : " + node.getInBound().getUri() );

            FileUtils.forceMkdir(new File( node.getOutBound().getUri()));
            logger.debug("Created dir : " + node.getOutBound().getUri() );

            FileUtils.forceMkdir(new File( node.getBackupUri() ));
            logger.debug("Created dir : " + node.getBackupUri() );

            FileUtils.forceMkdir(new File( node.getInternalQueueUri() ));
            logger.debug("Created dir : " + node.getInternalQueueUri() );
        }
    } */

//    public abstract void doSetup();

    /**
     * Create the router.
     *
     * @throws IOException
     */
    public abstract void createRouter();
/*    public void createRouter() throws IOException
    {
        createFileToFileNode();


        Set<Node> nodes = new HashSet<Node>();
        nodes.add(fileToFileNode);
        router.setNodes( nodes );
    }
*/

    /*
    private void createFileToFileNode()
    {
        fileToFileNode = new Node("NODE_ID_1", "fileToFileNode");
        fileToFileNode.setBackupUri( BASE_FOLDER_FOR_TEST + router.getId() + "/backup" );
        fileToFileNode.setInternalQueueUri( BASE_FOLDER_FOR_TEST + router.getId() + "/internalq" );

        EndPoint inbound = new EndPoint();
        inbound.setUri(BASE_FOLDER_FOR_TEST + router.getId() + "/in");
        inbound.setEndPointType( EndPointType.FILE_READER );
        inbound.setCron( "0/5 * * * * ?" );
        inbound.setId( "1" );

        EndPoint outbound = new EndPoint(  );
        outbound.setEndPointType(EndPointType.FILE_WRITER);
        outbound.setUri(BASE_FOLDER_FOR_TEST + router.getId() + "/out");
        outbound.setEndPointType( EndPointType.FILE_WRITER );
        outbound.setCron( "0/5 * * * * ?" );
        outbound.setId( "2" );

        fileToFileNode.setInBound( inbound );
        fileToFileNode.setOutBound( outbound );
    }

    private void createFtpToFileNode()
    {
        fileToFileNode = new Node("NODE_ID_2", "ftpToFileNode");
        fileToFileNode.setBackupUri( BASE_FOLDER_FOR_TEST + router.getId() + "/backup" );
        EndPoint inbound = new EndPoint();
        inbound.setUri(BASE_FOLDER_FOR_TEST + router.getId() + "/in");
        inbound.setEndPointType( EndPointType.FTP_READER );
        inbound.setCron( "0/5 * * * * ?" );
        inbound.setId( "1" );

        EndPoint outbound = new EndPoint(  );
        outbound.setEndPointType(EndPointType.FILE_WRITER);
        outbound.setUri(BASE_FOLDER_FOR_TEST + router.getId() + "/out");
        outbound.setEndPointType( EndPointType.FILE_WRITER );
        outbound.setCron( "0/5 * * * * ?" );
        outbound.setId( "2" );

        fileToFileNode.setInBound( inbound );
        fileToFileNode.setOutBound( outbound );
    }
    */

    /**
     * Create messages in the inbound uris for file based inbound types.
     * @throws IOException
     */
    /*
    public void createData() throws IOException
    {
        for (Node node : router.getNodes())
        {
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata1.txt"), "test data", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata2.txt"), "test data", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata3.txt"), "test data", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata4.txt"), "test data", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata5.txt"), "test data", "UTF-8");
        }
    }
        */


    /**
     * Injected.
     *
     * @param sessionFactory injected
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Specify all context files here.
     *
     * @return an array with context files
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        // "context-domain-aop.xml","context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-router.xml", "context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-sessionfactory.xml", "context-domain-service.xml"//,"context-domain-service-rmi.xml"
                };
    }


    /**
     * The data we want to use in our test cases.
     *
     * @return a path to test data script
     */
    protected String getTestDataLocation()
    {
        return "sql/test-router-data.sql_notused";
    }

    /**
     * Convinience method.
     */
    protected void flushSession()
    {
        sessionFactory.getCurrentSession().flush();
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        super.onSetUpInTransaction();

        List<String> lines = FileUtils.readLines(new ClassPathResource(getTestDataLocation()).getFile(),
                "ISO-8859-1");

        for (String line : lines)
        {
            if (StringUtils.hasText(line))
            {
                jdbcTemplate.execute(line);
            }
        }
    }
}
