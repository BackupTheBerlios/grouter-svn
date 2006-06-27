package org.siri.nodeviewer.swing.events;

import org.siri.nodeviewer.swing.panels.messageview.MessageItem;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Georges Polyzois
 */
public class MessageItemInjector implements Callable
{

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
            ApplicationStateEvent event = new ApplicationStateEvent(messageItem, "test updates from injector", ApplicationStateEvent.ApplicationEventType.UPDATEMESSAGEMODEL);
            ApplicationEventHandler.getInstance().fireDataChanged(event);
            i++;
        }
    }
}
