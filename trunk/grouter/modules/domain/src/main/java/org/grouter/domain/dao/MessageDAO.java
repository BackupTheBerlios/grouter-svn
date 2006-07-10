package org.grouter.domain.dao;

import org.grouter.domain.Message;

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
