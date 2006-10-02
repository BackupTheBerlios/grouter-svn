package org.grouter.domain.servicelayer.ejb3;

import org.grouter.common.jms.examples.JMSUtils;
import org.grouter.common.jms.QueueDestination;
import org.grouter.common.jms.AcknowledgeMode;
import org.grouter.domain.Message;
import org.grouter.domain.Sender;
import org.grouter.domain.Receiver;
import org.grouter.domain.servicelayer.jms.GRouterPublishEventDTO;
import org.apache.log4j.Logger;

import javax.jms.*;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.sql.Timestamp;

/**
 * Produces messages on queue.
 */
public class GrouterQueueMessageProducerTest implements Runnable
{
    private static Logger logger = Logger.getLogger(GrouterQueueMessageProducerTest.class);
    private QueueDestination queueDestination;
    protected Queue que;
    protected static final String QUEUE_TEST_QUEUE = "queue/GrouterQueue";
    protected QueueConnection conn;
    protected QueueSession session;
    protected QueueReceiver queueReceiver;
    protected static final int SLEEP = 5000;


    public GrouterQueueMessageProducerTest()
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
        InitialContext iniCtx = JMSUtils.getJbossInitialContext();
        queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, true, "ConnectionFactory", null, iniCtx, 4000, null, AcknowledgeMode.NONE);
        queueDestination.bind();
    }


    /**
     * Send the message.
     *
     * @param eventDTO
     * @throws javax.jms.JMSException
     * @throws javax.naming.NamingException
     */
    public void send(GRouterPublishEventDTO eventDTO)
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
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Sender sender = new Sender("A test sender");
                Receiver receiver = new Receiver("A test receiver");


                Message message = new Message("New test message : " + i);
                message.addToReceivers(receiver);
                message.setSender(sender);
                message.setCreationTimestamp(timestamp);
                sender.addToMessages(message);

                Message[] messages = new Message[]{message};

                GRouterPublishEventDTO eventDTO = new GRouterPublishEventDTO("testgrouterid", "testnode", messages);
                send(eventDTO);
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
        GrouterQueueMessageProducerTest producer = new GrouterQueueMessageProducerTest();
        Thread thr = new Thread(producer);
        thr.start();
    }

}
