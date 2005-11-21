package org.grouter.domain.servicelayer.pojoservice;

import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.servicelayer.dto.SystemUserDTO;

/**
 * MessageService will expose services.
 *
 */
public class MessageService
{
    SystemUserDAO systemUserDAO;

    public void createSystemUser(SystemUserDTO systemUserDTO)
    {
        systemUserDAO.createSystemUser(systemUserDTO);


    }
}
