package org.grouter.domain.servicelayer;

import org.grouter.domain.entities.Message;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-18
 * Time: 12:35:20
 * To change this template use File | Settings | File Templates.
 */

import javax.ejb.Remote;
import javax.ejb.Local;

@Remote
@Local
public interface GRouter
{
    Message createMessage(Message message);
    Message findMessageById(String id);
}
