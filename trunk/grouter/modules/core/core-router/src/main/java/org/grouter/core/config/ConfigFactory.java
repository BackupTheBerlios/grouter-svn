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

package org.grouter.core.config;

import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.grouter.domain.entities.*;
import org.grouter.config.GrouterDocument;
import org.grouter.config.Context;
import org.grouter.core.util.file.FileUtils;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.io.File;

/**
 * Use this factory (or assemblee) to create Node entities from corresponding xml binding type of object.
 * NodeType.
 * <p/>
 * This factory will also do some basic validation of configuration parameters - like valid paths etc.
 *
 * @author Georges Polyzois
 */
public class ConfigFactory
{
    private static Logger logger = Logger.getLogger(ConfigFactory.class);

    /**
     * Create a NodeConfig config object from a NodeType object(xml binding representation
     * object).
     *
     * @param grouterconfig root element in configuration of the grouter
     * @return Router instance to use within the Grouter domain
     * @throws IllegalArgumentException if failure to validate configuration
     */
    public static Router createRouter(GrouterDocument.Grouter grouterconfig)
    {
        Validate.notNull(grouterconfig, "Global config section is null, or you are lacking a valid grouter home element in your config file!");

        Router router = new Router();
        router.setDisplayName(grouterconfig.getDisplayName());
        router.setDescription(grouterconfig.getDescription());
        router.setId(grouterconfig.getId());
        router.setRmiRegistryPort(grouterconfig.getRmiRegistryPort());
        router.setRmiServicePort(grouterconfig.getRmiServicePort());
        //router.setStartedOn( grouterconfig.getId() );


        String uri = grouterconfig.getHome();
        if (uri.startsWith("file://"))
        {
            uri = grouterconfig.getHome().replace("file://", "/");
        }
        if (!FileUtils.isValidDir(uri))
        {
            throw new IllegalArgumentException("Invalid uri path to a grouter home folder. File path should begin with file://  . Path was :" + uri);
        }

        router.setHomePath(uri);


        router.setNodes(getNodes(grouterconfig, router));

        //   logger.info("Router config : " + router);
        return router;
    }


    private static Set<Node> getNodes(GrouterDocument.Grouter grouterconfig, Router router) throws IllegalArgumentException
    {
        Set<Node> result = new HashSet<Node>();

        org.grouter.config.Node[] nodes = grouterconfig.getNodeArray();

        for (org.grouter.config.Node configNode : nodes)
        {
            Node nodeEntity = new Node();
            nodeEntity.setDisplayName(configNode.getDisplayName());
            nodeEntity.setId(configNode.getId().getStringValue());
            nodeEntity.setReceiverStatic(configNode.getReceiverStatic());
            nodeEntity.setSenderStatic(configNode.getSenderStatic());

            if (configNode.isSetBackup())
            {
                // we need to handle backups
                if (configNode.getBackup().getOverrideDefaultUri() != null &&
                        !configNode.getBackup().getOverrideDefaultUri().equalsIgnoreCase(""))
                {
                    String uriValid = configNode.getBackup().getOverrideDefaultUri().replace("file://", "/");
                    nodeEntity.setBackupUri( uriValid );
                }
                else
                {
                    String uriValid = (router.getHomePath() + File.separator + "nodes" + File.separator +
                            configNode.getId().getStringValue() + File.separator + "backup").replace("file://", "/");
                    nodeEntity.setBackupUri( uriValid );
                }
                if (!FileUtils.isValidDir(nodeEntity.getBackupUri()))
                {
                    throw new IllegalArgumentException("Invalid uri path to backup folder specified. Path was :" + nodeEntity.getBackupUri());
                }

                logger.debug("Using backup path set to :" + nodeEntity.getBackupUri());
            }

            EndPoint inbound = getEndPoint(configNode.getInBoundEndPoint());
            nodeEntity.setInBound(inbound);

            EndPoint outbound = getEndPoint(configNode.getOutBoundEndPoint());
            nodeEntity.setOutBound(outbound);


            nodeEntity.setRouter(router);

            result.add(nodeEntity);
        }
        return result;
    }


    /**
     * Create an EndPoint from an EndPoint configuration instance.
     *
     * @param endPoint a configuration {@link org.grouter.config.EndPoint}
     * @return an domain {@link EndPoint}
     * @throws IllegalArgumentException if non valid config parameters are present
     */
    private static EndPoint getEndPoint(org.grouter.config.EndPoint endPoint) throws IllegalArgumentException
    {
        EndPoint result = new EndPoint();
        boolean supportedEndpointtypeFound = false;

        String uriValid = null;
        if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FILE_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FILE_WRITER))
        {
            uriValid = endPoint.getUri().replace("file://", "/");
            if (!FileUtils.isValidDir(uriValid))
            {
                throw new IllegalArgumentException("Invalid path to a file type of endpoint. Make sure the path exists. Path was :" + uriValid);
            }
            supportedEndpointtypeFound = true;
        }

        if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FTP_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FTP_WRITER))
        {
            String urlpath = endPoint.getUri();
            urlpath = urlpath.replace("ftp://", "");
            uriValid = urlpath.replaceAll("/", "");
            endPoint.setUri(uriValid);
            supportedEndpointtypeFound = true;
        }

        if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.JMS_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.JMS_WRITER))
        {
            uriValid = endPoint.getUri();
            supportedEndpointtypeFound = true;
        }

        if ( !supportedEndpointtypeFound )
        {
            throw new IllegalArgumentException("Unsupported endpoint type. Configuration showed :" +
                    endPoint.getEndPointType() + " Add support in ConfigFactory and EndPointType for this EndPointType.");
        }

        Context[] context = endPoint.getContextparamArray();
        Map<String,String> endPointContextMap = new HashMap<String,String>();
        if (context != null && context.length > 0)
        {
            for (Context context1 : context)
            {
                //EndPointContext endPointContext = new EndPointContext(context1.getDisplayName(), context1.getValue(), result);
                endPointContextMap.put(context1.getName(), context1.getValue());
            }
        }

        result.setClazzName(endPoint.getClazzname());
        result.setUri(uriValid);
        result.setId(endPoint.getId());
        result.setScheduleCron(endPoint.getCron());
        result.setEndPointContext(endPointContextMap);

        EndPointType type = EndPointType.valueOf(new Long(endPoint.getEndPointType().intValue()));
        result.setEndPointType(type);
        return result;
    }
}
