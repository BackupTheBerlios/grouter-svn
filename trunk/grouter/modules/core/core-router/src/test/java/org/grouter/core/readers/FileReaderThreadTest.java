package org.grouter.core.readers;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointFileReader;
import org.grouter.core.command.AbstractCommandWriter;

/**
 * Basic test for Reader.
 *
 * @author Georges Polyzois
 */
public class FileReaderThreadTest extends AbstractReaderTests
{
    private static Logger logger = Logger.getLogger(FileReaderThreadTest.class);
    FileReaderThread fileReaderThread;


    @Override
    public void doSetup()
    {
        fileReaderThread = new FileReaderThread(inbound, blockingQueue, outbound);
    }


    public void testReadInbound()
    {
        fileReaderThread.execute();
        assertEquals(0, (new File(inbound.getUri())).list().length);
    }


    public void testReadInboundCount()
    {
        List items = fileReaderThread.readFromSource();
        assertEquals(5, items.size());
    }


    @Override
    String getNodeId()
    {
        return this.getClass().getName();
    }
}
