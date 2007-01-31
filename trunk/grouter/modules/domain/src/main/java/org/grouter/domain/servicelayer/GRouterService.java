package org.grouter.domain.servicelayer;

import org.grouter.domain.entitylayer.Message;
import org.grouter.domain.entitylayer.SystemUser;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-18
 * Time: 12:35:20
 * To change this template use File | Settings | File Templates.
 */

import javax.ejb.Remote;
import javax.ejb.Local;


/**
 * @version $Id: GRouterService.java 3343 2006-11-10 08:49:35Z YELLOW\gepo01 $
 */

@Remote
@Local
public interface GRouterService
{
    SystemUser createSystemUser(SystemUser systemUser);

    Message createMessage(Message message);
    Message findMessageById(String id);
}
