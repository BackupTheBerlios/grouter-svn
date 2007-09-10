package org.grouter.common.jms.examples;

import org.apache.log4j.Logger;
import org.grouter.common.jms.AcknowledgeMode;
import org.grouter.common.jms.QueueSenderDestination;
import org.grouter.common.jndi.JNDIUtils;

import javax.naming.NamingException;
import javax.naming.InitialContext;

/**
 * Produces messages on queue.
 */
@SuppressWarnings({"JavaDoc"})
public class ManualJBossQMsgProducerUsingRebind extends AbstractJBossExample implements Runnable
{
    private static Logger logger = Logger.getLogger(ManualJBossQMsgProducerUsingRebind.class);
    private QueueSenderDestination queueDestination;


    public ManualJBossQMsgProducerUsingRebind()
    {
        try
        {
            setupMessaging();
        } 
        catch (NamingException e)
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
    private void setupMessaging() throws NamingException
    {
        InitialContext iniCtx = JNDIUtils.getJbossInitialContext();
        //queueDestination = new QueueSenderDestination(QUEUE_TEST_QUEUE, "ConnectionFactory",   null, iniCtx, 4000, AcknowledgeMode.NONE);

         /*queueDestination = new QueueSenderDestination(LOG_Q_NAME, (String) settingsContext.get(
                    SettingsContext.KEY_SETTINGS_JNDI_QUEUECONNECTIONFACTORY), new NeverRebind(),
                    getInitialContext(settingsContext), 4000, AcknowledgeMode.NONE);
           */
        queueDestination = new QueueSenderDestination(QUEUE_TEST_QUEUE, "UIL2ConnectionFactory",   null, iniCtx, 4000, AcknowledgeMode.NONE);
        queueDestination.bind();
    }


    /**
     * Send the message.
     *
     * @param text
     * @throws javax.jms.JMSException
     * @throws javax.naming.NamingException
     */
    public void send(String text)
    {
        logger.info("Sending message");
        queueDestination.sendMessage(text);
        logger.info("Message sennt");
    }

    /**
     * Send a message and wait...
     */
    public void run()
    {
        int i = 0;
        while(true)
        {
            try
            {
                send("Message nr "+ i++);
                Thread.sleep(SLEEP);
            } catch (Exception e)
            {
                logger.error(e,e);
            }
        }
    }


    public static void main(String[] args)
    {
        ManualJBossQMsgProducerUsingRebind producer = new ManualJBossQMsgProducerUsingRebind();

        Thread thr = new Thread(producer);
        thr.start();
    }

}
