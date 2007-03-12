package org.grouter.core;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.grouter.domain.entities.*;
import org.grouter.core.command.AbstractCommandWriter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.IOException;

/**
 * Base class for running grouter tests. Creates a router, directories and populates
 * the inbound folder with some sample messages.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractGrouterTests extends TestCase
{
    private static Logger logger = Logger.getLogger(AbstractGrouterTests.class);
    public Router router = new Router("GROUTER_ID");
    public BlockingQueue<AbstractCommandWriter> blockingQueue = new ArrayBlockingQueue<AbstractCommandWriter>(10);
    private static final String BASE_FOLDER_FOR_TEST = System.getProperty("java.io.tmpdir") + "/grouter/";
    private boolean cleanup = true;
    public Node fileToFileNode;


    /**
     * Overide if we want to preserver directory and messages after a test run.
     */
    public void setComplete()
    {
        cleanup = false;
    }

    /**
     * Cleaning up directoreis and files after a test run, unless setComplete() has not been called.
     * @throws Exception
     */
    @Override
    public void tearDown() throws Exception
    {
        if( cleanup )
        {
            FileUtils.deleteDirectory( new File(BASE_FOLDER_FOR_TEST) );
        }
        else
        {
            logger.info( "Leaving file structure and not cleaning up files after test run!!!" );
        }

    }


    /**
     * Do a setup for every test so we have a clean router for every run.
     * @throws Exception
     */
    public void setUp() throws Exception
    {
        super.setUp();

        createRouter();

        createDirs();

        createData();
    }


    /**
     * Setup up dirs for the router.
     * @throws Exception
     */
    private void createDirs() throws Exception
    {
        for (Node node : router.getNodes())
        {
            FileUtils.forceMkdir(new File( node.getInBound().getUri() ));
            FileUtils.forceMkdir(new File( node.getInBound().getUri() + "/emptyfolder"));
            logger.debug("Created dir : " + node.getInBound().getUri() );

            FileUtils.forceMkdir(new File( node.getOutBound().getUri()));
            logger.debug("Created dir : " + node.getOutBound().getUri() );

            FileUtils.forceMkdir(new File( node.getBackupUri() ));
            logger.debug("Created dir : " + node.getBackupUri() );


        }
    }



//    public abstract void doSetup();

    /**
     * Create the router.
     * @throws IOException
     */
    void createRouter() throws IOException
    {
        fileToFileNode = new Node("NODE_ID", "fileToFileNode");
        fileToFileNode.setBackupUri( BASE_FOLDER_FOR_TEST + router.getId() + "/backup" );
        EndPoint inbound = new EndPointFileReader();
        inbound.setUri(BASE_FOLDER_FOR_TEST + router.getId() + "/in");
        inbound.setEndPointType( EndPointType.FILE_READER );
        inbound.setScheduleCron( "0/5 * * * * ?" );
        inbound.setId( 1L );

        EndPoint outbound = new EndPointFileWriter(  );
        outbound.setEndPointType(EndPointType.FILE_WRITER);
        outbound.setUri(BASE_FOLDER_FOR_TEST + router.getId() + "/out");
        outbound.setEndPointType( EndPointType.FILE_WRITER );
        outbound.setScheduleCron( "0/5 * * * * ?" );
        outbound.setId( 2L );

        fileToFileNode.setInBound( inbound );
        fileToFileNode.setOutBound( outbound );

        Set<Node> nodes = new HashSet<Node>();
        nodes.add(fileToFileNode);
        router.setNodes( nodes );
    }


    /**
     * Create messages in the inbound uris for file based inbound types.
     * @throws IOException
     */
    void createData() throws IOException
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
}
