package org.grouter.core.readers;


import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.CommandFactory;
import org.grouter.core.command.Message;
import org.grouter.core.config.NodeConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;


public abstract class AbstractReader //extends TimerTask
{
    private static Logger logger = Logger.getLogger(AbstractReader.class);
    Command command;

    /**
     * Method overridden by subclasses.
     */
    abstract Message[] readFromSource();

    
    abstract void sendToConsumer();

    final protected void read(NodeConfig nodeConfig)
    {
        Message[] arrMessages = readFromSource();
        if (arrMessages != null && arrMessages.length > 0)
        {
            //if(nodeConfig.get)
            /*if(nodeConfig.isTransform())
            {
                transform(arrMessages);
            }
            if(nodeConfig.isBackup())
            {
                backup(arrMessages);
            } */
            command.setMessage(arrMessages);
            sendToConsumer();
        }
    }

    private void transform(Message[] arrMessages)
    {
        logger.debug("transforming...");
    }

    protected void backup(Message[] arrMessages)
    {
        logger.debug("doing backup...");
    }

    protected Command getCommand(final NodeConfig nodeConfig)
    {
        return CommandFactory.getCommand(nodeConfig);
    }

    //SHOULD THIS BE HERE...
    protected String getMessageAsString(File file)
    {
        java.io.FileReader filereader = null;
        String returnval = null;
        BufferedReader inputBuffer = null;
        try
        {
            filereader = new java.io.FileReader(file);
            inputBuffer = new BufferedReader(filereader);
            StringBuffer buffer = new StringBuffer();
            String line = inputBuffer.readLine();
            /*      if (skipfirstblankline)
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
