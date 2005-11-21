package codesnipps.designpatterns.command;

import java.util.Queue;

/**
 * A consumer.
 */
public class MessageConsumer implements Runnable
{
    private Queue q;
    boolean isRunning = true;
    Command cmd;
    
    public  MessageConsumer(Queue q)
    {
        this.q = q;
    }
    
    public void run()
    {
        
        while (isRunning)
        {
            cmd = (Command)q.poll();
            cmd.execute();
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        
    }
}
