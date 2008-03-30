package codesnipps.designpatterns.template;

public class EmailSender extends MessageSender
{
    public EmailSender()
    {
    }

    boolean sendMessage(String message)
    {
        System.out.println("We are sending the message as email");
        return true;
    }

}
