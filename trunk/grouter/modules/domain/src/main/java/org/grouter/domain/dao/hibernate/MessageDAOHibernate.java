package org.grouter.domain.dao.hibernate;

import org.hibernate.*;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.Message;

import java.util.*;

/**
 * Hibernate-specific implementation of the <tt>MessageDAO</tt>
 * non-CRUD data access object.
 *
 */
public class MessageDAOHibernate extends GenericHibernateDAO<Message, String> implements MessageDAO
{

    public MessageDAOHibernate(Session session)
    {
        super(Message.class, session);
    }

    @SuppressWarnings("unchecked")
    public List<Message> findConcrete(Class concreteClass)
    {
        return getSession().createCriteria(concreteClass).list();
    }
}
