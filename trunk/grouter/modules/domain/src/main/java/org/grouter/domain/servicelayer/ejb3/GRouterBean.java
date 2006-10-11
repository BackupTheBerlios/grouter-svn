package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.entities.Message;
import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.ejb3.PersistenceContextName;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.SessionContext;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;


@Stateless
public class GRouterBean implements GRouterLocal, GRouterRemote
{
 

    private static Logger logger = Logger.getLogger(GRouterBean.class);
    //todo alter to global name
    @PersistenceContext(unitName = PersistenceContextName.PERSISTENCE)
    private EntityManager entityManager;

    private MessageDAO messageDAO;

    @Resource
    private SessionContext sc;



    public Message createMessage(Message message)
    {
        logger.debug("In createMessage.");
        if(message == null )
         {
             logger.info("Got null event from router");
             throw new IllegalArgumentException("Can not handle a null inparameter");
         }
    
        messageDAO = DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        return messageDAO.createMessage(message);
    }

    public Message findMessageById(String id)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    //TODO fix a dao that takes a collection of messaes insttead...
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createMessage(Message[] messages)
    {
        logger.debug("## In createMessage. Number of messages :" + messages.length);
        messageDAO = DAOFactory.getFactory( DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        for (Message message : messages)
        {
            logger.debug("## Calling dao to create message");
               messageDAO.createMessage(message);
        }
    }

}
