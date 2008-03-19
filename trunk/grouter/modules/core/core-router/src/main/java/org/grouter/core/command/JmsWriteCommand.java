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
import org.grouter.domain.service.spring.logging.JDBCLogStrategyImpl;
import org.grouter.domain.service.spring.logging.LogStrategy;
import org.grouter.domain.service.BeanLocator;
import org.grouter.common.guid.GuidGenerator;

import java.io.File;

/**
 * A concrete command to be performed by a consumer, held by the CommandInvoker. </br>
 * This class acts as a consumer in the Command pattern.
 *
 * @author Georges Polyzois
 */
public class JmsWriteCommand extends AbstractCommand
{
    private static Logger logger = Logger.getLogger(JmsWriteCommand.class);
    LogStrategy logStrategy;
    BeanLocator beanLocator;


    public JmsWriteCommand()
    {
    }


    /**
     * Constructor.
     *
     * @param node
     * @throws IllegalArgumentException if node == null
     */
    public JmsWriteCommand(Node node)
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
        // Todo
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
                FileUtils.copyFile(commandMessage.getInternalInFile(), new File(node.getOutBound().getUri() + File.separator + commandMessage.getInternalInFile().getName()));
                File internalOutFile = new File(node.getRouter().getHomePath() + File.separator + "nodes" + File.separator + node.getId() + File.separator + "internal" + File.separator + "out" + File.separator + commandMessage.getInternalInFile().getName() + "_" + GuidGenerator.getInstance().getGUID());
                FileUtils.copyFile(commandMessage.getInternalInFile(), internalOutFile);
                commandMessage.getInternalInFile().delete();
            }
            catch (Exception e)
            {
                logger.error(e, e);
            }
        }
    }


    public void logInfoMessage()
    {
        for (CommandMessage commandMessage : commandMessages)
        {
            Message message = new Message();
            message.setContent(commandMessage.getMessage());
            message.setNode(node);
            LogStrategy jdbcLogStrategy = (JDBCLogStrategyImpl) beanLocator.getLogStrategy(BeanLocator.LOGSTRATEGY_BEAN);
            jdbcLogStrategy.log(message);
        }
    }

    void logErroMessage(String errorMessage)
    {
        node.setNodeStatus(NodeStatus.ERROR);
        node.setStatusMessage(errorMessage);
        logStrategy.log(node);
    }


    /**
     * Injected.
     *
     * @param beanLocator
     */
    public void setServiceFactory(BeanLocator beanLocator)
    {
        this.beanLocator = beanLocator;
    }
}