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

package org.grouter.core.readers;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.grouter.core.command.AbstractCommand;
import org.grouter.core.command.CommandMessage;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.NodeStatus;
import org.quartz.JobExecutionContext;
import org.quartz.UnableToInterruptJobException;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * This Reader connects to ftp server and downloads files locally to the inbound Endpoint of the Node.
 * The Reader is a scheduled Quartz job and configured to run at a certain intervall. Upon execution
 * (by Quartz) the Reader initializes, validates context parameters and sets up a connection to the
 * inbound Endpoint.
 * <p/>
 * The Reader acts as a command producer and puts commands on internal queue to be consumed by a Writer
 * later on (Writers are also scheduled). This decouples produceers and consumers and is a classical
 * CommandPattern [GoF].
 *
 * @author Georges Polyzois
 */
public class HttpReaderJob extends AbstractReader
{
    private static Logger logger = Logger.getLogger(HttpReaderJob.class);
    public final static String HTTP_AUTH_USER = "httpAuthUser";
    public final static String HTTP_PORT = "httpPort";
    public final static String FILE_LIST = "fileList";
    private static final int HTTP_DEFAULT_PORT = 80;
    private static final String HTTP_PROTOCOL = "httpProtocol";
    private static final String HTTP_AUTHENTICATION_PASSWORD = "httpAuthenticationPassword";
    private static final String HTTP_DEFAULT_PROTOCOL = "http";

    /**
     * Empty - needed by Quartz framework.
     */
    public HttpReaderJob()
    {
    }


    @Override
    void init(final Node node, BlockingQueue<AbstractCommand> blockingQueue)
    {
        if (node == null || blockingQueue == null)
        {
            throw new IllegalArgumentException("Constructor called with null argument.");
        }
        this.node = node;
        this.queue = blockingQueue;
        //which type of commands should this servicenode worker handle
        command = getCommand(node);

    }


    @Override
    protected List<CommandMessage> readFromSource()
    {
        logger.info("Reading files from :" + node.getInBound().getUri());

        HttpClient client = new HttpClient();

        // a list of full paths on http server we will download from
        Map endPointContext = node.getInBound().getEndPointContext();

        List<String> remoteHttpUriToFile = getPathIncludingFile((String) endPointContext.get(FILE_LIST));
        List<CommandMessage> commandMessages = new ArrayList<CommandMessage>();


        Map map = node.getInBound().getEndPointContext();
        String host = node.getInBound().getUri();
        String strPort = (String) map.get(HTTP_PORT);
        int port = HTTP_DEFAULT_PORT;
        if (strPort != null)
        {
            port = Integer.parseInt(strPort);
        }
        String httpProtocol = HTTP_DEFAULT_PROTOCOL;
        if (map.get(HTTP_PROTOCOL) != null)
        {
            httpProtocol = (String) map.get(HTTP_PROTOCOL);
        }
        String user = (String) map.get(HTTP_AUTH_USER);
        String pwd = (String) map.get(HTTP_AUTHENTICATION_PASSWORD);


        GetMethod method = null;
        try
        {
            for (String fullPathToFile : remoteHttpUriToFile)
            {
                URL url = new URL(httpProtocol, host, port, fullPathToFile);
                if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(pwd))
                {
                    Credentials cred = new UsernamePasswordCredentials(user, pwd);
                    AuthScope scope = new AuthScope(url.getHost(), port, AuthScope.ANY_REALM);
                    client.getState().setCredentials(scope, cred);
                }

                method = new GetMethod(url.toExternalForm());

                //String localFileName = fullPathToFile;
                File internalInFile =  getInternalFile( fullPathToFile ); 
                FileOutputStream fos = new FileOutputStream(internalInFile);
                logger.info("Downloading file from http server:" + fullPathToFile);
                // we have a valid fullPathToFile and there is a file at that fullPathToFile

                int statusCode = client.executeMethod(method);

                if (statusCode == HttpStatus.SC_OK)
                {
                    logger.info("Downloading complete :" + internalInFile);
                    IOUtils.copy(method.getResponseBodyAsStream(), fos);

                    // Get part of the message to store for querying purposes
                    String message = getMessage(internalInFile);
                    CommandMessage cmdMessage = new CommandMessage(message, internalInFile);
                    commandMessages.add(cmdMessage);
                } else
                {
                    logger.error("Failed to download remote file :" + fullPathToFile + " Status code received :" + statusCode);
                }
                fos.close();
            }
        }
        catch (Exception e)
        {
            // TODO We need to reset state if we start working again
            node.setNodeStatus(NodeStatus.ERROR);
            node.setStatusMessage("Failed reading files from :" + node.getInBound().getUri() + " Error:" + e.getMessage());
            logStrategy.log(node);
            logger.warn("Connection problem with Http server.", e);
        }
        finally
        {
            // release any connection resources used by the method
            if (method != null)
            {
                method.releaseConnection();
            }
        }
        return commandMessages;
    }

    @Override
    protected void validate(Node node)
    {
        Map endPointContext = node.getInBound().getEndPointContext();
        if (endPointContext == null)
        {
            throw new RuntimeException("Context needs to be set for this type of EndPoint.");
        }

        if (node.getInBound().getUri() == null || node.getInBound().getUri().equals(""))
        {
            throw new RuntimeException("Can not use an empty ftp host to fetch data from.");
        }


        if (endPointContext.get(FILE_LIST) == null || endPointContext.get(FILE_LIST).equals(""))
        {
            throw new RuntimeException("Can not use an empty file list to fetch data from.");
        }

    }


    @Override
    void pushToIntMemoryQueue()
    {
        logger.debug("Putting cmd on queue " + command.toStringUsingReflection());
        queue.offer(command);
    }

    /**
     * Parses comma separated string of paths.
     *
     * @param pathIncludingFiles a list with comma separated strings, e.g /tmp/kalle.xml,nisse.xml
     * @return paths
     */
    private List<String> getPathIncludingFile(String pathIncludingFiles)
    {
        List<String> paths = new ArrayList<String>();
        Scanner scanner = new Scanner(pathIncludingFiles);
        try
        {
            scanner.useDelimiter(",");
            while (scanner.hasNext())
            {
                String pathInclFile = scanner.next();
                logger.debug(pathInclFile);
                paths.add(pathInclFile);
            }
        }
        catch (Exception e)
        {
            logger.warn(e, e);
        }
        finally
        {
            scanner.close();
        }
        return paths;
    }


    public void execute(JobExecutionContext jobExecutionContext)
    {
        setJobExecutionContext(jobExecutionContext);
        execute();
    }

    public void setQueue(BlockingQueue<AbstractCommand> queue)
    {
        this.queue = queue;
    }

    public void interrupt() throws UnableToInterruptJobException
    {
        logger.info(node.getId() + " got request to stop");
    }


    void setNodeStatusToRunning()
    {
        node.setNodeStatus(NodeStatus.RUNNING);
        node.setStatusMessage("");
        logStrategy.log(node);
    }


    void setNodeStatusToNotRunning(String errorMessage)
    {
        node.setNodeStatus(NodeStatus.ERROR);
        node.setStatusMessage(errorMessage);
        logStrategy.log(node);
    }
}