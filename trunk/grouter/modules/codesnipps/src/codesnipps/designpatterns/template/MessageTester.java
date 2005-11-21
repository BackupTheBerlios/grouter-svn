package codesnipps.designpatterns.template;

/**
 * Test class.
 * @author gepo
 */
public class MessageTester
{
    static MessageSender sender;
    public static void main(String[] args)
    {
        sender = new EmailSender();
        sender.handleMessage("this is a message");
    }

}
