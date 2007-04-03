package org.grouter.presentation.controller.node;

import java.util.*;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;
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
public class NodeCallbackThread implements Runnable
{
    static Map nodes = new Hashtable();
    private static transient boolean active = false;
    private static final Logger logger = Logger.getLogger(NodeCallbackThread.class);
    WebContext wctx;
    private static final int CALLBACK_INTERVALL = 10000;
    String routerId;
    static Map clients = new Hashtable();

    public NodeCallbackThread()
    {
        wctx = WebContextFactory.get();
    }


    public synchronized void deRegister()
    {


    }

    /**
     * Set on or off for this view.
     */
    public synchronized void register(String routerId)
    {
        logger.info("Toggle callback for routerid :" + routerId);
        this.routerId = routerId;
        if (this.active == false)
        {
            this.active = true;
            new Thread(this).start();
        }
        // get my session id

        // store my session id in map

        // set register for callbacks true

        // start thread

    }


    /**
     * Loop while active and sleep for CALLBACK_INTERVALL.
     */
    public void run()
    {
        try
        {
            logger.debug("Starting server-side thread");

            while (active)
            {
                List<Node> nodes = ServiceFactory.getRouterService().findNodesWithNumberOfMessages(routerId);
                logger.debug("Number of nodes for this routerId :" + routerId + " is:" + nodes.size());

                // For all the browsers on the current page:
                String currentPage = wctx.getCurrentPage();
                Collection sessions = wctx.getScriptSessionsByPage(currentPage);
                for (Object session : sessions)
                {
                    ScriptSession sessionS = (ScriptSession) session;
                    sessionS.getId();

                    logger.debug("Scriptsessionid : " + sessionS.getId());

                }


                Util utilAll = new Util(sessions);

                for (Node node : nodes)
                {
                    logger.info("node_msg_id" + node.getId() + " #" + node.getNumberOfMessagesHandled().toString());
                    utilAll.setValue(node.getId(), node.getNumberOfMessagesHandled().toString());
                }

                Thread.sleep(CALLBACK_INTERVALL);
            }

            Util pages = new Util(wctx.getScriptSession());
            pages.setValue("callback_field", "");
            logger.debug("Stopping server-side thread");
        }
        catch (InterruptedException ex)
        {
            logger.warn("Thread interrupted.", ex);
        }
    }


    

}

