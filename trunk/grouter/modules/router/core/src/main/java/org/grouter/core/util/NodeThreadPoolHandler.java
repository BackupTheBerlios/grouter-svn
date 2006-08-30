package org.grouter.core.util;

import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.CommandConsumerThread;
import org.grouter.core.config.Node;
import org.grouter.core.readers.FileReaderThread;

import java.util.concurrent.*;

/**
 * What's the right size for a thread pool, assuming the goal is to keep the
 * processors fully utilized? Amdahl's law gives us a good approximate formula, if
 * we know how many processors our system has and the approximate ratio of
 * compute time to wait time for the tasks.
 * Let WT represent the average wait time per task, and ST the average service
 * time (computation time) per task. Then WT/ST is the percentage of time a task
 * spends waiting. For an N processor system, we would want to have
 * approximately N*(1+WT/ST) threads in the pool.
 */
public class NodeThreadPoolHandler
{
    private static Logger logger = Logger.getLogger(NodeThreadPoolHandler.class);
    private ConcurrentMap map = new ConcurrentHashMap();
    private ScheduledExecutorService scheduler;
    private BlockingQueue<Command> blockingQueue;
    private static final int INITIAL_DELAY = 1000;
    private static final int CAPACITY = 1000;

    public NodeThreadPoolHandler()
    {
    }

    public void initNodeThreadScheduling(Node[] nodes)
    {
        logger.info("Found " + nodes.length + " number of nodes. Init scheduler executor service!");
        scheduler = Executors.newScheduledThreadPool(nodes.length);
        for (Node node : nodes)
        {
            blockingQueue = new ArrayBlockingQueue<Command>(CAPACITY);
            if (node.getNodeType() == Node.Type.FILE_TO_FILE)
            {
                FileReaderThread fileReaderThread = new FileReaderThread(node, blockingQueue);
                scheduler.scheduleAtFixedRate(fileReaderThread, INITIAL_DELAY, node.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
            }
            CommandConsumerThread commandConsumerThread = new CommandConsumerThread(blockingQueue);
            scheduler.scheduleAtFixedRate(commandConsumerThread, INITIAL_DELAY, node.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
        }
    }

    /*public void initNodeThreadScheduling(ConfigHandler configHandler) {
        nodeTypes = configHandler.getGrouterConfigDocument().getGrouterConfig().getNodeArray();
        logger.info("Found " + nodeTypes.length + " number of nodes. Init scheduler executor service!");
        scheduler = Executors.newScheduledThreadPool(nodeTypes.length);
        blockingQueue = new ArrayBlockingQueue<Command>(CAPACITY, true);
        for (NodeType nodeType : nodeTypes) {
            Node node = NodeFactory.getNode(nodeType);
            FileReaderThread fileReader = new FileReaderThread(node, blockingQueue);
            CommandConsumerThread workerThread = new CommandConsumerThread(blockingQueue);

            scheduler.scheduleAtFixedRate(fileReader, INITIAL_DELAY, node.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
            scheduler.scheduleAtFixedRate(workerThread, INITIAL_DELAY, node.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
        }
    } */

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
