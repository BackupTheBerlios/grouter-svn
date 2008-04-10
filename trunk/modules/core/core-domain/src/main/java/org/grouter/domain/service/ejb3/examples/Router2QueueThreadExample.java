package org.grouter.domain.service.ejb3.examples;

import org.apache.log4j.Logger;
import org.grouter.common.jms.AcknowledgeMode;
import org.grouter.common.jms.QueueSenderDestination;
import org.grouter.common.jndi.JNDIUtils;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Produces messages on queue.
 */
public class Router2QueueThreadExample implements Runnable
{
    private static Logger logger = Logger.getLogger(Router2QueueThreadExample.class);
    private QueueSenderDestination queueDestination;
    protected Queue que;
    protected static final String QUEUE_TEST_QUEUE = "queue/GrouterQueue";
    protected QueueConnection conn;
    protected QueueSession session;
    protected QueueReceiver queueReceiver;
    protected static final int SLEEP = 5000;


    public Router2QueueThreadExample()
    {
        try
        {
            setupMessaging();
        } catch (JMSException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NamingException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    /**
     * Bootstrap.
     *
     * @throws javax.jms.JMSException
     * @throws javax.naming.NamingException
     */
    private void setupMessaging() throws JMSException, NamingException
    {
        InitialContext iniCtx = JNDIUtils.getJbossInitialContext();
        queueDestination = new QueueSenderDestination(QUEUE_TEST_QUEUE, "ConnectionFactory", null, iniCtx, 4000, AcknowledgeMode.NONE);
        queueDestination.bind();
    }


    /**
     * Send the message.
     *
     * @param eventDTO
     * @throws javax.jms.JMSException
     * @throws javax.naming.NamingException
     */
    public void send(org.grouter.domain.entities.Message eventDTO)
    {
        logger.info("Sending message");
        queueDestination.sendMessage(eventDTO);
        logger.info("Message sennt");
    }

    /**
     * Send a message and wait...
     */
    public void run()
    {
        int i = 0;
        while (true)
        {
            try
            {
                org.grouter.domain.entities.Message message =   new org.grouter.domain.entities.Message();  //RouterMessageFactory.createRouter(i++);
                send(message);
                Thread.sleep(SLEEP);
            } catch (Throwable e)
            {
                logger.error(e, e);
                try
                {
                    Thread.sleep(SLEEP);
                } catch (InterruptedException e1)
                {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }


    public static void main(String[] args)
    {
        Router2QueueThreadExample producer = new Router2QueueThreadExample();
        Thread thr = new Thread(producer);
        thr.start();
    }

}
