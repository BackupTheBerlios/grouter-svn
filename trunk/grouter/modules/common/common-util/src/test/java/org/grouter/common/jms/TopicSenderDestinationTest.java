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
public class TopicSenderDestinationTest extends AbstractDestinationTest
{
    int messageCounter;
    Context ctx;
    AbstractSenderDestination topicDestination;
    AbstractListenerDestination topicListener;


    public void setUp() throws Exception
    {
        messageCounter = 0;

        ctx = getInMemoryActiveMqContext();
        topicDestination = new TopicSenderDestination(TOPIC_TEST_QUEUE, ACTIVEMQCONNECTIONFACTORY, null, ctx, -1, AcknowledgeMode.AUTO_ACKNOWLEDGE);
        assertNotNull(topicDestination);

        topicListener = new TopicListenerDestination(TOPIC_TEST_QUEUE, ACTIVEMQCONNECTIONFACTORY, null, ctx, new MyMessageListener(), AcknowledgeMode.AUTO_ACKNOWLEDGE);
        assertNotNull(topicListener);
    }


    public void testSimpleSend() throws Exception
    {
        topicDestination.bind();
        topicListener.bind();

        topicDestination.sendMessage("SimpleSend");
        TimeUnit.SECONDS.sleep(2);           // give consumer time to execute...
        assertEquals(1, messageCounter);
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
            topicDestination.sendMessage("SimpleSend");
            fail("Should raise an exception since we are not bound");
        } catch (Exception e)
        {
            //expected
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