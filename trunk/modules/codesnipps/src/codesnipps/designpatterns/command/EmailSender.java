package codesnipps.designpatterns.command;

/**
 * A command for handling email sending.
 * 
 * @author gepo
 */
public class EmailSender extends Command
{
    
    public EmailSender(Message msg)
    {
        message = msg;
    }
    public void execute()
    {
        System.out.println("Sending an email " + message);
    }

}
