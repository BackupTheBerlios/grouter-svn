package org.grouter.core.readers;


import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.CommandFactory;
import org.grouter.core.command.CommandMessage;
import org.grouter.core.config.NodeConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;


public abstract class AbstractReader //extends TimerTask
{
    private static Logger logger = Logger.getLogger(AbstractReader.class);
    Command command;
    NodeConfig nodeConfig;

    /**
     * Method overridden by subclasses.
     */
    abstract CommandMessage[] readFromSource();


    /**
     * Method overridden by subclass.
     */
    abstract void sendToConsumer();

    /**
     * Template method pattern.
     */
    final protected void read()
    {
        CommandMessage[] arrCommandMessages = readFromSource();
        if (arrCommandMessages != null && arrCommandMessages.length > 0)
        {
            command.setMessages(arrCommandMessages);
            sendToConsumer();
        }
    }

    protected Command getCommand(final NodeConfig nodeConfig)
    {
        return CommandFactory.getCommand(nodeConfig);
    }

    /**
     * Copy contents of a file to a string.
     * @param file
     * @return
     */
    protected String readFileToString(File file)
    {
        // this method from common-io could do this but without
        // the the options of skipping a line and other potential operations
        // we might wanna do - FileUtils.readFileToString(file)
        java.io.FileReader filereader = null;
        String returnval = null;
        BufferedReader inputBuffer = null;
        try
        {
            filereader = new java.io.FileReader(file);
            inputBuffer = new BufferedReader(filereader);
            StringBuffer buffer = new StringBuffer();
            String line = inputBuffer.readLine();
            /*
            if (skipfirstblankline)
            {
                    line = inputBuffer.readLine();
            }
            */
            while (line != null)
            {
                buffer.append(line);
                buffer.append("\n");
                line = inputBuffer.readLine();
            }
            returnval = buffer.toString();
            filereader.close();
        }
        catch (Exception pce)
        {
            logger.warn("File could not be read, probably the process copying files to the infolder is to slow.");
        }
        finally
        {
            try
            {
                if (filereader != null)
                    filereader.close();
            }
            catch (IOException e)
            {
                try
                {
                    if (inputBuffer != null)
                        inputBuffer.close();
                }
                catch (IOException e1)
                {
                    //ignore
                }
            }
        }
        return returnval;
    }
}
