package org.grouter.domain.servicelayer.spring;

import static org.grouter.domain.daolayer.DAOFactory.FactoryType.HIBERNATESPRING;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.entities.SystemUser;
import org.grouter.domain.entities.Message;
import org.grouter.domain.servicelayer.GRouter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * GRouterService will expose services for different clients namely - gswing and gweb.
 * <p/>
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext.xml file.
 *
 * @author Georges Polyzois
 */
public class GRouterService implements GRouter
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

    public Message findMessageById(String id)
    {
        if(id != null)
        {
            throw new IllegalArgumentException("Parameter can not be null");
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
