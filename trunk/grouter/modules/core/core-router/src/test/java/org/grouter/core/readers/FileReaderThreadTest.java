package org.grouter.core.readers;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.grouter.core.AbstractGrouterTests;

/**
 * Basic test for Reader.
 *
 * @author Georges Polyzois
 */
public class FileReaderThreadTest extends AbstractGrouterTests
{
    private static Logger logger = Logger.getLogger(FileReaderThreadTest.class);
    FileReaderJob fileReaderThread = new FileReaderJob();

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
