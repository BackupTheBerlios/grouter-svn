package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.Message;
import org.grouter.domain.Sender;
import org.grouter.domain.servicelayer.GRouter;
import org.grouter.domain.servicelayer.jms.GRouterPublishEventDTO;
import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.MessageDAO;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;


@Stateless
public class GRouterBean implements GRouterLocal, GRouterRemote
{
    private static Logger logger = Logger.getLogger(GRouterBean.class);
    @PersistenceContext(unitName="grouterDomain")
    private EntityManager entityManager;
    private MessageDAO messageDAO;

    public Message createMessage(Message message)
    {
        logger.debug("In createMessage.");
        messageDAO = DAOFactory.getFactory( DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        return messageDAO.createMessage(message);
    }



    //TODO fix a dao that takes a collection of messaes insttead...
    //@TransactionAttribute(TransactionAttributeType.NEVER)
    public void createMessage(Message[] messages)
    {
        logger.debug("In createMessage. Number of messages :" + messages.length);
        this.entityManager.persist(new Sender("test") );

        /*messageDAO = DAOFactory.getFactory( DAOFactory.FactoryType.EJB3_PERSISTENCE).getMessageDAO();
        for (Message message : messages)
        {
               messageDAO.createMessage(message);
        } */
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void persistMessageAndBroadcastEvent(GRouterPublishEventDTO dto)
    {
        if(dto == null)
        {
            throw new IllegalArgumentException("Can not handle a null inparameter");
        }
        logger.debug("Persisting and broadcating event to topic.");
                         createMessage(dto.getMessages());


    }
}
