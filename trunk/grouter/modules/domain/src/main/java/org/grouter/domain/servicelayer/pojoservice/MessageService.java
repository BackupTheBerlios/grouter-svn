package org.grouter.domain.servicelayer.pojoservice;

import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.systemuser.SystemUser;
import org.grouter.domain.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * MessageService will expose services.
 *
 */
public class MessageService
{
    private static Log log = LogFactory.getLog(MessageService.class);
    SystemUserDAO systemUserDAO;
    MessageDAO messageDAO;

    public MessageService()
    {
        systemUserDAO = DAOFactorySpring.DEFAULT.getSystemUserDAO();
         messageDAO = DAOFactorySpring.DEFAULT.getMessageDAO();
    }


    public void createSystemUser(SystemUser systemUser)
    {
        log.debug("In create system user...");
        systemUserDAO.createSystemUser(systemUser);
        List<Message> messages = messageDAO.findAll();
        log.debug(messages);
        for (Message message : messages)
        {
            log.debug(message.getMessage());
        }

    }

    /**
     * For injection.
     * @param systemUserDAO
     */
    public void setSystemUserDAO(SystemUserDAO systemUserDAO)
    {
        this.systemUserDAO = systemUserDAO;
    }

    /**
     * For injection.
     * @param messageDAO
     */
    public void setMessageDAO(MessageDAO messageDAO) {
            this.messageDAO = messageDAO;
        }


}
