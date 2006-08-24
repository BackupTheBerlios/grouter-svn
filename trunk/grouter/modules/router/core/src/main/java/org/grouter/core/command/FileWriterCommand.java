package org.grouter.core.command;

import org.apache.log4j.Logger;
import org.grouter.core.config.FileReader;
import org.grouter.core.config.FileWriter;
import org.grouter.core.config.Node;

import java.io.IOException;
import java.io.File;

/**
 * A concrete command to be performed by a writer, held by the CommandInvoker. </br>
 * This class acts as a receiver in the Command pattern.
 *
 */
public class FileWriterCommand extends Command
{
    private static Logger logger = Logger.getLogger(FileWriterCommand.class);
    FileReader fileReaderConfig;
    /** Writer. */
    java.io.FileWriter writer;
    File toDir;
    Node node;


    public FileWriterCommand(Node node)
    {
        toDir = new File( node.getOutFolder().getOutFolderPath() );
    }

    /**
     * Overridden.
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
                writer = new java.io.FileWriter(fileName);
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
