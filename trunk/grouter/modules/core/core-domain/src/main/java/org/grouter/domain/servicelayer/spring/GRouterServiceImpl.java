package org.grouter.domain.servicelayer.spring;

import static org.grouter.domain.daolayer.DAOFactory.FactoryType.HIBERNATESPRING;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.entities.SystemUser;
import org.grouter.domain.entities.Message;
import org.grouter.domain.servicelayer.GRouterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * GRouterServiceImpl will expose services for different clients - gswing and gweb.
 * <p/>
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext.xml file
 * or if you are using the gswing client in spring-app.xml.
 * <p/>
 * This implementation class is injected using Spring IoC container.
 *
 * @author Georges Polyzois
 */
public class GRouterServiceImpl implements GRouterService
{
    private static Log logger = LogFactory.getLog(GRouterServiceImpl.class);
    private SystemUserDAO systemUserDAO;

    public MessageDAO getMessageDAO()
    {
        return messageDAO;
    }

    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    private MessageDAO messageDAO;

    public GRouterServiceImpl()
    {
    }

    private void initSystemDAO()
    {
        logger.info("Initializing the SystemUserDAO");
        systemUserDAO = DAOFactory.getFactory(HIBERNATESPRING).getSystemUserDAO();
    }

    private void initMessageDAO()
    {
        logger.info("Initializing the MessageDAOImpl");
        messageDAO = DAOFactory.getFactory(HIBERNATESPRING).getMessageDAO();
    }

    //TODO Can we use AOP to have a simple logging mechnism used by all sevice methods!?

    /**
     * @param systemUser
     * @return
     */
    public SystemUser createSystemUser(SystemUser systemUser)
    {

        logger.debug("In createSystemUser. Inaparam:" + systemUser);
        if (systemUser == null)
        {
            throw new IllegalArgumentException("Parameter can not be null");
        }
        initSystemDAO();
        return systemUserDAO.createSystemUser(systemUser);
    }

    public SystemUser findSystemUser(Long systemUserId)
    {
        logger.debug("In findSystemUser. Inaparam:" + systemUserId);
        if (systemUserId == null)
        {
            throw new IllegalArgumentException("Parameter can not be null");
        }
        initSystemDAO();
        return systemUserDAO.findById(systemUserId, false);
    }

    public Message createMessage(Message message)
    {
        logger.debug("In findMessageById. Inparameter message:" + message);
        if (message == null)
        {
            throw new IllegalArgumentException("Parameter can not be null");
        }
        initMessageDAO();
        return messageDAO.createMessage(message);
    }

    public Message findMessageById(String id)
    {
        logger.debug("######################## In findMessageById. Inparameter id:" + id);
        if (id == null)
        {
            throw new IllegalArgumentException("Parameter can not be null");
        }
        initMessageDAO();

        Message foundMessage = messageDAO.findById(id, false);
        logger.debug("######################## found " + foundMessage.getMessage());
        return foundMessage;
    }


    public Message findMessagesForNode(String nodeId)
    {
        logger.debug("In findMessagesForNode. Inparameter nodeId:" + nodeId);
        if (nodeId == null)
        {
            throw new IllegalArgumentException("Parameter can not be null");
        }
        initMessageDAO();

        throw new NotImplementedException();
    }


}
