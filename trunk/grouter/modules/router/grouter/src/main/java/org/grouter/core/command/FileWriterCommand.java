package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.core.config.FileReaderConfig;
import org.grouter.core.config.FileWriterConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * A concrete command to be performed by a writer, held by the 
 * CommandInvoker.
 *
 */
public class FileWriterCommand extends Command
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(FileWriterCommand.class);
    FileReaderConfig fileReaderConfig;
    /** Writer. */
    FileWriter writer;
    File toDir;


    public FileWriterCommand(FileWriterConfig fileReaderConfig)
    {
        toDir = fileReaderConfig.getToDir();
    }

    /**
     */
    public void execute()
    {

        logger.debug("Writing file to dir : " + toDir);

        for (int i = 0; i < message.length; i++)
        {
            logger.debug(message[i].getMessage());
            try
            {
                String fileName = toDir.getPath() + "/fil.txt";
                writer = new FileWriter(fileName);
                writer.write(message[i].getMessage());
                writer.flush();
            }
            catch(Exception e)
            {
                logger.error(e,e);
            }
            finally
            {
                try
                {
                    writer.close();
                } catch (IOException e)
                {
                    //ignore
                }
            }

        }


        //writer.write();
        //writer.readSendFiles(null,null);
        //writer.backupFiles(null);

        //writer.backup();
        //etc
    }
}
