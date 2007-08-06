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
import org.apache.commons.lang.Validate;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.Node;


/**
 * A factory for creating commands based on a Node's {@link org.grouter.domain.entities.EndPointType}.
 *
 * @author Georges Polyzois
 */
public class CommandFactory
{
    private static Logger logger = Logger.getLogger(CommandFactory.class);

    /**
     * Given a node create a command for this node. The command will act as a consumer and handle
     * outbound messaging.
     *
     * @param node a {@link Node}
     * @return an AbstractCommand or null if and {@link EndPointType } was not recognized
     */
    public static AbstractCommand getCommand(Node node)
    {
        // Guard this factory 
        Validate.notNull(node, "Can not handle a null Node");
        Validate.notNull(node.getOutBound(), "Can not handle a null EndPoint");
        Validate.notNull(node.getOutBound().getEndPointType(), "Can not handle a null EndPointType");
        Validate.isTrue( EndPointType.values.containsKey( node.getOutBound().getEndPointType().getId() ) );

        AbstractCommand resultCommand = null;
        if (node.getOutBound().getEndPointType().getId() == EndPointType.FILE_WRITER.getId())
        {
            resultCommand = new FileWriteCommand(node);
        }
        else if (node.getOutBound().getEndPointType().getId() == EndPointType.FTP_WRITER.getId())
        {
            resultCommand = new FtpWriteCommand(node);
        }
        else if (node.getOutBound().getEndPointType().getId() == EndPointType.JMS_WRITER.getId())
        {
            resultCommand = new JmsWriteCommand(node);
        }
        if ( resultCommand == null)
        {
            logger.warn( "A command writer for this endpoint type is not implemented" );
        }

        return resultCommand;
    }
    
}
