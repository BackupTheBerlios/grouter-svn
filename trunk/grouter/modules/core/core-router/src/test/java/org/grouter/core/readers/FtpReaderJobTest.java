package org.grouter.core.readers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.grouter.core.command.CommandHolder;

/**
 * Basic test for Reader.
 *
 * @author Georges Polyzois
 */
public class FtpReaderJobTest extends AbstractFtpReaderTests
{
    private static Logger logger = Logger.getLogger(FileReaderJobTest.class);
    FtpReaderJob ftpReader = new FtpReaderJob();

    public void testGetValidfile() throws IOException, InterruptedException
    {
        File file = new File(FTP_ROOT_DIR, "test.txt");
        FileUtils.writeStringToFile(  file, "ftp test data");
        assertTrue(file.exists());

        ftpReader.setNode( ftpToFile );
        ftpReader.setQueue( blockingQueue );
        setFileList( file.getName()  );


        List<CommandHolder> items = ftpReader.readFromSource();
        assertEquals( 1, items.size() );
        assertEquals( "ftp test data", items.get(0).getMessage() );

        // and all fiels should have been processed
        assertEquals(0, (new File(ftpToFile.getInBound().getUri())).list().length);
    }


    public void testGetValidfiles() throws IOException, InterruptedException
    {
        File file1 = new File(FTP_ROOT_DIR, "file1.txt");
        File file2 = new File(FTP_ROOT_DIR, "file2.txt");
        FileUtils.writeStringToFile(  file1, "ftp test data 1");
        FileUtils.writeStringToFile(  file2, "ftp test data 2");
        assertTrue(file1.exists());
        assertTrue(file2.exists());

        ftpReader.setNode( ftpToFile );
        ftpReader.setQueue( blockingQueue );

        setFileList( file1.getName() + ","  + file2.getName() );

        List<CommandHolder> items = ftpReader.readFromSource();
        assertEquals( 2, items.size() );

        // and all fiels should have been processed
        assertEquals(0, (new File(ftpToFile.getInBound().getUri())).list().length);
    }

    /**
     * Handle case when we have added a file to the file list but the actual remote file does not exist.
     * @throws IOException
     * @throws InterruptedException
     */
    public void testGetNonExisitngRemoteFile() throws IOException, InterruptedException
    {
        File file1 = new File(FTP_ROOT_DIR, "file1.txt");
        FileUtils.writeStringToFile(  file1, "ftp test data 1");
        assertTrue(file1.exists());

        ftpReader.setNode( ftpToFile );
        ftpReader.setQueue( blockingQueue );

        setFileList( file1.getName() + ","  + "nonexistingfile.txt" );

        List<CommandHolder> items = ftpReader.readFromSource();
        assertEquals( 1, items.size() );

        // and all files should have been processed
        assertEquals(0, (new File(ftpToFile.getInBound().getUri())).list().length);
    }

}