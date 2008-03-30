package codesnipps.designpatterns.command;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Producees commands.
 * @author gepo
 *
 */
public class MessageProducer implements Runnable
{
    Queue q = new LinkedList();
    Command cmd;
    
    public void run()
    {
        for (int i = 0; i < 50; i++)
        {
            if(i%2==0)
            {
                 cmd = new EmailSender(new Message("this is an email"));
            }
            else
            {
                cmd = new SmsSender(new Message("this is an sms"));
            }
            q.offer(cmd);    
            try
            {
                Thread.sleep(600);
            } catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        MessageProducer producer = new MessageProducer();
        Thread thr = new Thread(producer);
        thr.start();
        
        Thread thrConsumer = new Thread(new MessageConsumer(producer.getQ()));
        thrConsumer.start();
    }

    public Queue getQ()
    {
        return q;
    }
}
