package org.grouter.core.util;

import org.grouter.config.NodeType;
import org.grouter.core.command.Command;
import org.grouter.core.config.Node;
import org.grouter.core.config.NodeFactory;
import org.grouter.core.readers.FileReader;
import org.grouter.core.readers.WorkerThread;
import org.grouter.common.config.ConfigHandler;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-28
 * Time: 10:15:11
 * To change this template use File | Settings | File Templates.
 */
public class NodeThreadPoolHandler {
    //Logger.
    private static Logger logger = Logger.getLogger(NodeThreadPoolHandler.class);
    private ConcurrentMap map = new ConcurrentHashMap();
    private ScheduledExecutorService scheduler;
    private BlockingQueue<Command> blockingQueue;
    private NodeType[] nodeTypes;
    private static final int INITIAL_DELAY = 1000;
    private static final int CAPACITY = 100;

    public NodeThreadPoolHandler() {
    }

    public void initNodeThreadScheduling(ConfigHandler configHandler )
    {
        nodeTypes = configHandler.getGrouterConfigDocument().getGrouterConfig().getNodeArray();
        logger.info("Found " + nodeTypes.length + " number of nodes. Init scheduler executor service!");
        scheduler = Executors.newScheduledThreadPool(nodeTypes.length);
        blockingQueue = new ArrayBlockingQueue<Command>(CAPACITY,true);
        for (NodeType nodeType : nodeTypes)
        {
                Node node = NodeFactory.getNode(nodeType);
                FileReader fileReader = new FileReader(node ,blockingQueue);
                WorkerThread workerThread = new WorkerThread(blockingQueue);
               
                scheduler.scheduleAtFixedRate(fileReader, INITIAL_DELAY, node.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
                scheduler.scheduleAtFixedRate(workerThread, INITIAL_DELAY, node.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Delegate to ScheduledExecutorService for a shutdown of node threads.
     */
    public void shutdown()
    {
       scheduler.shutdown();
    }

    public void restartNodeThread(String nodeId)
    {

    }

}
