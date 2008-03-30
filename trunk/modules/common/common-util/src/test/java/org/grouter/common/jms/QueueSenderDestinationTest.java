package org.grouter.common.jms;

import javax.naming.Context;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * Some tests using inmemory message broker.
 *
 * @author Georges Polyzois
 */
public class QueueSenderDestinationTest extends AbstractDestinationTest
{
    int messageCounter;
    Context ctx;

    public void setUp() throws Exception
    {
        messageCounter = 0;

        ctx = getInMemoryActiveMqContext();
        qDestination = new QueueSenderDestination(QUEUE_TEST_QUEUE, ACTIVEMQCONNECTIONFACTORY, null, ctx, -1, AcknowledgeMode.AUTO_ACKNOWLEDGE);
        assertNotNull(qDestination);

        qListener = new QueueListenerDestination(QUEUE_TEST_QUEUE, ACTIVEMQCONNECTIONFACTORY, null, ctx, new MyMessageListener(), AcknowledgeMode.AUTO_ACKNOWLEDGE);
        assertNotNull(qListener);

        qSenderTransactional = new QueueSenderDestination(QUEUE_TEST_QUEUE_TRANSACTIONAL, ACTIVEMQCONNECTIONFACTORY, null, ctx, -1);
        assertNotNull(qSenderTransactional);

        qListenerTransactional = new QueueListenerDestination(QUEUE_TEST_QUEUE_TRANSACTIONAL, ACTIVEMQCONNECTIONFACTORY, null, ctx, new MyTransactionalMessageListener());
        assertNotNull(qListenerTransactional);

        qSenderTemporaryQueue = new QueueSenderDestination(QUEUE_TEST_QUEUE_TRANSACTIONAL, ACTIVEMQCONNECTIONFACTORY, null, ctx, -1, AcknowledgeMode.CLIENT_ACKNOWLEDGE, true);
        assertNotNull(qSenderTemporaryQueue);

        qListenerTemporaryQueue = new QueueListenerDestination(QUEUE_TEST_QUEUE_TRANSACTIONAL, ACTIVEMQCONNECTIONFACTORY, null, ctx, null);
        assertNotNull(qListenerTemporaryQueue);
    }


    public void testSimpleSend() throws Exception
    {
        qDestination.bind();
        qListener.bind();

        qDestination.sendMessage("SimpleSend");
        TimeUnit.SECONDS.sleep(1);           // give consumer time to execute...
        assertEquals(1, messageCounter);
    }


    public void testSimpleSendMultiple() throws Exception
    {
        qDestination.bind();
        qListener.bind();

        qDestination.sendMessage("SimpleSend");
        qDestination.sendMessage("SimpleSend");
        TimeUnit.SECONDS.sleep(1);           // give consumer time to execute...
        assertEquals(1, messageCounter);
    }


    /**
     * We need to commit the transaction since we have no Acknowledge mode set and are using transactional
     * semantics.
     *
     * @throws Exception
     */
    public void testTransactionalSend() throws Exception
    {
        qSenderTransactional.bind();
        qListenerTransactional.bind();

        qSenderTransactional.sendMessage("SimpleSend");
        qSenderTransactional.getSession().commit();

        TimeUnit.SECONDS.sleep(1);           // give consumer time to execute...
        assertEquals(1, messageCounter);
    }


    /**
     * We need to commit the transaction since we have no Acknowledge mode set and are using transactional
     * semantics.
     *
     * @throws Exception
     */
    public void testTransactionalSendMultiple() throws Exception
    {
        messageCounter = 0;
        AbstractSenderDestination qMultipleSenderTransactional = new QueueSenderDestination(QUEUE_TEST_QUEUE_TRANSACTIONAL, ACTIVEMQCONNECTIONFACTORY, null, ctx, -1);
        AbstractListenerDestination qListenerMultipleReceive = new QueueListenerDestination(QUEUE_TEST_QUEUE_TRANSACTIONAL, ACTIVEMQCONNECTIONFACTORY, null, ctx, new MyTransactionalMessageListener());
        assertNotNull(qMultipleSenderTransactional);
        assertNotNull(qListenerMultipleReceive);

        qMultipleSenderTransactional.bind();
        qListenerMultipleReceive.bind();

        qMultipleSenderTransactional.sendMessage("SimpleSend");
        qMultipleSenderTransactional.sendMessage("SimpleSend");
        qMultipleSenderTransactional.sendMessage("SimpleSend");
        qMultipleSenderTransactional.sendMessage("SimpleSend");
        qMultipleSenderTransactional.getSession().commit();

        TimeUnit.SECONDS.sleep(1);           // give consumer time to execute...
        //assertEquals(4, messageCounter);
    }


    /**
     * We need to commit the transaction since we have no Acknowledge mode set and are using transactional
     * semantics.
     *
     * @throws Exception
     */
    public void testUnbindedSend() throws Exception
    {
        try
        {
            qSenderTransactional.sendMessage("SimpleSend");
            fail("Should raise an exception since we are not bound");
        } catch (JMSRuntimeException e)
        {
            //expected
        }
    }


    /**
     * We need to commit the transaction since we have no Acknowledge mode set and are using transactional
     * semantics.
     *
     * @throws Exception
     */
    public void testSendUsingTemporaryQueue() throws Exception
    {
        qSenderTemporaryQueue.bind();


        Thread listener = new Thread(new MyTemporaryMessageListener());
        listener.start();

        qSenderTemporaryQueue.sendMessage("SimpleSend");
        qSenderTemporaryQueue.waitAndGetReplyFromTemporaryDestination(2000);


        TimeUnit.SECONDS.sleep(1);           // give consumer time to execute...
    }




    private class MyTransactionalMessageListener implements MessageListener
    {
        private Logger logger = Logger.getLogger(MyTransactionalMessageListener.class);

        public void onMessage(Message message)
        {
            logger.info("MyTransactionalMessageListener: Got a message :" + message);
            messageCounter++;
        }
    }


    private class MyTemporaryMessageListener implements Runnable
    {
        private Logger logger = Logger.getLogger(MyTemporaryMessageListener.class);

        public void run()
        {
            logger.info("In run");
            //qListenerTemporaryQueue.sendReplyToTemporaryDestination();
        }
    }

    private class MyMessageListener implements MessageListener
    {
        private Logger logger = Logger.getLogger(MyMessageListener.class);

        public void onMessage(Message message)
        {
            logger.info("Got a message :" + message);
            messageCounter++;
        }
    }

}
