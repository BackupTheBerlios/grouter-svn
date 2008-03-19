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

import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.NodeStatus;
import org.grouter.domain.service.spring.logging.LogStrategy;
import org.grouter.domain.service.BeanLocator;

import java.io.File;

/**
 * A concrete command to be performed by a consumer, held by the CommandInvoker. </br>
 * This class acts as a consumer in the Command pattern.
 *
 * @author Georges Polyzois
 */
public class FileWriteCommand extends AbstractCommand
{
    private static Logger logger = Logger.getLogger(FileWriteCommand.class);
    LogStrategy logStrategy;
    BeanLocator beanLocator;



    public FileWriteCommand()
    {

    }


    /**
     * Constructor.
     *
     * @param node the node holding outbound information
     * @throws IllegalArgumentException if node == null
     */
    public FileWriteCommand(Node node)
    {
        if (node == null)
        {
            throw new IllegalArgumentException("You must provide a Node !!");
        }
        this.node = node;


    }


    @Override
    public void transform()
    {
        // Todo implement this
        logger.debug("Do a transform !!!!!!!");
    }

    public void write()
    {
        logger.debug(node.getDisplayName() + " writing to uri : " + node.getOutBound().getUri());
        for (CommandMessage commandMessage : commandMessages)
        {
            logger.debug("Wrote a new file :" + commandMessage.getMessage());
            try
            {
                // copy to uri - if somehow the folder was deleted it will be recreated
                // validation is done at bootstrap - folder might be deleted during the lifetime of
                // the router - but we will then try creating that folder and copy the file
                // there anyway. If an exception is raised we log that.
                File outFile = new File( node.getOutBound().getUri() + File.separator + commandMessage.getInternalInFile().getName()  );
                File inFile = commandMessage.getInternalInFile();
                logger.debug( "Copying file from :" + outFile + " to :" +outFile );
                FileUtils.copyFile( inFile, outFile);
                //File internalOutFile = new File(node.getRouter().getHomePath() + File.separator + "nodes" + File.separator + node.getId() + File.separator + "internal" + File.separator + "out" + File.separator + commandMessage.getInternalInFile().getMessage() + "_" + GuidGenerator.getInstance().getGUID());

                // delete fron internal file - a backup was saved if configured to do so...
                commandMessage.getInternalInFile().delete();
            }
            catch (Exception e)
            {
                logErroMessage("Could not write to " + node.getOutBound().getUri() + " Error:" + e.getMessage());
                logger.error(e, e);
            }
        }
    }


    public void logInfoMessage()
    {
        logStrategy = beanLocator.getLogStrategy(BeanLocator.LOGSTRATEGY_BEAN);
        for (CommandMessage commandMessage : commandMessages)
        {
            Message message = new Message();
            message.setContent(commandMessage.getMessage());
            message.setNode(node);
            logStrategy.log(message);
        }
    }

    void logErroMessage(String errorMessage)
    {
        logStrategy = beanLocator.getLogStrategy(BeanLocator.LOGSTRATEGY_BEAN);
        node.setNodeStatus(NodeStatus.ERROR);
        node.setStatusMessage( errorMessage );
        logStrategy.log(node);
    }


    /**
     * Injected.
     *
     * @param beanLocator the servicefactory
     */
    public void setServiceFactory(BeanLocator beanLocator)
    {
        this.beanLocator = beanLocator;
    }
}
