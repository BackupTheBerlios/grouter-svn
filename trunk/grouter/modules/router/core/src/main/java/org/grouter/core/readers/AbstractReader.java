package org.grouter.core.readers;



import org.grouter.core.command.Command;
import org.grouter.core.command.CommandFactory;
import org.grouter.core.command.Message;
import org.grouter.core.config.ServiceNodeConfig;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;


public abstract class AbstractReader //extends TimerTask
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(AbstractReader.class);
   // protected ServiceNodeConfig serviceNodeConfig;
    Command command;

    /** Method overridden by subclasses. */
    abstract Message[] readFromSource();

    abstract void sendToDestination();

    final protected void read(ServiceNodeConfig config)
    {
        Message[] arrMessages = readFromSource();
        if(arrMessages!=null && arrMessages.length>0)
        {
            if(config.isTransform())
            {
                transform(arrMessages);
            }
            if(config.isBackup())
            {
                backup(arrMessages);
            }
            command.setMessage(arrMessages);
            sendToDestination();
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

    protected Command getCommand(final ServiceNodeConfig theServiceNodeConfig)
    {
        return CommandFactory.getCommand(theServiceNodeConfig);
    }

    //SHOULD THIS BE HERE...
    protected String getMessageAsString(File file)
    {
        java.io.FileReader filereader = null;
        String returnval = null;
        BufferedReader inputBuffer =  null;
        try
        {
            filereader =  new java.io.FileReader(file);
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
                if ( filereader != null )
                    filereader.close();
            }
            catch (IOException e)
            {
                try
                {
                    if ( inputBuffer != null )
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
