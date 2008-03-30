/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.core.command;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.grouter.domain.entities.Node;
import org.grouter.common.guid.GuidGenerator;

import java.util.List;
import java.io.File;

/**
 * Interface for commands.
 * <p/>
 * Commands are object put on a blocking queue from a produces/publisher and handed over to a consumer/writer.
 * A command contains type of command and the message the writer side needs.
 *
 *
 * @author Georges Polyzois
 */
public abstract class AbstractCommand
{
    private static Logger logger = Logger.getLogger(AbstractCommand.class);
    protected List<CommandMessage> commandMessages;
    Node node;



    /**
     * Commands must override this method to provide an implementation of an execute command
     * in the context for that command.
     * We are trying to enforce a template method pattern on top of this to ensure that we
     * handle backingup, transforming etc.
     */
    public void execute()
    {
        // Not doing any validation  - this is taken care of by the ConfigFactory at bootstrap
        backup();
        transform();
        write();
        logInfoMessage();
    }



    /**
     * Do a transform of the message if applicable.
     */
    abstract void transform();

    /**
     * Write the message to a destination - file etc.
     */
    abstract void write();

    /**
     * If configured commandMessages are backuped.
     */
    public void backup()
    {
        // check if we should backup message to file
        if (!(node.getBackupUri() != null && node.getBackupUri().equalsIgnoreCase("")))
        {
            logger.debug(node.getDisplayName() + " backup to uri : " + node.getBackupUri());
            for (CommandMessage commandMessage : commandMessages)
            {
                try
                {
                    FileUtils.writeStringToFile(getBackupFile(commandMessage), commandMessage.getMessage(), commandMessage.getEncoding());
                }
                catch (Exception e)
                {
                    logger.error(e, e);
                }
            }
        }
    }

    /**
     * Log that we have sent a message - typically this should be to a JMS destination.
     */
    abstract void logInfoMessage();

    /**
     * Log when some exception is caught while trying o write to an endpoint. The status of the
     * Node will be set to error.
     *
     * @param errorMessage the message to log on a Node instance if some failure ooccurs
     */
    abstract void logErroMessage( String errorMessage );



    /**
     * Use reflection to pull out all attributs and values.
     *
     * @return a String representing the state of this Command, using reflection
     */
    public String toStringUsingReflection()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public void setMessages(List<CommandMessage> arrCommandMessages)
    {
        this.commandMessages = arrCommandMessages;
    }

    /**
     * Fromt the
     * @param commandMessage the message we are processing as a command consumer
     * @return a File in which we are storing the backup
     */
    File getBackupFile( CommandMessage commandMessage )
    {
        String uri = node.getBackupUri() + File.separator + commandMessage.getInternalInFile().getName() + "___" + GuidGenerator.getInstance().getGUID() ;
        return new File( uri );
    }

}
