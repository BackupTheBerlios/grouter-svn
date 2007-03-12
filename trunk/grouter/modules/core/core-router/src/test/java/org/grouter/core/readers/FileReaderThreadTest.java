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
import org.grouter.domain.entities.Node;
import org.grouter.core.command.AbstractCommandWriter;
import org.grouter.core.AbstractGrouterTests;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 * Basic test for Reader.
 *
 * @author Georges Polyzois
 */
public class FileReaderThreadTest extends AbstractGrouterTests
{
    private static Logger logger = Logger.getLogger(FileReaderThreadTest.class);
    FileReaderThread fileReaderThread = new FileReaderThread();

    public void testReadInboundCount() throws Exception
    {

        fileReaderThread.setNode( fileToFileNode );
        fileReaderThread.setQueue( blockingQueue );
        List items = fileReaderThread.readFromSource();
        assertEquals(5, items.size());

        assertEquals(0, (new File(fileToFileNode.getInBound().getUri())).list().length);

//        setComplete();
    }


}
