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

    public List<Message> findConcrete(Class concreteClass)
    {
        throw new NotImplementedException();
    }

    public List<Message> findMessagesForNode(String nodeId)
    {
        throw new NotImplementedException();
    }

}

