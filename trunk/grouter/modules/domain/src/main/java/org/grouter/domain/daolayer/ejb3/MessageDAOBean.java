package org.grouter.domain.daolayer.ejb3;

import org.apache.log4j.Logger;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.entities.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


public class MessageDAOBean extends GenericEjb3DAO<Message, String> implements MessageDAO
{
    private static Logger logger = Logger.getLogger(MessageDAOBean.class);

    public MessageDAOBean()
    {
        super(Message.class);
    }

    public MessageDAOBean(Class persistentClass)
    {
        super(persistentClass);
    }

    /**
     * Todo implement
     *
     * @param concreteClass
     * @return
     */
    public List<Message> findConcrete(Class concreteClass)
    {
        throw new NotImplementedException();
    }

    /**
     * Todo implement
     *
     * @param nodeId
     * @return
     */
    public List<Message> findMessagesForNode(String nodeId)
    {
        throw new NotImplementedException();
    }

    public Message createMessage(Message message)
    {
        logger.debug("####### In create message");
        Message returnval = null;
        try
        {

            returnval = save(message);
        }
        catch (Throwable thr)
        {
            logger.debug(thr, thr);
        }
        logger.debug("####### After save updte");

        return returnval;
    }

    public Message save(Message entity)
    {
        logger.debug("## save");
        return makePersistent(entity);


    }

    /**
     * Todo
     *
     * @param entity
     */
    public void delete(Message entity)
    {
        throw new NotImplementedException();
    }
}

