package org.grouter.domain.servicelayer.spring;

import static org.grouter.domain.dao.DAOFactory.FactoryType.HIBERNATESPRING;
import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.systemuser.SystemUser;
import org.grouter.domain.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * GRouterService will expose services for clients - gswing and gweb.
 * <p/>
 * Methods and their transaction demarcation attributes are handled
 * in the Spring applicationContext.xml file.
 *
 * @author Georges Polyzois
 */
public class GRouterService
{
    private static Log log = LogFactory.getLog(GRouterService.class);
    private SystemUserDAO systemUserDAO;
    private MessageDAO messageDAO;

    public GRouterService()
    {
    }

    /**
     * 
     * @param systemUser
     * @return
     */
    public SystemUser createSystemUser(SystemUser systemUser)
    {
        log.debug("In createSystemUser...");
        systemUserDAO = DAOFactory.getFactory(HIBERNATESPRING).getSystemUserDAO();////DAOFactory.DEFAULT.getSystemUserDAO();
        return systemUserDAO.createSystemUser(systemUser);
    }

    public SystemUser findSystemUser(Long systemUserId)
    {
        log.debug("In findSystemUser...");
        systemUserDAO = DAOFactory.getFactory(HIBERNATESPRING).getSystemUserDAO();////DAOFactory.DEFAULT.getSystemUserDAO();
        return systemUserDAO.findById(systemUserId, false);
    }

    public Message createMessage(Message message)
    {
        log.debug("In createMessage...");
        messageDAO = DAOFactory.getFactory(HIBERNATESPRING).getMessageDAO();////DAOFactory.DEFAULT.getSystemUserDAO();
        return messageDAO.createMessage(message);
    }


    public Message findMessagesForNode(String nodeId)
    {
        log.debug("In findMessage...");
        //messageDAO = DAOFactory.getFactory(DAOFactory.FactoryType.HIBERNATESPRING).getMessageDAO();
        //Message message = (Message)messageDAO.findById(messageId,false);
        //log.debug(message);
        //return message;
        return null;
    }


}
