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
    public List<Message> findConcrete(Class concreteClass);
}
