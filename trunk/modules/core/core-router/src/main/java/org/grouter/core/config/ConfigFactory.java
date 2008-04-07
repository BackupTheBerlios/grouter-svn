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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.grouter.config.Context;
import org.grouter.config.GrouterDocument;
import org.grouter.config.NodeType;
import org.grouter.config.NodesType;
import org.grouter.core.util.file.FileUtils;
import org.grouter.domain.entities.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
    private static final String FILE_PREFIX = "file://";

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
        String uri = grouterconfig.getHome();
        if (uri.startsWith(FILE_PREFIX))
        {
            uri = grouterconfig.getHome().replace(FILE_PREFIX, "/");
        }
        if (!FileUtils.isValidDir(uri))
        {
            throw new IllegalArgumentException("Homepath of Grouter did not exist");
        }

        router.setHomePath(uri);
        router.setSettings(getSettingsFromConfig(grouterconfig, router));
        router.setNodes(getNodes(grouterconfig, router));
        return router;
    }

    /**
     * Extract settings from xml representation to domain.
     *
     * @param grouterconfig the xml representtation of a grouter configuration
     * @param router        the domain entity
     * @return domain entity represetnting settings
     */
    private static Settings getSettingsFromConfig(final GrouterDocument.Grouter grouterconfig,
                                                  final Router router)
    {
        Map<String, String> settingsContext = new HashMap<String, String>();
        settingsContext.put(SettingsContext.KEY_SETTINGS_JNDI_JAVA_NAMING_FACTORY_INITIAL, grouterconfig.getSettings().getJndi().getNamingFactoryInitial());
        settingsContext.put(SettingsContext.KEY_SETTINGS_JNDI_JAVA_NAMING_FACTORY_URL, grouterconfig.getSettings().getJndi().getNamingFactoryUrlPkgs());
        settingsContext.put(SettingsContext.KEY_SETTINGS_JNDI_JAVA_NAMING_PROVIDER_URL, grouterconfig.getSettings().getJndi().getNamingProviderUrl());
        settingsContext.put(SettingsContext.KEY_SETTINGS_JNDI_QUEUECONNECTIONFACTORY, grouterconfig.getSettings().getJndi().getNamingQueueconnectionfactoy());
        settingsContext.put(SettingsContext.KEY_SETTINGS_LOGGING_JMSLOGGINGQUEUE, grouterconfig.getSettings().getLogging().getJmsLoggingQueue());

        if (grouterconfig.getSettings().getDataSource() != null)
        {
            logger.info("Datasource was set in config");
            settingsContext.put(SettingsContext.KEY_SETTINGS_DATASOURCE_DIALECT, grouterconfig.getSettings().getDataSource().getDialect());
            settingsContext.put(SettingsContext.KEY_SETTINGS_DATASOURCE_DRIVERCLASSNAME, grouterconfig.getSettings().getDataSource().getDriverClassName());
            settingsContext.put(SettingsContext.KEY_SETTINGS_DATASOURCE_URL, grouterconfig.getSettings().getDataSource().getUrl());
            settingsContext.put(SettingsContext.KEY_SETTINGS_DATASOURCE_USERNAME, grouterconfig.getSettings().getDataSource().getUserName());
            settingsContext.put(SettingsContext.KEY_SETTINGS_DATASOURCE_PASSWORD, grouterconfig.getSettings().getDataSource().getPassword());
        }
        // return new Settings(router.getId(), settingsContext);
        return new Settings(settingsContext);
    }


    private static Set<Node> getNodes(final GrouterDocument.Grouter grouterconfig,
                                      final Router router) throws IllegalArgumentException
    {
        Set<Node> result = new HashSet<Node>();
        NodesType nodes = grouterconfig.getNodes();
        NodeType[] nodeArr = nodes.getNodeArray();

        for (NodeType configNode : nodeArr)
        {
            final Node nodeEntity = new Node();

            AuditInfo auditInfo = new AuditInfo();
            auditInfo.setCreatedBy(User.ADMIN);
            auditInfo.setModifiedBy(User.ADMIN);
            auditInfo.setCreatedOn(new Date());
            auditInfo.setModifiedOn(new Date());
            nodeEntity.setAuditInfo(auditInfo);

            nodeEntity.setDisplayName(configNode.getDisplayName());
            nodeEntity.setId(configNode.getId().getStringValue());
            nodeEntity.setReceiver(configNode.getReceiver());
            nodeEntity.setSource(configNode.getSource());
            nodeEntity.setCreateDirectories(configNode.getCreateDirectories());

            // Do we have a backup element - if so we need to handle backups
            if (configNode.isSetBackup())
            {
                // we need to handle backups. Check if we should override default backup location
                if (!StringUtils.isEmpty(configNode.getBackup().getOverrideDefaultUri()))
                {
                    // Use override path
                    String uriValid = configNode.getBackup().getOverrideDefaultUri().replace(FILE_PREFIX, "/");
                    nodeEntity.setBackupUri(uriValid);
                } else
                {
                    // create in node home folder
                    String uriDefaultValidPath = (router.getHomePath() + File.separator + "nodes" + File.separator +
                            configNode.getId().getStringValue() + File.separator + "backup").replace(FILE_PREFIX, "/");
                    nodeEntity.setBackupUri(uriDefaultValidPath);
                }

                if (!FileUtils.isValidDir(nodeEntity.getBackupUri()) && !nodeEntity.getCreateDirectories())
                {
                    throw new IllegalArgumentException("CreateDirectory flag for node was false and path did not " +
                            "exist - was :" + nodeEntity.getBackupUri());
                } else if (!FileUtils.isValidDir(nodeEntity.getBackupUri()) && nodeEntity.getCreateDirectories())
                {
                    // Try to create the folder
                    try
                    {
                        org.apache.commons.io.FileUtils.forceMkdir(new File(nodeEntity.getBackupUri()));
                    } catch (IOException e)
                    {
                        throw new IllegalArgumentException("Invalid uri path to backup folder specified. Tried to create" +
                                "folder :" + nodeEntity.getBackupUri(), e);
                    }
                }
                logger.debug("Using backup path set to :" + nodeEntity.getBackupUri());
            }

            EndPoint inbound = getEndPoint(nodeEntity, configNode.getInBoundEndPoint());
            nodeEntity.setInBound(inbound);

            EndPoint outbound = getEndPoint(nodeEntity, configNode.getOutBoundEndPoint());
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
    private static EndPoint getEndPoint(final Node nodeEntity, final org.grouter.config.EndPoint endPoint) throws IllegalArgumentException
    {
        EndPoint result = new EndPoint();
        result.setId(endPoint.getId());
        boolean supportedEndpointtypeFound = false;


        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setCreatedBy(User.ADMIN);
        auditInfo.setModifiedBy(User.ADMIN);
        auditInfo.setCreatedOn(new Date());
        auditInfo.setModifiedOn(new Date());
        nodeEntity.setAuditInfo(auditInfo);
        result.setAuditInfo(auditInfo);

        String uriValid = null;
        if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FILE_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FILE_WRITER))
        {
            uriValid = endPoint.getUri().replace(FILE_PREFIX, "/");
            supportedEndpointtypeFound = true;
        } else if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FTP_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.FTP_WRITER))
        {
            String urlpath = endPoint.getUri();
            urlpath = urlpath.replace("ftp://", "");
            uriValid = urlpath.replaceAll("/", "");
            endPoint.setUri(uriValid);
            supportedEndpointtypeFound = true;
        } else if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.JMS_READER) ||
                endPoint.getEndPointType().equals(org.grouter.config.EndPointType.JMS_WRITER))
        {
            uriValid = endPoint.getUri();
            supportedEndpointtypeFound = true;
        } else if (endPoint.getEndPointType().equals(org.grouter.config.EndPointType.HTTP_READER))
        {
            String urlpath = endPoint.getUri();
            urlpath = urlpath.replace("http://", "");
            urlpath = urlpath.replace("https://", "");
            uriValid = urlpath.replaceAll("/", "");
            endPoint.setUri(uriValid);
            supportedEndpointtypeFound = true;
        }

        if (!supportedEndpointtypeFound)
        {
            throw new IllegalArgumentException("Unsupported endpoint type. Configuration showed :" +
                    endPoint.getEndPointType() + " Add support in ConfigFactory and EndPointType for this EndPointType.");
        }

        Context[] context = endPoint.getContextparamArray();
        Map<String, String> endPointContextMap = new HashMap<String, String>();
        if (context != null && context.length > 0)
        {
            for (Context item : context)
            {
                //EndPointContext endPointContext = new EndPointContext(item.getDisplayName(), item.getValue(), result);
                endPointContextMap.put(item.getName(), item.getValue());
            }
        }

        result.setClazzName(endPoint.getClazzname());
        result.setUri(uriValid);
        result.setCron(endPoint.getCron());
        result.setEndPointContext(endPointContextMap);

        EndPointType type = EndPointType.valueOf(new Long(endPoint.getEndPointType().intValue()));
        result.setEndPointType(type);
        return result;
    }
}
