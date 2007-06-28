package org.grouter.core.readers;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.grouter.domain.entities.*;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.AbstractRouterTests;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.IOException;

/**
 *
 *
 * @author Georges Polyzois
 */
public abstract class AbstractFileReaderTests extends AbstractRouterTests
{
    private static Logger logger = Logger.getLogger(AbstractRouterTests.class);
    public BlockingQueue<AbstractCommand> blockingQueue = new ArrayBlockingQueue<AbstractCommand>(10);
    public Node fileToFileNode;


    /**
     * Do a setup for every test so we have a clean router for every run. onSetup is final and
     * Spring provides us with this method to hook us into.
     * @throws Exception
     */
    @Override
    public void onSetUpBeforeTransaction() throws Exception
    {
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

            FileUtils.forceMkdir(new File( node.getRouter().getHomePath() + "/nodes/" + node.getId() + "/internal/in" ));
            logger.debug("Created dir : " + node.getRouter().getHomePath() + "/nodes/" + node.getId() + "/internal/in" );

            FileUtils.forceMkdir(new File( node.getRouter().getHomePath() + "/nodes/" + node.getId() + "/internal/out" ));
            logger.debug("Created dir : " + node.getRouter().getHomePath() + "/nodes/" + node.getId() + "/internal/out" );
        }
    }

    @Override
    public void createRouter() 
    {
        createNode();

        Set<Node> nodes = new HashSet<Node>();
        nodes.add(fileToFileNode);
        router.setNodes( nodes );
    }


    /**
     * Create the entity and and set realtionships.
     */
    public void createNode()
    {
        fileToFileNode = new Node("id_ftpToFileNode", "ftpToFileNode");
        fileToFileNode.setBackupUri( router.getHomePath() + "/nodes/" + fileToFileNode.getId() + "/internal/backup" );
//        fileToFileNode.setInternalQueueUri( BASE_FOLDER_FOR_TEST + File.separator + fileToFileNode.getName() + "/internalq" );

        EndPoint inbound = new EndPoint();
        inbound.setUri(router.getHomePath() + "/nodes/" + fileToFileNode.getId() + "/in");
        inbound.setEndPointType( EndPointType.FILE_READER );
        inbound.setScheduleCron( "0/5 * * * * ?" );
        inbound.setId( "1" );

        EndPoint outbound = new EndPoint(  );
        outbound.setEndPointType(EndPointType.FILE_WRITER);
        outbound.setUri(router.getHomePath() + "/nodes/"+ fileToFileNode.getId() + "/out");
        outbound.setEndPointType( EndPointType.FILE_WRITER );
        outbound.setScheduleCron( "0/5 * * * * ?" );
        outbound.setId( "2" );

        fileToFileNode.setInBound( inbound );
        fileToFileNode.setOutBound( outbound );

        fileToFileNode.setRouter( router );

    }

    /**
     * Create messages in the inbound uris for file based inbound types.
     * @throws java.io.IOException
     */
    public void createData() throws IOException
    {
        for (Node node : router.getNodes())
        {
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata1.txt"), "test data 1", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata2.txt"), "test data 2", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata3.txt"), "test data 3", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata4.txt"), "test data 4", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata5.txt"), "test data 5", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata6.txt"), "test data 6", "UTF-8");
            FileUtils.writeStringToFile(new File(node.getInBound().getUri() + "/testdata7.txt"), "test data 7", "UTF-8");
        }
    }

}