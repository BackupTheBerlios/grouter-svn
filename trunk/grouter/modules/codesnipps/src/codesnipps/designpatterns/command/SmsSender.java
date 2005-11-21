package codesnipps.designpatterns.command;

public class SmsSender extends Command
{
    public SmsSender(Message msg)
    {
        message = msg;
        
    }
    
    public void execute()
    {
        System.out.println("Sending an sms " + message);
    }

}
