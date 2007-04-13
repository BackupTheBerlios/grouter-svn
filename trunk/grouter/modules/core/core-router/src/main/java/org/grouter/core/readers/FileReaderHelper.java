package org.grouter.core.readers;

import org.grouter.core.command.CommandHolder;
import org.grouter.domain.entities.Node;
import org.grouter.common.guid.GuidGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

/**
 * @author Georges
 */
public class FileReaderHelper
{
    private static final int MESSAGE_LENGTH = 100;

    private static Logger logger = Logger.getLogger(FileReaderHelper.class);

    public static List<CommandHolder> getCommands( Node node )
    {
        File inFolder = new File(node.getInBound().getUri());
        File[] curFiles = inFolder.listFiles();  // TODO refactor to use generic Filter

        if (curFiles == null || curFiles.length == 0)
        {
            logger.debug("No files found - empty : " + node.getInBound().getUri());
            return null;
        }
        logger.debug("Found number of files and folders : " + curFiles.length);
        List<CommandHolder> commandMessages = new ArrayList<CommandHolder>(curFiles.length);
        for (File curFile : curFiles)
        {
            if (curFile.isDirectory())
            {
                try
                {
                    FileUtils.forceDelete( curFile );
                } catch (IOException e)
                {
                    if( logger.isDebugEnabled() )
                    {
                        logger.debug("Could not delete folder", e);
                    }
                }
            } else
            {
                try
                {
                    String message = getMessage(curFile);
                    CommandHolder cmdHolder = new CommandHolder(message, node.getInternalQueueUri() );
                    File internalqFile = new File( cmdHolder.getFileUriToMessage() );
                    FileUtils.copyFile( curFile,  internalqFile );
                    commandMessages.add(cmdHolder);
                    // remove file from infolder
                    curFile.delete();
                }
                catch (Exception ex)
                {
                    logger.info(ex, ex);
                }
            }
        }
        return commandMessages;
    }


    /**
     * Get message content for current file but only up to MESSAGE_LENGTH
     *
     * @param currentFile the current file beeing processed
     * @return message content for this file
     * @throws IOException
     */
    private static String getMessage(File currentFile)
            throws IOException
    {
        String message = FileUtils.readFileToString(new File(currentFile.getPath()), "UTF-8");
        if( message.length() > MESSAGE_LENGTH )
        {
            return message.substring(MESSAGE_LENGTH);
        }
        return message;
    }


}
