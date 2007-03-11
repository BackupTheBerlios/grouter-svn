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
 * @author Georges Polyzois
 */
abstract class AbstractGrouterTests extends TestCase
{
    private static Logger logger = Logger.getLogger(AbstractGrouterTests.class);

    private static final String BASE_FOLDER = System.getProperty("java.io.tmpdir") ;
    Router router = new Router("grouter_id");

    public void setUp() throws Exception
    {
        super.setUp();


        doSetup();

        createRouterConfig();
    }

    public void tearDown() throws Exception
    {
    }


    abstract void doSetup();

    void createRouterConfig() throws IOException
    {
        Node fileToFile = new Node("fileToFileId", "fileToFile");
        EndPoint inbound = new EndPointFileReader();
        inbound.setUri(BASE_FOLDER + router.getId() + "/in");
        inbound.setEndPointType( EndPointType.FILE_READER );

        EndPoint outbound = new EndPointFileWriter(  );
        outbound.setEndPointType(EndPointType.FILE_WRITER);
        outbound.setUri(BASE_FOLDER + router.getId() + "/out");
        outbound.setEndPointType( EndPointType.FILE_WRITER );

        fileToFile.setInBound( inbound );
        fileToFile.setOutBound( outbound );




        Set<Node> nodes = new HashSet<Node>();
        nodes.add(fileToFile);
        router.setNodes( nodes );
    }
}
