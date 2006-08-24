package org.grouter.core.readers;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;
import org.grouter.core.config.AbstractNode;
import org.grouter.core.config.FileReader;
import org.grouter.core.config.FileWriter;

import javax.xml.parsers.FactoryConfigurationError;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Class description.
 */
public class TestFileToFileWorker
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(TestFileToFileWorker.class);

    static
    {
        try
        {
            DOMConfigurator.configure("log4j.xml");
        } catch (FactoryConfigurationError factoryConfigurationError)
        {
            factoryConfigurationError.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String[] args)
    {
        ExecutorService pool = Executors.newScheduledThreadPool(2);
        AbstractNode abstractNode = new FileWriter(new File("C:\\temp\\todir"));

        FileReader fileReaderConfig = new FileReader(new File("C:\\temp\\fromdir"));
        fileReaderConfig.setBackup(true);
        Queue queue = new LinkedList();

        WorkerThread workerThread = new WorkerThread(queue);

        /*while (true)
        {
            try
            {
                pool.submit(new org.grouter.core.readers.FileReader(fileReaderConfig,queue, abstractNode));
                TimeUnit.SECONDS.sleep(3);
                pool.submit(workerThread);
            } catch (InterruptedException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        */


    }

}
