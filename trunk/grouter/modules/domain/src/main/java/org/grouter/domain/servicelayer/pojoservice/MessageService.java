package org.grouter.domain.servicelayer.pojoservice;

import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.servicelayer.dto.SystemUserDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MessageService will expose services.
 *
 */
public class MessageService
{
    private static Log log = LogFactory.getLog(MessageService.class);

    public void setSystemUserDAO(SystemUserDAO systemUserDAO) {
        this.systemUserDAO = systemUserDAO;
    }

    SystemUserDAO systemUserDAO;


    public void createSystemUser(SystemUserDTO systemUserDTO)
    {
        log.debug("In create system user...");

        //systemUserDAO.createSystemUser(systemUserDTO);


    }
}
