package org.grouter.core.readers;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.grouter.core.readers.AbstractFileReaderTests;
import org.grouter.core.command.FileWriteCommand;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandConsumerJob;
import org.quartz.JobExecutionContext;

/**
 * Basic test for FileReader. Verify that files are processed as should.
 *
 * @author Georges Polyzois
 */
public class FileReaderJobTest extends AbstractFileReaderTests
{
    private static Logger logger = Logger.getLogger(FileReaderJobTest.class);
    FileReaderJob fileReaderThread = new FileReaderJob();

    public void testReadInboundCount() throws Exception
    {


        fileReaderThread.setNode( fileToFileNode );
        fileReaderThread.setQueue( blockingQueue );
        List items = fileReaderThread.readFromSource();

        // we should have 5 commmands
        assertEquals(7, items.size());

        // and all fiels should have been processed
        assertEquals(0, (new File(fileToFileNode.getInBound().getUri())).list().length);

        // all messages should be in the internalq folder
//        assertEquals(7, (new File(fileToFileNode.getInternalQueueUri())).list().length);


        //CommandConsumerJob commandConsumerJob = new CommandConsumerJob( blockingQueue );

        AbstractCommand fileWriteCommand = new FileWriteCommand( fileToFileNode );

        fileWriteCommand.execute();


//  Hold file structure and save database state
        setDoNotCleanup();
        setComplete();
    }


}
