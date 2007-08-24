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

package org.grouter.presentation.controller.node;

import java.util.*;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;
import org.directwebremoting.proxy.scriptaculous.Effect;
import org.directwebremoting.util.Logger;
import org.grouter.domain.servicelayer.ServiceFactory;
import org.grouter.domain.entities.Node;


/**
 * Thread class uses reverse Ajax in DWR library to do callback into the browser window. Callback
 * fired when new messages are found on a {@link Node}.
 * <p/>
 * Internally uses a Map to store nodes and messages on nodes so that callbacks are not fired with
 * unless there is actually some new information to send back to the client.
 *
 * @author Georges Polyzois
 */
public final class NodeCallbackThread implements Runnable
{
    private static Map<String, Node> nodes = new Hashtable<String, Node>();
    private static transient boolean active = false;
    private static final Logger logger = Logger.getLogger(NodeCallbackThread.class);
    private WebContext wctx;
    public static final int CALLBACK_INTERVALL = 3000;
    private String routerId;
    private static Map<String, ScriptSession> clients = new Hashtable<String, ScriptSession>();

    public NodeCallbackThread()
    {
        wctx = WebContextFactory.get();
    }


    /**
     * Deregisters a ScriptSession from client map if user does so actively. If not done actively
     * a ScriptSession times out in 5min()
     */
    public synchronized void deRegister()
    {
        // stop the thread running...
        logger.debug("deregister called - invalidating session");

        // remove from registered listeners and then invalidate the ScriptSession
        clients.remove(wctx.getScriptSession().getId());
        wctx.getScriptSession().invalidate();

        if (clients.size() == 0)
        {
            // might as well stop thread since we have no registered listeners
            this.active = false;
        }

    }

    /**
     * Register a client in the map.
     */
    public synchronized void register(String routerId)
    {
        logger.info("Toggle callback for routerid :" + routerId);
        // todo : handle multiple router ids - store in map and do find for every rouer id on thread execution
        this.routerId = routerId;
        clients.put(wctx.getScriptSession().getId(), wctx.getScriptSession());
        if ( !this.active )
        {
            this.active = true;
            new Thread(this).start();
        }
    }


    /**
     * Loop while active and sleep for NodeCallbackThread.CALLBACK_INTERVALL
     */
    public void run()
    {
        try
        {
            logger.debug("Starting server-side thread");
            while (active)
            {
                List<Node> nodesFromQuery = null;
                if (clients.size() > 0)
                {
                    nodesFromQuery = ServiceFactory.getRouterService().findNodesWithNumberOfMessages(routerId);
                    logger.debug("Number of nodesFromQuery for this routerId :" + routerId + " is:" + nodesFromQuery.size());

                    for (Node node : nodesFromQuery)
                    {
                        // if node exists in map
                        if (this.nodes.get(node.getId()) != null)
                        {
                            logger.debug("Node already in map");
                            // check if something has been updated on the node - using hashcode

                            logger.debug(this.nodes.get(node.getId()).getNumberOfMessagesHandled().toString());
                            logger.debug(node.getNumberOfMessagesHandled().toString());
                            boolean hasNumberOfMessagesChanged = !this.nodes.get(node.getId()).getNumberOfMessagesHandled().equals(node.getNumberOfMessagesHandled());
                            if (hasNumberOfMessagesChanged)
                            {
                                logger.debug("Fire update event..");
                                // if something has changed fire update event
                                this.nodes.put(node.getId(), node);
                                fireNodeUpdatedEvent(node);
                            } else
                            {
                                // nothing changed - ignore and log
                                logger.info("Nothing changed on node :" + node.getId());
                            }
                        } else
                        {
                            // else if node does not exist store the node in the map
                            logger.debug("Storing node in map");
                            this.nodes.put(node.getId(), node);
                            fireNodeUpdatedEvent(node);
                        }
                    }
                } else
                {
                    logger.info("Thread running but no registered clients");
                }
                Thread.sleep(CALLBACK_INTERVALL);
            }
            logger.debug("Stopping server-side thread. Clearing nodes and clients.");
            clients.clear();
            nodes.clear();
        }
        catch (InterruptedException ex)
        {
            logger.warn("Thread interrupted.", ex);
        }
    }

    private void fireNodeUpdatedEvent(Node node)
    {
        // For all the browsers on the current page:
        //String currentPage = wctx.getCurrentPage();
        //logger.debug("currentPage:" + currentPage);

        Iterator iterator = clients.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = (String) iterator.next();

            ScriptSession asession = clients.get(key);
            Util utilAll = new Util(asession);
            logger.info("Setting value on :" + node.getId() + " to #" + node.getNumberOfMessagesHandled().toString());
            utilAll.setValue(node.getId(), node.getNumberOfMessagesHandled().toString());

            Effect effect = new Effect(asession);     //, duration: 15
            effect.highlight(node.getId(), "{ duration: 2, queue: {position: 'end', scope: '" + node.getId() + "', limit:1}}"); //"{startcolor:'e6e6fa', queue: {position: 'end', scope: 'price', limit:1}}");
            // highlight a row
            // effect.highlight("row_" + node.getId(), "{ duration: 1, queue: {position: 'end', scope: 'row_" + node.getId() + "', limit:1}}"); //"{startcolor:'e6e6fa', queue: {position: 'end', scope: 'price', limit:1}}");
        }
    }

    private void printSessionInfo(Collection sessions)
    {
        for (Object session : sessions)
        {
            ScriptSession sessionS = (ScriptSession) session;
            logger.debug("Scriptsessionid : " + sessionS.getId());
        }
    }
}

