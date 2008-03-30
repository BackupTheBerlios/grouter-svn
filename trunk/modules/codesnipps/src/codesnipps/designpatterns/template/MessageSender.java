package codesnipps.designpatterns.template;

import java.io.File;

/**
 * Defines a template for hanlding messages.
 * @author gepo
 * @stereotype abstract
 *
 */
public abstract class MessageSender
{
    /**
     * Algorithm - each step is represented as a method call.
     * The send message is handles by sub classes to this class.
     * @param message
     */ 
    final void handleMessage(String message)
    {
        File location = backupMessage(message);
        boolean sent = sendMessage(message);
        if(sent)
        {
            removeBackup(location);
        }
    }
    
    private void removeBackup(File location)
    {
        System.out.println("Removing backup media");
    }

    private File backupMessage(String message)
    {
        System.out.println("Store message in backup media");
        return new File("c:/message.txt");
    }

    abstract boolean sendMessage(String message);
}
