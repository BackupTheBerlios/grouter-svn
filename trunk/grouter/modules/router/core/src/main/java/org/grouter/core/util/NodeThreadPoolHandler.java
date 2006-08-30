package org.grouter.core.util;

import org.apache.log4j.Logger;
import org.grouter.core.command.Command;
import org.grouter.core.command.CommandConsumerThread;
import org.grouter.core.config.NodeConfig;
import org.grouter.core.config.GrouterConfig;
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

    /**
     * Consturctor
     */
    public NodeThreadPoolHandler()
    {
    }

    /**
     * Initialize threads.
     *
     * @param grouterConfig 
     * @throws IllegalArgumentException if nodeConfigs == null
     */
    public void initNodeThreadScheduling(GrouterConfig grouterConfig)
    {
        NodeConfig[] nodeConfigs = grouterConfig.getNodes();
        if(grouterConfig == null || nodeConfigs == null)
        {
            throw new IllegalArgumentException("Nodes can not be null!!");
        }
        logger.info("Found " + nodeConfigs.length + " number of nodeConfigs. Init scheduler executor service!");
        scheduler = Executors.newScheduledThreadPool(nodeConfigs.length);
        for (NodeConfig nodeConfig : nodeConfigs)
        {
            blockingQueue = new ArrayBlockingQueue<Command>(CAPACITY);
            if (nodeConfig.getNodeType() == NodeConfig.Type.FILE_TO_FILE)
            {
                FileReaderThread fileReaderThread = new FileReaderThread(nodeConfig, blockingQueue);
                scheduler.scheduleAtFixedRate(fileReaderThread, INITIAL_DELAY, nodeConfig.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
            }
            CommandConsumerThread commandConsumerThread = new CommandConsumerThread(blockingQueue);
            scheduler.scheduleAtFixedRate(commandConsumerThread, INITIAL_DELAY, nodeConfig.getInFolder().getPollIntervallMilliSeconds(), TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Delegate to ScheduledExecutorService for a shutdown of node threads.
     */
    public void shutdown()
    {
        scheduler.shutdown();
    }
}
