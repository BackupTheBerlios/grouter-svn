package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.entitylayer.Message;
import org.grouter.domain.entitylayer.SystemUser;
import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.SystemUserDAO;
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
public class GRouterBeanService implements GRouterLocalService, GRouterRemoteService
{
    private static Logger logger = Logger.getLogger(GRouterBeanService.class);
    @PersistenceContext(unitName = PersistenceContextName.PERSISTENCE)
    private EntityManager entityManager;

    private MessageDAO messageDAO;
    private SystemUserDAO systemUserDAO;


    @Resource
    private SessionContext sc;


    public SystemUser createSystemUser(SystemUser systemUser)
    {
        logger.debug("In createMessage. Inparam:" + systemUser);
        if(systemUser == null )
         {
             throw new IllegalArgumentException("Can not handle a null inparameter");
         }
        systemUserDAO = DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE).getSystemUserDAO();
        return systemUserDAO.createSystemUser(systemUser);
    }

    public Message createMessage(Message message)
    {
        logger.debug("In createMessage. Inparam:" + message);
        if(message == null )
         {
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
        logger.debug("In createMessage. Number of messages :" + messages.length);
        if(messages == null)
        {
            throw new IllegalArgumentException("Null inparameter");
        }
        messageDAO = DAOFactory.getFactory( DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        for (Message message : messages)
        {
            logger.debug("## Calling dao to create message");
               messageDAO.createMessage(message);
        }
    }

}
