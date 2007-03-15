package org.grouter.presentation.beans;

import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Message;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Georges Polyzois
 */
public class MessageBean
{
    private static Logger logger = Logger.getLogger(MessageBean.class);
    RouterService grouterService;
    Message message;

    public void setGrouterService(RouterService grouterService)
    {
        this.grouterService = grouterService;
    }

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }

    public List<Message> getMessages( String nodeid )
    {
        List<Message> messages =  grouterService.findAllMessages( nodeid  );
        return messages;
    }

    public void save()
    {
        logger.info("####");

        
    }
}
