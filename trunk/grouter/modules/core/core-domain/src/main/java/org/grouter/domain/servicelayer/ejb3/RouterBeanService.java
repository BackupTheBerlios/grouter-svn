package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;
import org.grouter.domain.daolayer.*;
import org.grouter.domain.daolayer.ejb3.PersistenceContextName;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.SessionContext;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import java.util.List;


@Stateless
public class RouterBeanService implements RouterLocalService, RouterRemoteService
{
    private static Logger logger = Logger.getLogger(RouterBeanService.class);
    @PersistenceContext(unitName = PersistenceContextName.PERSISTENCE)
    private EntityManager entityManager;

    private MessageDAO messageDAO;
    private NodeDAO nodeDAO;
    private UserDAO userDAO;
    private RouterDAO routerDAO;


    public void setRouterDAO(RouterDAO routerDAO)
    {
        this.routerDAO = routerDAO;
    }

    @Resource
    private SessionContext sc;


    public List<Router> findAll()
    {
        return routerDAO.findAll();  
    }

    public Message saveMessage(Message message)
    {
        logger.debug("In saveMessage. Inparam:" + message);
        if (message == null)
        {
            throw new IllegalArgumentException("Can not handle a null inparameter");
        }

        messageDAO = DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        return messageDAO.save(message);
    }

    public Message findMessageById(String id)
    {
        return messageDAO.findById( id );
    }

    /**
     * {@inheritDoc}
     */
    public List<Message> findAllMessages(String id)
    {
       return  messageDAO.findMessagesForNode( id );
    }

    public List<Node> findAllNodes(String routerId)
    {
        return nodeDAO.findAll();
    }


    //TODO fix a dao that takes a collection of messaes insttead...
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createMessage(Message[] messages)
    {
        logger.debug("In saveMessage. Number of messages :" + messages.length);
        if (messages == null)
        {
            throw new IllegalArgumentException("Null inparameter");
        }
        messageDAO = DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        for (Message message : messages)
        {
            logger.debug("## Calling dao to create message");
            messageDAO.save(message);
        }
    }

}
