package org.grouter.domain.daolayer;

import org.grouter.domain.entities.Message;

import java.util.*;

/**
 * Business DAO operations related to the <tt>Message</tt> entity.
 * <p/>
 *
 * @author Georges Polyzois
 */
public interface MessageDAO extends GenericDAO<Message, String>
{
    List<Message> findConcrete(Class concreteClass);
    List<Message> findMessagesForNode(String nodeId);
    Message createMessage(Message message);

}
