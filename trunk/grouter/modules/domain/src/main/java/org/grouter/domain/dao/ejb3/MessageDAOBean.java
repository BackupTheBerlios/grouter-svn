package org.grouter.domain.dao.ejb3;

import org.grouter.domain.Message;
import org.grouter.domain.dao.MessageDAO;
import org.apache.log4j.Logger;

import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A Message is consideered to be a root entity in our domain model.
 *
 */
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
     * @param concreteClass
     * @return
     */
    public List<Message> findConcrete(Class concreteClass)
    {
        throw new NotImplementedException();
    }

    /**
     * Todo implement
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

            returnval = saveOrUpdate(message);
        }
        catch(Throwable thr)
        {
            logger.debug(thr,thr);
        }
        logger.debug("####### After save updte" );
        
        return returnval;
    }

    public Message saveOrUpdate(Message entity)
    {
        logger.debug("## saveOrUpdate");
        return makePersistent(entity);


    }

    /**
     * Todo
     * @param entity
     */
    public void delete(Message entity)
    {
        throw new NotImplementedException();
    }
}

