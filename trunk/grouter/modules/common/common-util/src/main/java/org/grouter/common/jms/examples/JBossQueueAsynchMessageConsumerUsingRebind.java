package org.grouter.common.jms.examples;

import org.apache.log4j.Logger;
import org.grouter.common.jms.QueueDestination;
import org.grouter.common.jndi.JNDIUtils;

import javax.jms.*;
import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * Register and listen for messages asynch from Queueu.
 */
public class JBossQueueAsynchMessageConsumerUsingRebind extends JBossExample implements MessageListener, Runnable
{
    private static Logger logger = Logger.getLogger(JBossQueueAsynchMessageConsumerUsingRebind.class);
    private QueueDestination queueDestination;


    public JBossQueueAsynchMessageConsumerUsingRebind()
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
        Object tmp = iniCtx.lookup("ConnectionFactory");
        queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, false, null,
                null, iniCtx, 4000, this);
        queueDestination.bind();
    }

    /**
     * Callback method invoked by JMS provider.
     *
     * @param msg
     */
    public void onMessage(Message msg)
    {
       // TextMessage tm = (TextMessage) msg;
        try
        {
            System.out.println("onMessage, recv text="
                    + msg);
        }
        catch (Throwable t)
        {
            logger.error(t, t);
        }
    }

    public static void main(String[] args)
    {
        JBossQueueAsynchMessageConsumerUsingRebind client = new JBossQueueAsynchMessageConsumerUsingRebind();
        Thread thr = new Thread(client);
        thr.start();
    }

    /**
     * Prevent us from exiting.
     */
    public void run()
    {
        while (true)
        {

            try
            {
                logger.info("Sleeping for " + SLEEP + " seconds");
                Thread.sleep(SLEEP);
            } catch (InterruptedException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }
}
