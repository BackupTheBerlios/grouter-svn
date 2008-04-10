package org.grouter.common.jms.examples;

import org.apache.log4j.Logger;
import org.grouter.common.jndi.JNDIUtils;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Produces messages on queue.
 */
@SuppressWarnings({"JavaDoc"})
public class ManualJBossQMsgProducer extends AbstractJBossExample implements Runnable
{
    private static Logger logger = Logger.getLogger(ManualJBossQMsgProducer.class);


    public ManualJBossQMsgProducer()
    {
        try
        {
            setupMessaging();
        } catch (JMSException e)
        {
            e.printStackTrace();
        } catch (NamingException e)
        {
            e.printStackTrace();
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
        Object tmp = iniCtx.lookup("UIL2ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        que = (Queue) iniCtx.lookup(QUEUE_TEST_QUEUE);
        session = conn.createQueueSession(false,
                QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }


    /**
     * Send the message.
     *
     * @param text
     * @throws JMSException
     * @throws NamingException
     */
    public void send(String text)
            throws JMSException
    {
        if (session != null)
        {

            QueueSender send = session.createSender(que);
            TextMessage tm = session.createTextMessage(text);
            send.send(tm);
            logger.info("Message sent");
            send.close();
        }
        else
        {
            logger.warn("No session");
            try
            {
                setupMessaging();
            } catch (NamingException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

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
                send("Message nr " + i++);
                Thread.sleep(SLEEP);
            } catch (Exception e)
            {
                logger.error(e, e);
            }
        }
    }


    public static void main(String[] args)
    {
        ManualJBossQMsgProducer producerManual = new ManualJBossQMsgProducer();
        Thread thr = new Thread(producerManual);
        thr.start();
    }

}
