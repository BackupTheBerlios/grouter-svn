package org.grouter.core.readers;

import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointFileReader;
import org.grouter.domain.entities.EndPointFileWriter;
import org.grouter.domain.entities.EndPointType;
import org.grouter.core.command.AbstractCommandWriter;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Helper for setting up tests - creates directories and data for uni tests.
 * @author Georges Polyzois
 */
abstract class AbstractReaderTests extends TestCase
{
    private static Logger logger = Logger.getLogger(AbstractReaderTests.class);
    EndPoint outbound = new EndPointFileWriter();

    EndPoint inbound = new EndPointFileReader();
    BlockingQueue<AbstractCommandWriter> blockingQueue = new ArrayBlockingQueue<AbstractCommandWriter>(10);
    private static final String BASE_FOLDER_FOR_TEST = System.getProperty("java.io.tmpdir") + "/grouter/";


    public void setUp() throws Exception
    {
        super.setUp();

        //tmpdir = new File(BASE_FOLDER_FOR_TEST);
        //tmpdir.mkdirs();
        //logger.debug("Created dir : " + tmpdir.getPath() );

        inbound.setUri( BASE_FOLDER_FOR_TEST  + getNodeId() + "/in");
        inbound.setId(1L);
        FileUtils.forceMkdir(new File(inbound.getUri()));
        FileUtils.forceMkdir(new File(inbound.getUri() + "/emptyfolder"));

        logger.debug("Created dir : " + inbound.getUri() );

        outbound.setEndPointType( EndPointType.FILE_WRITER );
        outbound.setUri( BASE_FOLDER_FOR_TEST + getNodeId() + "/out");
        outbound.setId(1L);
        FileUtils.forceMkdir(new File(outbound.getUri()));
        logger.debug("Created dir : " + outbound.getUri() );





        doSetup();

        createData();
    }

    public void tearDown() throws Exception
    {
        FileUtils.forceDelete( new File( BASE_FOLDER_FOR_TEST  ) );
    }


    /**
     * Override this and prvide a node id to use for builidng oup the path.
     * @return
     */
    abstract String getNodeId();


    abstract void doSetup();

    void createData() throws IOException
    {
        FileUtils.writeStringToFile(new File(inbound.getUri() + "/testdata1.txt"), "test data", "UTF-8");
        FileUtils.writeStringToFile(new File(inbound.getUri() + "/testdata2.txt"), "test data", "UTF-8");
        FileUtils.writeStringToFile(new File(inbound.getUri() + "/testdata3.txt"), "test data", "UTF-8");
        FileUtils.writeStringToFile(new File(inbound.getUri() + "/testdata4.txt"), "test data", "UTF-8");
        FileUtils.writeStringToFile(new File(inbound.getUri() + "/testdata5.txt"), "test data", "UTF-8");
    }
}
