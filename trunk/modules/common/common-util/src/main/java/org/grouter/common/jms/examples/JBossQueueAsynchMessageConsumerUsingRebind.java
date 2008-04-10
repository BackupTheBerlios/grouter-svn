package org.grouter.common.jms.examples;

import org.apache.log4j.Logger;
import org.grouter.common.jms.QueueListenerDestination;
import org.grouter.common.jndi.JNDIUtils;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Register and listen for messages asynch from Queueu.
 */
@SuppressWarnings({"JavaDoc"})
public class JBossQueueAsynchMessageConsumerUsingRebind extends AbstractJBossExample implements MessageListener, Runnable
{
    private static Logger logger = Logger.getLogger(JBossQueueAsynchMessageConsumerUsingRebind.class);


    public JBossQueueAsynchMessageConsumerUsingRebind()
    {
        try
        {
            setupMessaging();
        } catch (NamingException e)
        {
            e.printStackTrace(); 
        }
    }

    /**
     * Bootstrap.
     *
     */
    private void setupMessaging() throws NamingException
    {
        InitialContext iniCtx = JNDIUtils.getJbossInitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");
        final QueueListenerDestination queueDestination = new QueueListenerDestination(QUEUE_TEST_QUEUE, null,
                null, iniCtx, this);
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
                e.printStackTrace();
            }
        }

    }
}
