package org.siri.nodeviewer.swing.events;

import org.apache.log4j.Logger;
import org.siri.nodeviewer.swing.panels.messageview.MessageItem;
import org.siri.nodeviewer.swing.util.Log4JInit;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Georges Polyzois
 */
public class MessageItemInjector implements Callable
{
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(MessageItemInjector.class);
    private Log4JInit Log4JInit;

    public static void main(String[] args)
    {
        MessageItemInjector messageItemInjector = new MessageItemInjector();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(messageItemInjector);
    }


    public Object call() throws Exception
    {
        int i = 0;
        while (true)
        {
            TimeUnit.SECONDS.sleep(1);
            boolean sent = false;
            if (i % 2 == 0)
            {
                sent = true;
            }

            MessageItem messageItem = new MessageItem(new String("nodeid" + i), "msgid" + i, "content " + i, "serviceid " + i, sent);
            ApplicationStateEvent event = new ApplicationStateEvent(messageItem, "test updates from injector", ApplicationEventType.UPDATEMESSAGEMODEL);
            ApplicationEventHandler.getInstance().fireDataChanged(event);
            logger.debug("Just fired one message event");
            i++;
        }
    }
}
