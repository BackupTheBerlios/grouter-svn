package org.grouter.domain.servicelayer;

import org.grouter.domain.Message;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-18
 * Time: 12:35:20
 * To change this template use File | Settings | File Templates.
 */

import javax.ejb.Remote;


public interface GRouter
{
    Message createMessage(Message message);
}
